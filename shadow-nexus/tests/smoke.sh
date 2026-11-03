#!/usr/bin/env bash
set -Eeuo pipefail
ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
bash -n "$ROOT/snctl"
bash -n "$ROOT/scripts/adb-ters-baglantiyi-baslat"
bash -n "$ROOT/scripts/adb-ters-baglantiyi-durdur"
python3 -m py_compile "$ROOT/portal/server.py"
! SN_CASE_ID='../gecersiz' bash "$ROOT/snctl" vaka-olustur
! SN_PORT=80 bash "$ROOT/snctl" yardim
python3 - <<'PY' "$ROOT"
from pathlib import Path
import csv
import sys
import xml.etree.ElementTree as ET

root = Path(sys.argv[1])
with (root / "portal/downloads/Kestrel_Varlik_Listesi.csv").open(encoding="utf-8") as stream:
    rows = list(csv.DictReader(stream))
assert len(rows) == 3
assert all(row["siniflandirma"] == "SENTETİK-SENARYO" for row in rows)
assert (root / "portal/downloads/Kestrel_Egitim_Kisileri.vcf").read_text(encoding="utf-8").count("BEGIN:VCARD") == 3
assert "SENTETİK-SENARYO" in (root / "portal/downloads/Kestrel_Takvim_Etkinlikleri.ics").read_text(encoding="utf-8")
assert "Eğitim senaryosu" in (root / "portal/index.html").read_text(encoding="utf-8")
manifest = ET.parse(root / "android/app/src/main/AndroidManifest.xml").getroot()
permissions = {node.attrib.get("{http://schemas.android.com/apk/res/android}name") for node in manifest.findall("uses-permission")}
assert not any(p and ("SMS" in p or "CALL_LOG" in p) for p in permissions)
assert not (root / "README_AR.md").exists()
assert not (root / "docs/SAFETY_AND_CLASSIFICATION_AR.md").exists()
print("Shadow Nexus denetimleri başarılı.")
PY
bash -n "$ROOT/scripts/kestrel-vaka-denetle"
bash "$ROOT/tests/test-kestrel-vaka-denetle.sh"
bash -n "$ROOT/scripts/kestrel-senaryo-incele"


python3 - <<'PY' "$ROOT"
from datetime import datetime
from pathlib import Path
import csv
import sys

root = Path(sys.argv[1]) / "scenarios/operation-kestrel-v2"

def read_rows(name):
    with (root / name).open(encoding="utf-8", newline="") as stream:
        return list(csv.DictReader(stream))

characters = read_rows("KARAKTERLER_V2_SIMULATED.csv")
messages = read_rows("MESAJLASMA_V2_SIMULATED.csv")
timeline = read_rows("OLAY_ZAMAN_CIZELGESI_V2_SIMULATED.csv")

assert len(characters) == 6
assert all(row["source_classification"] == "SENTETIK-SENARYO" for row in characters)
accounts = [row for row in characters if row["record_type"] == "SYSTEM_ACCOUNT"]
assert len(accounts) == 1 and accounts[0]["display_name"] == "root@c2"
aliases = [row for row in characters if row["record_type"] == "LEGACY_ALIAS"]
assert len(aliases) == 2
assert all(row["mapping_status"] == "PROPOSED_PENDING_APPROVAL" for row in aliases)

assert len(messages) == 12
assert len({row["message_id"] for row in messages}) == len(messages)
assert all(row["source_classification"] == "SENTETIK-SENARYO" for row in messages)
message_times = [datetime.fromisoformat(row["timestamp_local"]) for row in messages]
assert message_times == sorted(message_times)

assert len(timeline) == 7
assert len({row["event_id"] for row in timeline}) == len(timeline)
assert all(row["source_classification"] == "SENTETIK-SENARYO" for row in timeline)
event_times = [datetime.fromisoformat(row["scenario_time_local"]) for row in timeline]
assert event_times == sorted(event_times)
assert "Gerçek telefon" in (root / "V2_TASARIM_NOTU_TR.md").read_text(encoding="utf-8")

print("Kestrel v2 senaryo verisi kontrolleri başarılı.")
PY


bash -n "$ROOT/scripts/kestrel-v2-telefon-materyali-aktar"
bash "$ROOT/scripts/kestrel-v2-telefon-materyali-aktar" --help >/dev/null
python3 - <<'PY' "$ROOT"
from pathlib import Path
import sys

root = Path(sys.argv[1])
script = (root / "scripts/kestrel-v2-telefon-materyali-aktar").read_text(encoding="utf-8")
for required in (
    "SNX-20261103-KESTREL-V2",
    "KARAKTERLER_V2_SIMULATED.csv",
    "MESAJLASMA_V2_SIMULATED.csv",
    "OLAY_ZAMAN_CIZELGESI_V2_SIMULATED.csv",
    "SHA256SUMS_HOST.txt",
    "REMOTE_MATERIALS",
):
    assert required in script, f"Missing transfer guard or artifact: {required}"
