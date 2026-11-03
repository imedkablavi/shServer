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
