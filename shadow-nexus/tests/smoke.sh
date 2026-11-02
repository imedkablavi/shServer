#!/usr/bin/env bash
set -Eeuo pipefail
ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
bash -n "$ROOT/scripts/snctl"
bash -n "$ROOT/scripts/adb-reverse-start"
bash -n "$ROOT/scripts/adb-reverse-stop"
python3 -m py_compile "$ROOT/portal/server.py"
python3 - <<'PY' "$ROOT"
from pathlib import Path
import csv, sys
root=Path(sys.argv[1])
with (root/'portal'/'downloads'/'Kestrel_Asset_Register.csv').open(encoding='utf-8') as f:
    rows=list(csv.DictReader(f))
assert len(rows)==3 and all(r['classification']=='SYNTHETIC-SCENARIO' for r in rows)
assert (root/'portal'/'downloads'/'Kestrel_Contacts.vcf').read_text().count('BEGIN:VCARD')==3
assert 'SYNTHETIC TRAINING EVENT' in (root/'portal'/'downloads'/'Kestrel_Calendar.ics').read_text()
assert 'Training scenario' in (root/'portal'/'index.html').read_text()
print('Shadow Nexus smoke tests passed.')
PY
