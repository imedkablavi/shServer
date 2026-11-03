# Operation Kestrel v2 — Gerçek Fotoğraf Kaynakları

## İlke

Bu pakette AI üretimi, kolaj veya sentetik çizim kullanılmaz. Dosyalar Wikimedia Commons'taki özgün JPEG kaynaklarından indirilir; yeniden boyutlandırma, kırpma, birleştirme veya EXIF değiştirme yapılmaz.

Fotoğraflar GERÇEK FOTOĞRAF / DIŞ-KAYNAK sınıfındadır. Bunlar Redmi Note 13 Pro kamerasıyla çekilmiş değildir ve Kestrel olayının kanıtı değildir. Yalnızca eğitim galerisinde gerçek fotoğraf dosyalarının incelenmesi için bağlam sağlar.

## Kaynak listesi

| Dosya | Fotoğrafın konusu | Yazar | Lisans | Kaynak |
|---|---|---|---|---|
| Usb_flash_drive_on_a_laptop_closeup.jpg | Dizüstü bilgisayara bağlı USB bellek | Shixart1985 | CC BY 2.0 | https://commons.wikimedia.org/wiki/File:Usb_flash_drive_on_a_laptop_closeup.jpg |
| Laptop-hard-drive-exposed.jpg | Açılmış 2,5 inç dizüstü HDD | Evan-Amos | CC BY-SA 3.0 | https://commons.wikimedia.org/wiki/File:Laptop-hard-drive-exposed.jpg |
| Datacenter_Rack.jpg | Veri merkezi sunucu rafı | Edmondo | CC BY-SA 3.0 | https://commons.wikimedia.org/wiki/File:Datacenter_Rack.jpg |

## Kaynak doğrulaması

- USB bellek fotoğrafının Commons sayfası özgün dosyanın 6016×4016 JPEG olduğunu, yazarını Shixart1985 olarak ve CC BY 2.0 lisansını bildirir. Sayfada Nikon D610 kamera, 35 mm odak uzaklığı ve 23 Ekim 2022 çekim zamanı EXIF alanları da listelenmiştir.
- Açık HDD fotoğrafı Evan-Amos'un kendi çalışmasıdır. Commons sayfası 4600×3520 JPEG ve CC BY-SA 3.0 lisansını bildirir.
- Veri merkezi rafı fotoğrafı Edmondo'nun kendi çalışmasıdır. Commons sayfası 453×604 JPEG ve CC BY-SA 3.0 lisansını bildirir.

Her indirme anında Wikimedia Commons API üzerinden güncel özgün dosya URL'si, yazar, lisans, boyut ve varsa kaynak EXIF tanımı tekrar sorgulanır. İndirilen verinin MediaWiki SHA-1 bilgisi doğrulanır; ayrıca SHA-256 manifesti üretilir. Kaynak sayfası/API lisans bilgisini yeniden doğrulamadan dosyalar dağıtılmaz.

## İndirme

Depo kök dizininden:

```bash
python3 shadow-nexus/scripts/kestrel-v2-gercek-fotograf-paketi-indir.py
```

Varsayılan çıktı:

```text
~/ShadowNexus_Cases/SNX-20261103-KESTREL-V2/02_GERCEK_FOTOGRAFLAR/
```

## Telefona aktarım

İndirme tamamlandıktan sonra:

```bash
bash shadow-nexus/scripts/kestrel-v2-gercek-fotograflari-telefona-aktar
```

Fotoğraflar telefonda hem vaka klasörüne hem de Galeri için `Pictures/ShadowNexus-Kestrel-V2` yoluna kopyalanır. Üç kopyanın SHA-256 değerleri karşılaştırılır. Ardından Shadow Nexus uygulamasında **Aktarılan fotoğrafları Galeri'ye tanıt** işlemi kullanılabilir.

## Lisans koşulları

CC BY 2.0 ve CC BY-SA 3.0 lisansları atıf gerektirir. CC BY-SA fotoğraflarını değiştirerek yeniden dağıtmak aynı veya uyumlu lisans koşulunu gerektirebilir. Bu proje dosyaları değiştirmeden korur; her kaydın lisansı ve atıf bilgisi indirilen `FOTOGRAF_PROVENANSI_TR.csv` ve `KULLANIM_VE_LISANS_TR.md` içinde yer alır.
