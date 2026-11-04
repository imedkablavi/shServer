# Shadow Nexus — Kestrel Eğitim Laboratuvarı

Bazzite Linux yardımcı komutları ve Android uygulaması, Operasyon Kestrel adlı kurgusal mobil adli bilişim senaryosu için hazırlanmıştır.

## Temel bilgiler
- Hedef bilgisayar: Bazzite Linux.
- Hedef telefon: Redmi Note 13 Pro / Android 15; Root erişimi varsayılmaz.
- Önceki vaka: SNX-20261101-043455. Bu modül önceki vaka dosyalarını değiştirmez.
- Yeni ek vaka: SNX-20261103-KESTREL-ADDON.
- Senaryo kişileri, takvim etkinlikleri ve belgeler kurgusaldır.

## Android uygulaması
Android uygulaması Android 10 ve üzeri cihazlara kurulabilir. Derleme yönergesi: android/README_TR.md.

Uygulama:
- eğitim belgelerini İndirilenler klasörüne kaydeder;
- görünür sentetik filigran taşıyan bir eğitim görselini Galeri'ye kaydeder;
- Kişi Ekle ekranını açar; rehbere ekleme kullanıcı onayıyla yapılır;
- Takvim ekranını açar; etkinlik kaydı kullanıcı onayıyla yapılır;
- Bazzite üzerinde çalışan yerel Kestrel portalını açabilir;
- uygulama içindeki işlemler için dışa aktarılabilir bir etkinlik günlüğü oluşturur.

Uygulama SMS, arama kaydı, WhatsApp veya Telegram veritabanlarına yazmaz.

## Bazzite komutları
Depo kök dizininden:
```bash
bash shadow-nexus/snctl yardim
bash shadow-nexus/snctl denetle
bash shadow-nexus/snctl cihaz-bilgisi
bash shadow-nexus/snctl vaka-olustur
bash shadow-nexus/snctl portal-baslat
bash shadow-nexus/snctl durum
bash shadow-nexus/snctl ozet-hash
bash shadow-nexus/snctl hash-dogrula
```

Telefonun Chrome tarayıcısında yerel portalı açmak için bilgisayarda portal başlatıldıktan sonra:
```bash
bash shadow-nexus/scripts/adb-ters-baglantiyi-baslat
```
Telefon adresi: http://127.0.0.1:34871/

Durdurmak için:
```bash
bash shadow-nexus/snctl portal-durdur
bash shadow-nexus/scripts/adb-ters-baglantiyi-durdur
```

## Kanıt sınıflandırması
- GERÇEK-LAB-ETKİNLİĞİ: cihaz, kullanıcı veya yerel sunucu üzerinde gerçekten gözlenen etkinlik.
- SENTETİK-SENARYO: kurgusal eğitim kişileri, olayları ve içerikleri.
- DIŞ-KAYNAK: kaynağı ve lisansı kaydedilmiş dış materyal.
- ÇIKARILAN-KANIT: yalnızca Oxygen raporunda gerçekten görülen kayıt.

Bir dosyanın telefona kaydedilmiş olması, Oxygen tarafından kesinlikle çıkarılabileceği anlamına gelmez. Sonuçlar yalnızca gerçek çıkarım raporuna göre yazılmalıdır.

## Güvenlik
- Telefon üzerinde otomatik silme veya veri tabanı değiştirme işlemi yoktur.
- Sistem saati değiştirilmez; yapay zaman damgaları oluşturulmaz.
- Root, Bootloader kilidi açma veya güvenlik açığı kullanımı yoktur.
- USB edinim aracı bu modülden bağımsızdır; Kestrel için çalıştırılmamalıdır.

## Operation Kestrel — Ana Senaryo

Önceki senaryo yeni bir hikâyeyle değiştirilmemiştir. Ana vaka kimliği `SNX-20261101-043455` olarak korunur. `SNX-20261103-KESTREL-ADDON` yalnızca ek cihaz-durum kaydıdır.

- [Vaka dosyası ve olay örgüsü](scenarios/operation-kestrel/VAKA_DOSYASI_TR.md)
- [Kanıt matrisi ve sınıflandırma](scenarios/operation-kestrel/KANIT_MATRISI_TR.md)
- [Root öncesi / Root sonrası uygulama planı](scenarios/operation-kestrel/UYGULAMA_PLANI_TR.md)

Gerçek laboratuvar etkinliği, sentetik senaryo içeriği ve Oxygen'den çıkarılan kanıtlar ayrı sınıflandırılır. Root öncesi Oxygen edinimi tamamlanıp doğrulanmadan Root aşamasına geçilmez.

### Ana vaka dosyasını denetleme

Önceki Operation Kestrel vaka klasörünü değiştirmeden denetlemek için:

```bash
bash shadow-nexus/scripts/kestrel-vaka-denetle
```

Farklı bir konum kullanılıyorsa tam yol verilebilir:

```bash
bash shadow-nexus/scripts/kestrel-vaka-denetle "/tam/yol/SNX-20261101-043455"
```

