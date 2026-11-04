# Redmi Note 13 Pro — Dijital Adli İnceleme Raporu (Taslak)

> **Belge durumu:** TASLAK — gerçek Oxygen edinim sonuçları eklenmeden nihai rapor değildir. Bu çalışma araştırmacının kendi kontrolündeki cihazla yürütülen akademik eğitim laboratuvarı çalışmasıdır. Cihazın suç mahallinden ele geçirildiği veya resmî kolluk delili olduğu iddia edilmez.

## Kapak bilgileri

| Alan | Değer |
|---|---|
| Ders / çalışma | Bilişim Suçu İnceleme Yazılımları |
| Proje | Shadow Nexus — Operation Kestrel |
| Rapor başlığı | Redmi Note 13 Pro Android Cihazının Dijital Adli İncelemesi |
| Vaka / çalışma kimliği | SNX-20261103-KESTREL-V2 |
| Öğrenci adı | [Rapor sahibi tarafından eklenecek] |
| Öğrenci numarası | [Rapor sahibi tarafından eklenecek] |
| İnceleme cihazı | Xiaomi Redmi Note 13 Pro |
| Taslak tarihi | 04.11.2026 |
| Rapor sürümü | 0.1 — Ön inceleme taslağı |
| İnceleme aracı | Oxygen Forensic Detective / Device Extractor |
| Rapor dili | Türkçe |

## 1. Amaç ve kapsam

Bu çalışmanın amacı, Android işletim sistemli Redmi Note 13 Pro cihazında mobil adli bilişim sürecini akademik eğitim kapsamında uygulamak; cihazın başlangıç durumunu belgelemek; Root öncesinde Oxygen Forensic Detective ile desteklenen veri edinimini gerçekleştirmek; edinim çıktısını korumak ve yalnızca güvenli/uygun görülürse Root sonrası ayrı bir edinimle karşılaştırmaktır.

Çalışma iki veri sınıfını birbirinden ayırır:

1. **GERÇEK-CİHAZ / GERÇEK-LAB-ETKİNLİĞİ:** Redmi'nin gerçek sistem bilgileri, gerçek kullanıcı arayüzü işlemleri, kontrollü hatlar arasında yapılan gerçek SMS/arama testleri, gerçek kamera fotoğrafları, Oxygen işlem kayıtları ve edinim çıktıları.
2. **SENTETİK-SENARYO:** Operation Kestrel için kurgulanmış kişiler, mesaj metinleri, olay anlatısı ve eğitim dosyaları.

SENTETİK-SENARYO içeriği gerçek bir suçun, harici sunucu ihlalinin veya gerçek veri sızdırma olayının kanıtı olarak gösterilmez. Eğitim amaçlı SMS metni gerçekten gönderilmişse mesajın metni sentetik olabilir; ancak gönderim zamanı ve cihazın SMS kaydı gerçek laboratuvar etkinliğidir.

## 2. Senaryo özeti

Operation Kestrel, yetkisiz erişim, kurum dosyası aktarımı ve kayıt silme iddialarını inceleyen tamamen kurgusal bir mobil adli bilişim senaryosudur. Senaryo tarihleri 12–17.10.2026 aralığındadır. Bu tarihler kurgusal olay zamanlarıdır; gerçek cihaz edinim zamanı veya telefonun geçmiş etkinliği değildir.

Senaryoda kullanılan adlar:
- Deniz Arslan — kurgusal kişi.
- Kerem Yilmaz — kurgusal kişi.
- Selin Kaya — kurgusal BT koordinatörü.
- root@c2 — kurgusal hesap/etiket; insan kimliği değildir.

Önceki görsel taslaktaki Shadow ve Kemal (H) etiketleri, Deniz Arslan ve Kerem Yilmaz ile henüz doğrulanmış şekilde eşleştirilmemiştir. Eşleştirme onayı olmadan raporda kesin kimlik ilişkisi kurulmaz.

## 3. İnceleme cihazı ve başlangıç bilgileri

Aşağıdaki bilgiler önceki ADB/cihaz kontrol kayıtlarında gözlenmiştir. İlk resmi rapor oturumunda yeniden doğrulanacak ve ekran görüntüsü/çıktı ile eklenecektir.

