.PHONY: check build build-legacy build-assignment

# check yalnızca statik kontroller çalıştırır; USB aygıtına dokunmaz.
check:
	bash -n scripts/usb-forensics
	bash -n scripts/usb-forensics-odev-tr
	python3 -m py_compile scripts/olustur-test-verileri.py
	bash tests/shellcheck-guardrails.sh

# build eski iş akışını koruyan varsayılandır.
build: build-legacy

build-legacy:
	docker build -t usb-forensics-tools:local .

build-assignment:
	docker build -t usb-forensics-assignment-tr:local .
