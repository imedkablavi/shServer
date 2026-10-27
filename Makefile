.PHONY: check build
check:
	bash -n scripts/usb-forensics
	bash scripts/check-syntax.sh

build:
	docker compose build forensic-tools