| Özellik | Önceki gözlem | Raporlama durumu |
|---|---|---|
| Üretici | Xiaomi | İlk oturumda yeniden doğrulanacak |
| Cihazın ticari adı | Redmi Note 13 Pro | İlk oturumda yeniden doğrulanacak |
| Model kimliği | 23129RAA4G | Gerçek ADB çıktısı ile yeniden doğrulanacak |
| Ürün kodu | sapphire_global | Gerçek ADB çıktısı ile yeniden doğrulanacak |
| Android | Android 15 / API 35 | Gerçek ADB çıktısı ile yeniden doğrulanacak |
| Güvenlik yaması | 2026-08-01 | Gerçek ADB çıktısı ile yeniden doğrulanacak |
| Derleme | OS2.0.213.0.VNGMIXM | Gerçek ADB çıktısı ile yeniden doğrulanacak |
| Bootloader | Açık / unlocked olarak gözlenmiş | İlk oturumda yeniden doğrulanacak |
| Verified Boot | orange olarak gözlenmiş | İlk oturumda yeniden doğrulanacak |
| Root durumu | Yapılan kontrollerde Root tespit edilmedi | Mutlak “Root yok” olarak ifade edilmeyecek |
| USB hata ayıklama | Açık; ADB bağlantısı daha önce yetkilendirilmiş | İlk oturumda yeniden doğrulanacak |
| Oxygen sürümü | Henüz doğrulanmadı | About ekranından kaydedilecek |
| Oxygen edinim yöntemi | Henüz seçilmedi | Connected devices ekranında Redmi için sunulan yöntemlerden belirlenecek |

**Root değerlendirmesi:** su komutunun bulunmaması ve yaygın Root paketlerinin tespit edilmemesi, yalnızca uygulanan kontrollerde Root bulunmadığını gösterir. Bu sonuç bütün Root yöntemlerinin mutlak olarak bulunmadığını kanıtlamaz.

**Bootloader değerlendirmesi:** Bootloader kilidinin açık olması Root erişiminin etkin olduğu anlamına gelmez. Ayrıca bootloader/firmware işlemleri cihazda değişiklik veya veri kaybı yaratabileceğinden ilk edinim tamamlanmadan herhangi bir flaşlama yapılmayacaktır.

## 4. Cihazın fiziksel durumunun belgelenmesi

Bu bölüm, Redmi'nin gerçek durumuna bakılarak doldurulacaktır.

| Kontrol | Gözlem |
|---|---|
| Ön ekran / cam | [Fotoğraf ve gözlem eklenecek] |
| Arka kapak | [Fotoğraf ve gözlem eklenecek] |
| Sağ/sol kenarlar ve tuşlar | [Fotoğraf ve gözlem eklenecek] |
| USB-C bağlantı noktası | [Fotoğraf ve gözlem eklenecek] |
| SIM tepsisi | [Fotoğraf ve gözlem eklenecek; IMEI/ICCID yayımlanmayacak] |
| Ekran kilidi durumu | [Gerçek durum eklenecek] |
| Pil seviyesi | [İlk oturumda kaydedilecek] |
| Tarih/saat ve saat dilimi | [İlk oturumda kaydedilecek] |
| Görünür fiziksel hasar | [Varsa tarif edilecek; yoksa açıkça yazılacak] |

Fotoğraflar cihazın mevcut durumunu gösteren özgün dosyalar olarak korunacaktır. Görseller üzerine sonradan yazı eklenmiş, birleştirilmiş veya yapay olarak üretilmiş materyal fiziksel durum kanıtı olarak kullanılmayacaktır. Kişisel seri numarası/IMEI genel rapor kopyasında maskelenecektir.

## 5. Cihazın bilgisayara bağlanması ve laboratuvar hazırlığı

İnceleme bilgisayarında Oxygen Forensic Detective'in Windows kurulumu kullanılacaktır. Kurulu sürüm, lisans durumu, Windows sürümü, bağlantı biçimi ve programın cihazı nasıl tanıdığı gerçek ekran görüntüleriyle kaydedilecektir.

Bu aşamada:
- Cihaz modeli ve Android sürümü Oxygen arayüzünde doğrulanır.
- Bağlantı yöntemi ve USB/ADB durumları kaydedilir.
- Ekranda görülen uyarı ve izinler olduğu gibi belgelenir.
- Otomatik senkronizasyon veya veri değiştirme riski değerlendirilir.
- Henüz Root, boot imajı flaşlama, downgrade veya factory reset yapılmaz.

