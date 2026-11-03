# Operation Kestrel v2 — Redmi Kamerasıyla Gerçek Fotoğraf Çekim Protokolü

## 1. Amaç

Bu protokol, eğitim sahnesinin Redmi Note 13 Pro kamerasıyla gerçekten fotoğraflanmasını ve orijinal kamera dosyalarının sonradan değiştirilmeden korunmasını sağlar.

Bu fotoğraflar gerçek cihaz kamerasından çıkmış olacaktır. Ancak sahne eğitim amacıyla kurulmuşsa fotoğraflar yalnızca sahnelenmiş eğitim ortamını gösterir; kurgusal sunucu ihlalini veya gerçek suç olayını kanıtlamaz.

## 2. Önerilen Türkçe sahne seti

Kişisel bilgi, yüz, plaka, ev adresi veya gerçek kurum bilgisi göstermeden aşağıdaki 5 kareyi çekin:

1. **Genel masa görünümü:** Laptop, kapalı USB bellek ve Kestrel eğitim klasörünün birlikte göründüğü geniş açı.
2. **USB ayrıntısı:** Eğitim amaçlı kullanılacak USB belleğin yakın planı.
3. **Laptop ayrıntısı:** Bilgisayar ekranında yalnızca yerel Kestrel laboratuvar portalı açıkken ekran ve klavye.
4. **Eğitim notu:** Üzerinde büyük ve okunaklı biçimde “SENTETİK EĞİTİM MATERYALİ” yazan basılı not ile dosya zarfı.
5. **Depolama aygıtı:** Kendi laboratuvarınıza ait kullanılmayan SSD/HDD veya USB donanımının ayrıntısı.

Fotoğrafları normal Kamera uygulamasıyla çekin. Fotoğraf üzerine sonradan yazı eklemeyin; kolaj, AI iyileştirme, kırpma, EXIF düzenleme veya yeniden sıkıştırma yapmayın.

## 3. Çekim öncesi

- Telefonun tarih/saat ayarı otomatik olmalı.
- Kamera uygulamasının kendi normal ayarları kullanılmalı.
- Gerçek müşteri belgesi, kişisel ekran, parola, seri numarası veya kimlik bilgisi kadraja girmemeli.
- Sahneye konan tüm yazılı materyaller açıkça “SENTETİK EĞİTİM MATERYALİ” ibaresi taşımalı.
- Kurgusal olay tarihleriyle eşleşsin diye telefon saati geriye alınmamalı.

## 4. Orijinal dosyaları bilgisayara alma

Fotoğrafları çektikten sonra USB debugging açık ve ADB yetkili durumdayken depo kök dizininden:

```bash
bash shadow-nexus/scripts/kestrel-v2-cihaz-kamera-fotograflarini-al
```

Betik:
- `/sdcard/DCIM/Camera` klasöründeki desteklenen görüntüleri listeler.
- Yalnızca sizin seçtiğiniz dosyaları alır.
- Orijinal dosyaları telefondan silmez.
- Kaynak dosya SHA-256 değeri, boyutu ve dosya sistemi değiştirilme zamanını okur.
- Bilgisayara kopyaladıktan sonra SHA-256 ve boyutu tekrar karşılaştırır.
- Dosyaları yeniden kodlamaz ve EXIF alanlarına yazmaz.
- Kayıtları `03_CIHAZ_KAMERA_CEKIMLERI` klasöründe tutar.

Birden çok ADB cihazı varsa:

```bash
SNX_ADB_SERIAL=CIHAZ_SERI bash shadow-nexus/scripts/kestrel-v2-cihaz-kamera-fotograflarini-al
```

## 5. Kayıtların anlamı

| Alan | Kanıt değeri |
|---|---|
| Fotoğraf dosyasının SHA-256 değeri | Kopya bütünlüğünü karşılaştırır |
| Kamera EXIF alanları | Varsa kamera modeli, çekim zamanı ve ayarlar hakkında ipucu sağlar |
| Dosya sistemi mtime | Dosyanın mevcut dosya sistemindeki değiştirilme zamanını gösterir; çekim zamanı ile aynı olduğu varsayılmaz |
| Fotoğraf içeriği | Çekilen fiziksel eğitim sahnesini gösterir |
| Sahne içindeki kurgusal not | Yalnızca sentetik eğitim materyalini gösterir; tek başına olay kanıtı değildir |

## 6. Klasör ve sınıflandırma

`03_CIHAZ_KAMERA_CEKIMLERI` içindeki dosyalar gerçek cihaz kamerasından alınmış kopyalardır. Kaynak sınıfı GERÇEK-CIHAZ; fotoğraf çekim işlemi GERÇEK-LAB-ETKİNLİĞİ olarak kaydedilir. Sahnenin kurgusal olması ayrıca belirtilir.

Bu klasör, Wikimedia Commons'tan alınan dış kaynaklı fotoğrafların tutulduğu `02_GERCEK_FOTOGRAFLAR` klasöründen ayrı kalır.
