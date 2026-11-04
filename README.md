# shServer — USB Adli Bilişim Laboratuvarı

Bu depo aynı USB bellek için iki ayrı laboratuvar iş akışı içerir. Aynı USB üzerinde iki iş akışını aynı anda çalıştırmayın. İki betik de seri numarasına dayalı ortak kilit kullanır: /srv/cs2ssd/usb-forensics/.locks/usb-<SERIAL>.lock.

## 1. Önceki laboratuvar deneyi (case-001)

- Betik: scripts/usb-forensics
- Varsayılan kanıt yolu: /srv/cs2ssd/usb-forensics/case-001
- Docker imajı: usb-forensics-tools:local
- Eski deney içindir; yeni üniversite ödevinin dört aşamasını temsil etmez.
- Eski deneyin kayıtları ve imajları korunmalıdır. Yeni ödev betiği bu klasöre yazmaz.

İlgili eski komutlar: make build-legacy ve docker compose build forensic-tools.

## 2. Üniversite ödevi için yeni deney

- Betik: scripts/usb-forensics-odev-tr
- Veri üreticisi: scripts/olustur-test-verileri.py
- Kanıt yolu: /srv/cs2ssd/usb-forensics/<ÖĞRENCİ_NO>_<AD>_USB_Odevi_<UTC_ZAMANI>/
- Docker imajı: usb-forensics-assignment-tr:local
- İmajlar:
  - imajlar/imaj-1-orijinal.img
  - imajlar/imaj-2-silme-sonrasi.img
  - imajlar/imaj-3-format-sonrasi.img
  - imajlar/imaj-4-guvenli-silme-sonrasi.img

Yeni betik önce Türkçe Office Open XML belgeleri, PNG/JPG görüntüleri, WAV ses, MP4 video ve C++/Python örnek dosyaları üretir. Sonra imaj-1, Delete sonrası imaj-2, FAT32 format sonrası imaj-3 ve mantıksal sıfır yazma sonrası imaj-4 aşamalarını uygular.

Yeni deney Docker imajını usb-forensics-assignment-tr:local etiketiyle oluşturur; eski usb-forensics-tools:local etiketini kullanmaz. Dosya üreticisi yalnızca yeni iş akışı tarafından çağrılır.

## 3. Güvenli doğrulama

Aşağıdaki komutlar yalnızca sözdizimi ve statik koruma testleridir; USB üzerinde işlem yapmaz:

```bash
bash -n scripts/usb-forensics
bash -n scripts/usb-forensics-odev-tr
python3 -m py_compile scripts/olustur-test-verileri.py
bash tests/shellcheck-guardrails.sh
```

Yeni Docker imajını yalnızca make build-assignment oluşturur. Eski araç imajını make build-legacy oluşturur. Her iki imajın etiketleri ayrıdır.

## 4. Önemli

- Çalıştırmadan önce USB seri numarasını ve hedef diski doğrulayın.
- Yeni iş akışı USB'deki mevcut verileri silecek ve en son aşamada mantıksal sektörlere sıfır yazacaktır.
- Docker hizmetini başlatmak başka otomatik başlatma politikası olan kapsayıcıları da çalıştırabilir.
- İmajlar ve günlükler sunucudaki kanıt diskinde kalır; istemci bilgisayara otomatik indirilmez.
- Mantıksal sıfır doğrulaması, USB denetleyicisinin arkasındaki tüm fiziksel NAND hücrelerinin temizlendiğini kanıtlamaz.
- Autopsy veya başka bir kurtarma aracıyla dosya içeriğini kurtarma deneyi ayrıca yapılmalı ve belgelenmelidir.