## 6. Root öncesi Oxygen edinimi

**Durum: Henüz gerçekleştirilmedi.**

Oxygen'in Android Agent yönteminin üretici dokümantasyonunda Android OS 15'i desteklediği belirtilmiştir. Ancak bu bilgi, Redmi'nin bütün edinim yöntemlerinin veya Full File System/Physical extraction'ın desteklendiği anlamına gelmez. Kullanılacak yöntem, kurulu Oxygen sürümünde bu cihaza bağlandığında gerçekten gösterilen seçeneklere göre seçilecektir.

Öncelik, cihazın mevcut durumunu mümkün olduğunca az değiştirerek kapsam dahilindeki kullanıcı verilerini almaktır. Eğer Oxygen Android Agent/Logical extraction sunarsa bu yöntem ve sınırları aynen raporlanır. Android Agent sonucu “tam fiziksel imaj” diye adlandırılmaz. Üretici dokümantasyonu, Android Agent'ın mantıksal ve seçili uygulama verisi edinimi için kullanıldığını; tam dahili bellek/dosya sistemi erişiminin ayrı yöntemler gerektirebileceğini belirtir.

Edinim kaydı:

| Alan | Değer |
|---|---|
| Oxygen sürümü | [About ekranından] |
| Device Extractor sürümü | [Ekrandan] |
| Cihaz tanıma sonucu | [Ekrandan] |
| Seçilen edinim yöntemi | [Ekrandaki tam adı] |
| Edinim kapsamı | [Seçilen kategoriler] |
| Başlama zamanı (UTC) | [Gerçek log] |
| Bitiş zamanı (UTC) | [Gerçek log] |
| Yerel saat dilimi | Europe/Istanbul |
| Çıktı klasörü | [Gerçek yol] |
| Araç sonucu | [Başarı/hata/uyarı] |
| SHA-256 | [Gerçek hash manifesti] |

## 7. Root öncesi analiz

Bu bölüm yalnızca Oxygen'in gerçekten çıkardığı veri kategorilerine göre doldurulacaktır. Beklenen kategori listesi, otomatik olarak elde edilmiş kayıt anlamına gelmez.

- Kişiler / Contacts: [Gerçek sonuç]
- SMS/MMS/RCS: [Gerçek sonuç]
- Gelen/giden/cevapsız aramalar: [Gerçek sonuç]
- Takvim: [Gerçek sonuç]
- Fotoğraf/video ve EXIF: [Gerçek sonuç]
- İndirilen dosyalar: [Gerçek sonuç]
- Tarayıcı geçmişi: [Gerçek sonuç]
- Uygulama verileri: [Gerçek sonuç ve destek sınırı]
- Wi-Fi/Bluetooth kayıtları: [Gerçek sonuç]
- Timeline: [Gerçek sonuç]
- Dosya sistemi kapsamı: [Gerçek sonuç]

Her bulgu için kaynak türü, uygulama/veritabanı, zaman damgası, saat dilimi, çıktı referansı ve sınırlamalar yazılır.

## 8. Root karar kaydı

**Durum: Root başlatılmadı.**

Root işlemi ancak aşağıdaki şartlar sağlandıktan sonra ayrıca değerlendirilir:

1. Root öncesi Oxygen edinimi tamamlanmış olmalıdır.
2. Özgün edinim klasörü ve loglar korunmalıdır.
3. SHA-256 değerleri hesaplanmalı ve tekrar doğrulanmalıdır.
4. Cihazın tam model/ürün kodu, ROM/build ve güvenlik yaması doğrulanmalıdır.
5. Cihaza özel Root yönteminin kaynağı ve uyumluluğu doğrulanmalıdır.
6. Veri kaybı, bootloop ve sistem değişikliği riskleri rapora yazılmalıdır.
7. Öğretim görevlisinin cihaz seçimine ilişkin onayı bulunmalıdır.

Samsung J7 örneğindeki Odin/CF-Auto-Root/SuperSU/TWRP adımları Redmi'ye aktarılmayacaktır.

## 9. Root sonrası edinim ve karşılaştırma

