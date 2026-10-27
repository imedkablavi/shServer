#!/usr/bin/env bash
set -Eeuo pipefail
ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
SCRIPT="$ROOT/scripts/usb-forensics"
bash -n "$SCRIPT"
grep -q '^#!/usr/bin/env bash' "$SCRIPT"
grep -q 'set -Eeuo pipefail' "$SCRIPT"
grep -q 'function capture' "$SCRIPT" || grep -q '^capture()' "$SCRIPT"
grep -q 'function acquire_image' "$SCRIPT" || grep -q '^acquire_image()' "$SCRIPT"
grep -q 'cmp -s' "$SCRIPT"
grep -q 'sha256sum -c' "$SCRIPT"
grep -q 'ERASE-AND-REINITIALIZE-' "$SCRIPT"
grep -q 'WIPE-' "$SCRIPT"
if grep -Eq 'FULL WORKFLOW COMPLETED SUCCESSFULLY' "$SCRIPT"; then
  echo "Known false-success message must not be present." >&2
  exit 1
fi
echo "Guardrail checks passed (syntax/static only; no device operations performed)."
