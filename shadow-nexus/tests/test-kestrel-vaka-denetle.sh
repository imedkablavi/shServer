#!/usr/bin/env bash
set -Eeuo pipefail
ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
AUDIT="$ROOT/scripts/kestrel-vaka-denetle"
TMP="$(mktemp -d)"
trap 'rm -rf "$TMP"' EXIT

mkdir -p \
  "$TMP/08_REPORTS" \
  "$TMP/05_SCENARIO_DATA" \
  "$TMP/07_PHONE_ACTIVITY" \
  "$TMP/09_PHONE_COPY/07_PHONE_ACTIVITY"

printf '{"case_id":"SNX-TEST","classification":"SENTETİK-SENARYO + GERÇEK-LAB-ETKİNLİĞİ"}\n' \
  > "$TMP/08_REPORTS/run_summary.json"
printf '{"path":"/"}\n' > "$TMP/07_PHONE_ACTIVITY/lab_server_access_ACTUAL.jsonl"

python3 - "$TMP" <<'PY'
import json
import sqlite3
import sys
from pathlib import Path

root = Path(sys.argv[1])
(root / "09_PHONE_COPY/07_PHONE_ACTIVITY/phone_network_activity_ACTUAL.json").write_text(
    json.dumps({"requests": [{"path": "/"}]}, ensure_ascii=False) + "\n",
    encoding="utf-8",
)
with sqlite3.connect(root / "05_SCENARIO_DATA/scenario_communications_SIMULATED.sqlite3") as db:
    db.execute("CREATE TABLE messages (id INTEGER PRIMARY KEY, body TEXT)")
    db.execute("INSERT INTO messages(body) VALUES ('SENTETİK-SENARYO')")
with sqlite3.connect(root / "09_PHONE_COPY/07_PHONE_ACTIVITY/phone_network_activity_ACTUAL.sqlite3") as db:
    db.execute("CREATE TABLE requests (id INTEGER PRIMARY KEY, path TEXT)")
    db.execute("INSERT INTO requests(path) VALUES ('/')")
PY

(
  cd "$TMP"
  sha256sum \
    08_REPORTS/run_summary.json \
    05_SCENARIO_DATA/scenario_communications_SIMULATED.sqlite3 \
    07_PHONE_ACTIVITY/lab_server_access_ACTUAL.jsonl \
    09_PHONE_COPY/07_PHONE_ACTIVITY/phone_network_activity_ACTUAL.json \
    09_PHONE_COPY/07_PHONE_ACTIVITY/phone_network_activity_ACTUAL.sqlite3 > SHA256SUMS.txt
)

bash "$AUDIT" "$TMP" > "$TMP/denetim-ilk.txt"
grep -q "BAŞARILI:" "$TMP/denetim-ilk.txt"
grep -q "09_PHONE_COPY — telefondan alınan kopya" "$TMP/denetim-ilk.txt"
grep -q "Telefon etkinliği SQLite kopyası sağlam" "$TMP/denetim-ilk.txt"

printf 'bozulma\n' >> "$TMP/09_PHONE_COPY/07_PHONE_ACTIVITY/phone_network_activity_ACTUAL.json"
if bash "$AUDIT" "$TMP" > "$TMP/denetim-bozuk.txt"; then
  echo "HATA: Değiştirilmiş dosyanın hash kontrolü başarısız olmadı." >&2
  exit 1
fi
grep -q "doğrulaması başarısız" "$TMP/denetim-bozuk.txt"

echo "Kestrel vaka denetimi testleri başarılı."
