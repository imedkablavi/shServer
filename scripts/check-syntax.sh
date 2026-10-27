#!/usr/bin/env bash
set -Eeuo pipefail
ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
SCRIPT="$ROOT/scripts/usb-forensics"
bash -n "$SCRIPT"
grep -q '^#!/usr/bin/env bash' "$SCRIPT"
grep -q 'set -Eeuo pipefail' "$SCRIPT"
grep -q 'WORKFLOW FINISHED' "$SCRIPT"
if grep -Eq 'FULL WORKFLOW COMPLETED SUCCESSFULLY|capture: command not found|fail: command not found' "$SCRIPT"; then
  echo "Unexpected known failure pattern found." >&2
  exit 1
fi
echo "Static checks passed."
