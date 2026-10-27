#!/usr/bin/env bash
set -Eeuo pipefail
ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
SCRIPT="$ROOT/scripts/usb-forensics"
DOCKERFILE="$ROOT/Dockerfile"
bash -n "$SCRIPT"
grep -q '^#!/usr/bin/env bash' "$SCRIPT"
grep -q 'set -Eeuo pipefail' "$SCRIPT"
grep -q '^capture()' "$SCRIPT"
grep -q '^acquire_image()' "$SCRIPT"
grep -q -- '--user "$(id -u):$(id -g)"' "$SCRIPT"
grep -q -- '--group-add "$disk_gid"' "$SCRIPT"
device_run_count="$(grep -c -- '--device=' "$SCRIPT")"
disk_group_count="$(grep -c -- '--group-add "$disk_gid"' "$SCRIPT")"
[[ "$device_run_count" -eq 3 && "$disk_group_count" -eq 3 ]] || {
  echo "Raw-device containers must run as the host user with the disk group explicitly added." >&2
  exit 1
}
grep -q 'cmp -s' "$SCRIPT"
grep -q 'sha256sum -c' "$SCRIPT"
grep -q 'ERASE-AND-REINITIALIZE-' "$SCRIPT"
grep -q 'WIPE-' "$SCRIPT"
grep -q 'S04_WIPEFS' "$SCRIPT"
grep -q 'S11_DELETE_SYNTHETIC_FILES' "$SCRIPT"
grep -q 'S20_WIPE_OPERATION' "$SCRIPT"
grep -q 'TERM_SHOT' "$SCRIPT"
grep -Fq 'CMD ["/bin/bash"]' "$DOCKERFILE"
if grep -Eq 'FULL WORKFLOW COMPLETED SUCCESSFULLY|capture: command not found|fail: command not found' "$SCRIPT"; then
  echo "Known false-success pattern found." >&2
  exit 1
fi
echo "Guardrail checks passed (syntax/static only; no physical device operations performed)."