assert "su " not in script
assert "rm -rf" not in script
assert "127.0.0.1" not in script
print("Kestrel v2 aktarım betiği statik kontrolleri başarılı.")
PY


bash -n "$ROOT/scripts/kestrel-v2-gercek-fotograflari-telefona-aktar"
bash "$ROOT/scripts/kestrel-v2-gercek-fotograflari-telefona-aktar" --help >/dev/null
python3 -m py_compile "$ROOT/scripts/kestrel-v2-gercek-fotograf-paketi-indir.py"
python3 "$ROOT/scripts/kestrel-v2-gercek-fotograf-paketi-indir.py" --help >/dev/null
python3 - <<'PY' "$ROOT"
from pathlib import Path
import sys
import xml.etree.ElementTree as ET

root = Path(sys.argv[1])
java = (root / "android/app/src/main/java/info/imedkablavi/shadownexus/MainActivity.java").read_text(encoding="utf-8")
manifest = ET.parse(root / "android/app/src/main/AndroidManifest.xml").getroot()
permissions = {
    node.attrib.get("{http://schemas.android.com/apk/res/android}name")
    for node in manifest.findall("uses-permission")
}
assert not any(
    p and any(token in p for token in ("SEND_SMS", "WRITE_SMS", "WRITE_CALL_LOG", "READ_CALL_LOG"))
    for p in permissions
)
assert "Intent.ACTION_SENDTO" in java
assert "Intent.ACTION_DIAL" in java
assert "sms_body" in java
assert "MediaScannerConnection.scanFile" in java
assert "setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, \"image/*\")" in java
assert "Deniz Arslan [EĞİTİM]" in java
assert "Kerem Yılmaz [EĞİTİM]" in java
assert "Selin Kaya [EĞİTİM]" in java
assert "Mert Aydın [EĞİTİM]" in java
assert "Ece Yıldız [EĞİTİM]" in java
assert "Wikimedia_Servers-0001_44.jpg" in java
assert "example.invalid" in java
assert "0000000001" in java
assert "SmsManager" not in java
assert "CallLog.Calls.CONTENT_URI" not in java
assert "saveTrainingImage" not in java

photo_script = (root / "scripts/kestrel-v2-gercek-fotograf-paketi-indir.py").read_text(encoding="utf-8")
assert "commons.wikimedia.org/w/api.php" in photo_script
assert "sha256" in photo_script.lower()
assert "changed_after_download" in photo_script
assert "Shixart1985" in photo_script
assert "Evan-Amos" in photo_script
assert "Helpameout" in photo_script
assert "Wikimedia_Servers-0001_44.jpg" in photo_script
assert "ImageMagick" not in photo_script
assert "PIL" not in photo_script

transfer_script = (root / "scripts/kestrel-v2-gercek-fotograflari-telefona-aktar").read_text(encoding="utf-8")
assert "sha256sum" in transfer_script
assert "REMOTE_GALLERY" in transfer_script
assert "MEDIA_SCANNER_SCAN_FILE" in transfer_script
assert "Wikimedia_Servers-0001_44.jpg" in transfer_script
assert "rm -rf" not in transfer_script

print("Kestrel v2 gerçek iletişim ve fotoğraf güvenlik kontrolleri başarılı.")
PY


bash -n "$ROOT/scripts/kestrel-v2-cihaz-kamera-fotograflarini-al"
bash "$ROOT/scripts/kestrel-v2-cihaz-kamera-fotograflarini-al" --help >/dev/null
python3 - <<'PY' "$ROOT"
from pathlib import Path
import sys

root = Path(sys.argv[1])
camera_script = (root / "scripts/kestrel-v2-cihaz-kamera-fotograflarini-al").read_text(encoding="utf-8")
for required in (
    "/sdcard/DCIM/Camera",
    "03_CIHAZ_KAMERA_CEKIMLERI",
    "sha256sum",
    'pull "$REMOTE_PATH" "$DEST"',
    "Kaynak dosyalar telefonda bırakılır.",
    "EVET",
):
    assert required in camera_script, f"Missing camera preservation invariant: {required}"
assert "adb shell rm " not in camera_script
assert "rm -rf" not in camera_script
assert "magick " not in camera_script
assert "exiftool -" not in camera_script

guide = (root / "scenarios/operation-kestrel-v2/CIHAZ_KAMERASI_CEKIM_VE_KORUMA_V2_TR.md").read_text(encoding="utf-8")
assert "EXIF" in guide and "SHA-256" in guide
assert "SENTETİK EĞİTİM MATERYALİ" in guide
print("Kestrel v2 gerçek cihaz fotoğrafı koruma kontrolleri başarılı.")
PY