Bu komut dosya envanterini, `run_summary.json` biçimini, sentetik iletişim SQLite veritabanının bütünlüğünü, telefon etkinliği JSON dosyasını, yerel sunucu JSONL günlüğünü ve varsa SHA-256 manifestlerini salt okunur olarak kontrol eder. Telefon etkinliği JSON dosyasını ana klasörde veya `09_PHONE_COPY` altında arar. Eksik dosyalar uyarı olarak raporlanır; bozuk JSON/SQLite veya hash uyuşmazlığı hata üretir. Testleri çalıştırmak için `make -C shadow-nexus test` komutunu kullanın.

### Senaryo içeriğini inceleme

Ana Kestrel vakasının sentetik iletişim veritabanı, telefondan alınmış etkinlik kopyası, yerel sunucu günlüğü ve senaryo CSV dosyalarını salt okunur incelemek için:

```bash
bash shadow-nexus/scripts/kestrel-senaryo-incele
```

Farklı vaka konumu için:

```bash
bash shadow-nexus/scripts/kestrel-senaryo-incele "/tam/yol/SNX-20261101-043455"
```

Bu araç ağ bağlantısı kurmaz, telefona erişmez ve dosya oluşturmaz. İletişim veri tabanını açıkça `SENTETİK-SENARYO`, gerçek yerel portal günlüklerini `GERÇEK-LAB-ETKİNLİĞİ` olarak değerlendirir. Hassas nitelikli sütunlar çıktıda gizlenir.

### Mevcut vaka içeriği ve kanıt boşlukları

İlk denetim çıktısına dayalı içerik incelemesi ve tutarlılık uyarıları:
- [Mevcut veri incelemesi ve tutarlılık açıkları](scenarios/operation-kestrel/MEVCUT_VERI_INCELEMESI_TR.md)

Bu inceleme, mevcut telefon etkinliğinin yalnızca yerel eğitim portalına erişimi doğruladığını ve önceki görseldeki sunucu ihlali anlatısıyla mevcut iletişim veri kümesi arasında henüz eşleşme olmadığını açıklar. V1 vaka verileri değiştirilmeden korunur.


## Operation Kestrel v2 — Ayrı sentetik senaryo taslağı

V1 vaka dosyaları korunarak ayrı bir V2 senaryo paketi eklenmiştir. V2 materyalleri kurgusal mesajlar ve olay zaman çizelgesinden oluşur; native SMS, WhatsApp, Telegram veya gerçek sunucu günlükleri değildir.

- [V2 tasarım notu](scenarios/operation-kestrel-v2/V2_TASARIM_NOTU_TR.md)
- [V2 karakter ve hesap listesi](scenarios/operation-kestrel-v2/KARAKTERLER_V2_SIMULATED.csv)
- [V2 sentetik mesajlaşma verisi](scenarios/operation-kestrel-v2/MESAJLASMA_V2_SIMULATED.csv)
- [V2 sentetik olay zaman çizelgesi](scenarios/operation-kestrel-v2/OLAY_ZAMAN_CIZELGESI_V2_SIMULATED.csv)
- [V2 kanıt kaynağı ve kullanım rehberi](scenarios/operation-kestrel-v2/KANIT_KAYNAK_REHBERI_V2_TR.md)

Önceki görsel taslaktaki Shadow ve Kemal (H) etiketlerinin mevcut sentetik kişi kayıtlarıyla eşleştirilmesi henüz onaylanmamıştır; V2 tasarım notunda öneri olarak tutulur. root@c2 bir kurgusal hesap etiketidir, insan kimliği değildir. Gerçek cihaz/laboratuvar zamanları, senaryo zamanlarından ayrı kaydedilir.


### Kestrel V2 materyallerini telefona aktarma

Root öncesi senaryo materyallerini ayrı bir klasöre, normal dosya aktarımıyla kopyalamak için:

```bash
bash shadow-nexus/scripts/kestrel-v2-telefon-materyali-aktar
```

Birden fazla ADB cihazı bağlıysa:

```bash
SNX_ADB_SERIAL=CIHAZ_SERI bash shadow-nexus/scripts/kestrel-v2-telefon-materyali-aktar
```

Betiğin oluşturduğu bilgisayar vaka klasörü `$HOME/ShadowNexus_Cases/SNX-20261103-KESTREL-V2`, telefon klasörü ise `/sdcard/Download/ShadowNexus_Cases/SNX-20261103-KESTREL-V2` olur. Altı V2 dosyası kopyalanır, kaynak ve hedef SHA-256 değerleri karşılaştırılır, aktarım günlüğü ve manifest iki tarafta saklanır. Hedef klasörlerden biri zaten varsa betik üzerine yazmak yerine durur.

Bu işlem gerçek laboratuvar aktarımıdır; aktarılan içeriklerin tümü SENTETİK-SENARYO olarak kalır. V1 vaka klasörüne, uygulama veritabanlarına veya sistem saatine dokunulmaz. Betik Root veya Oxygen edinimi yapmaz.


