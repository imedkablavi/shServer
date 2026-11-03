#!/usr/bin/env bash
set -Eeuo pipefail
ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
AUDIT="$ROOT/scripts/kestrel-vaka-denetle"
TMP="$(mktemp -d)"
trap 'rm -rf "$TMP"' EXIT

mkdir -p "$TMP/08_REPORTS" "$TMP/05_SCENARIO_DATA" "$TMP/07_PHONE_ACTIVITY"
printf '{"case_id":"SNX-TEST","classification":"SENTETİK-SENARYO + GERÇEK-LAB-ETKİNLİĞİ"}\n' > "$TMP/08_REPORTS/run_summary.json"
printf 'test-verisi\n' > "$TMP/05_SCENARIO_DATA/scenario_communications_SIMULATED.sqlite3"
printf '{"requests":1}\n' > "$TMP/07_PHONE_ACTIVITY/phone_network_activity_ACTUAL.json"
printf '{"path":"/"}\n' > "$TMP/07_PHONE_ACTIVITY/lab_server_access_ACTUAL.jsonl"

(
  cd "$TMP"
  sha256sum \
    08_REPORTS/run_summary.json \
    05_SCENARIO_DATA/scenario_communications_SIMULATED.sqlite3 \
    07_PHONE_ACTIVITY/phone_network_activity_ACTUAL.json \
    07_PHONE_ACTIVITY/lab_server_access_ACTUAL.jsonl > SHA256SUMS.txt
)

bash "$AUDIT" "$TMP" > "$TMP/denetim-ilk.txt"
grep -q "BAŞARILI:" "$TMP/denetim-ilk.txt"

printf 'bozulma\n' >> "$TMP/07_PHONE_ACTIVITY/phone_network_activity_ACTUAL.json"
if bash "$AUDIT" "$TMP" > "$TMP/denetim-bozuk.txt"; then
  echo "HATA: Değiştirilmiş dosyanın hash kontrolü başarısız olmadı." >&2
  exit 1
fi
grep -q "doğrulaması başarısız" "$TMP/denetim-bozuk.txt"

echo "Kestrel vaka denetimi testleri başarılı."