Bu bölüm Root işlemi gerçekten uygulanır ve Oxygen desteklenen bir edinim yöntemi sunarsa doldurulacaktır. Ayrı bir edinim adı/kimliği ve ayrı çıktı klasörü kullanılacak; Pre-Root çıktısı asla üzerine yazılmayacaktır.

| Veri kategorisi | Pre-Root sonucu | Post-Root sonucu | Fark | Açıklama |
|---|---|---|---|---|
| Contacts | [Bekliyor] | [Bekliyor] | [Bekliyor] | |
| SMS/MMS | [Bekliyor] | [Bekliyor] | [Bekliyor] | |
| Call Logs | [Bekliyor] | [Bekliyor] | [Bekliyor] | |
| Media/EXIF | [Bekliyor] | [Bekliyor] | [Bekliyor] | |
| App data | [Bekliyor] | [Bekliyor] | [Bekliyor] | |
| Browser | [Bekliyor] | [Bekliyor] | [Bekliyor] | |
| File System | [Bekliyor] | [Bekliyor] | [Bekliyor] | |
| Timeline | [Bekliyor] | [Bekliyor] | [Bekliyor] | |
| Deleted data | [Bekliyor] | [Bekliyor] | [Bekliyor] | Yalnızca gerçek kurtarma çıktısı varsa |

Root sonrası bir kaydın görünmesi, tek başına Root'un bu veriyi “kurtardığını” kanıtlamaz. Uygulama sürümü, senkronizasyon, yeni etkinlikler, edinim yöntemi ve zaman farkı dikkate alınır.

## 10. Sınırlamalar

- Bu cihaz araştırmacının kişisel kontrolündedir; suç mahallinden ele geçirilmiş telefon değildir.
- Cihazda kişisel veri bulunabilir; edinim kapsamı eğitim amacıyla gereken verilerle sınırlandırılmalı ve rapor kopyasında hassas bilgiler maskelenmelidir.
- Android Agent/Logical extraction, tam fiziksel imaj veya Full File System extraction ile eşdeğer değildir.
- Root durumu başlangıç kontrollerinde tespit edilmemiştir; kontrol sınırları raporda belirtilir.
- Root işlemi sistemde değişiklik ve veri kaybı riski taşıyabilir.
- Kurgusal senaryo materyalleri gerçek üçüncü taraf uygulama kayıtları gibi sunulmaz.
- Bulut çıkarımı, erişim belirteçleri veya üçüncü taraf hesapları bu çalışmanın mevcut kapsamına dahil değildir.

## 11. Sonuç

**Bu bölüm, gerçek Pre-Root ve varsa Post-Root edinim sonuçları alındıktan sonra yazılacaktır.** Şu anda çıkarım sayıları, uygulama bulguları, silinmiş veri kurtarma sonucu veya Root sonrası ek erişim iddiası bulunmamaktadır.

## 12. Ekler ve ekran görüntüleri

- Ek A — Cihazın fiziksel durum fotoğrafları.
- Ek B — ADB cihaz bilgileri ve Root başlangıç kontrolleri.
- Ek C — Oxygen Forensic Detective sürüm/lisans ekranı.
- Ek D — Oxygen Connected devices / cihaz tanıma ekranı.
- Ek E — Root öncesi edinim yöntemi ve ayarları.
- Ek F — Root öncesi edinim tamamlanma/log/hash.
- Ek G — Root öncesi analiz ekranları.
- Ek H — Root karar ve risk kaydı.
- Ek I — Root sonrası cihaz durumu ve edinim ekranları (gerçekleşirse).
- Ek J — Root sonrası analiz ve karşılaştırma (gerçekleşirse).

## Kaynak notları

- Oxygen Forensics, Android Agent ve cihaz desteği: https://www.oxygenforensics.com/technical-resources/android-agent/
- Oxygen Forensics, Detective v17.2 güncellemesi ve Android OS 15 desteği: https://www.oxygenforensics.com/news/oxygen-forensic-detective-v-17-updates/
- Referans rapor: “Bilişim Suçu İnceleme Yazılımları” — Samsung J7 / Android 6.0.1 örnek uygulaması. Bu rapor yalnızca bölüm yapısı açısından referans alınmıştır; cihaz-özel teknik adımları Kestrel'e kopyalanmamıştır.