### Kestrel V2 — Türkçe iletişim verileri ve gerçek fotoğraf paketi

V2 Android uygulaması beş Türkçe eğitim kişisi içerir. Kişi kartları kullanıcı onayıyla Android Rehber ekranında açılır; telefon alanları bilerek geçersiz yer tutuculardır, e-posta alanları `example.invalid` kullanır.

Gerçek SMS/arama kayıtları sentetik satır enjeksiyonu ile oluşturulmaz. Uygulama, yalnızca araştırmacının kendi kontrolündeki ikinci test hattına ait numarayı girerek varsayılan SMS/Telefon uygulamasını açar. Gönderim veya arama kullanıcının onayını gerektirir. Gelen arama/SMS için ikinci cihazdan gerçek işlem yapılır. Ayrıntılar:
- [Gerçek SMS ve arama test planı](scenarios/operation-kestrel-v2/GERCEK_ILETISIM_TEST_PLANI_V2_TR.md)

### Lisanslı orijinal fotoğraflar

Kestrel V2 için AI ile oluşturulmuş veya birleştirilmiş görsel yerine üç özgün Wikimedia Commons JPEG kaynağı kullanılır. Kaynak, yazar, lisans, özgün boyut ve SHA-256 bilgileri ayrı manifestte tutulur:
- [Gerçek fotoğraf kaynakları ve lisanslar](scenarios/operation-kestrel-v2/GERCEK_FOTOGRAF_KAYNAKLARI_V2_TR.md)

İndir:

```bash
python3 shadow-nexus/scripts/kestrel-v2-gercek-fotograf-paketi-indir.py
```

Telefona aktar ve SHA-256 değerlerini doğrula:

```bash
bash shadow-nexus/scripts/kestrel-v2-gercek-fotograflari-telefona-aktar
```

Uygulamayı yeniden derledikten sonra **Aktarılan fotoğrafları Galeri'ye tanıt** seçeneğini kullanın. Fotoğraf dosyaları değiştirilmez; EXIF korunur. Bunlar Redmi ile çekilmiş fotoğraflar veya Kestrel olayının gerçek kanıtları değildir.

### İletişim ve fotoğraf bütünlüğü

- V2 mesaj CSV dosyaları ve karakter kayıtları SENTETİK-SENARYO olarak kalır.
- Gerçek SMS/çağrı işlemi varsa GERÇEK-LAB-ETKİNLİĞİ olarak gerçek zamanında kaydedilir.
- Uygulama SMS/Call Log veri tabanına doğrudan yazmaz ve bunlar için özel izin istemez.
- Fotoğraf kaynağı DIŞ-KAYNAK / GERÇEK-FOTOĞRAF; dosya aktarımı GERÇEK-LAB-ETKİNLİĞİ olarak ayrı kaydedilir.
- Root öncesi Oxygen edinimi tamamlanmadan Root'a geçilmez.


### Redmi kamerasıyla çekilen özgün fotoğrafları koruma

Gerçek cihaz kamerasıyla eğitim sahnesinin fotoğrafını çekmek için:
- [Kamera çekim ve koruma protokolü](scenarios/operation-kestrel-v2/CIHAZ_KAMERASI_CEKIM_VE_KORUMA_V2_TR.md)

Fotoğrafları telefonun `DCIM/Camera` klasöründen bilgisayara orijinal baytlarıyla almak için:

```bash
bash shadow-nexus/scripts/kestrel-v2-cihaz-kamera-fotograflarini-al
```

Betik dosyaları listeler, yalnızca seçtiğiniz fotoğrafları kopyalar, telefon kaynağını silmez ve SHA-256/boyut eşleşmesini doğrular. Orijinal fotoğraflar `03_CIHAZ_KAMERA_CEKIMLERI` içinde saklanır; dış kaynaklı Commons fotoğraflarıyla karıştırılmaz.


### Nihai rapor yapısı ve kanıt planı

Samsung J7 örnek PDF raporunun bölüm sırası incelenerek Kestrel V2'ye özel nihai rapor planı hazırlanmıştır:
- [Rapor yapısı, kanıt planı ve referans raporun uyarlama notları](scenarios/operation-kestrel-v2/RAPOR_YAPISI_VE_KANIT_PLANI_V2_TR.md)

Bu belge referans rapordaki olay/cihaz fotoğrafları, teslim, Oxygen Android Agent, import/hash/zaman dilimi, Pre-Root, Root, Post-Root ve karşılaştırma bölümlerini Kestrel'e uyarlar. Samsung J7'ye özgü Odin/CF-Auto-Root/SuperSU adımlarının Redmi Note 13 Pro'da kullanılmaması gerektiğini açıklar. Ayrıca Android Agent ediniminin otomatik olarak fiziksel imaj sayılmaması ve bulut Access Token bilgilerinin gizli tutulması gerektiğini belirtir.
