# Operation Kestrel V2 — Nihai Rapor Yapısı ve Kanıt Planı

## 1. Amaç ve referans

Bu belge, 42 sayfalık “Bilişim Suçu İnceleme Yazılımları” Samsung J7 örnek raporundaki bölüm sırasını inceler ve Kestrel V2 için uyarlanmış bir nihai rapor planı tanımlar.

Referans rapor; olay senaryosu, fiziksel cihaz fotoğrafları, teslim bilgileri, Oxygen Forensic Android Agent çıkarımı, içe aktarma ayarları, Root hazırlığı, Root sonrası dosya sistemi çıkarımı, uygulama analizleri ve sonuç karşılaştırması bölümlerinden oluşur.

**Uyarlama ilkesi:** Referans raporun sunum yapısı kullanılabilir; ancak Samsung J7'ye özgü teknik işlemler, tarih/kimlikler, veri sayıları ve bulgular Kestrel'e kopyalanmaz. Kestrel raporu yalnızca Redmi Note 13 Pro'dan gerçekten kaydedilen ekranlar, Oxygen çıktıları ve doğrulanmış dosyalara dayanır.

| Alan | Kestrel V2 değeri |
|---|---|
| Proje | Shadow Nexus |
| Senaryo | Operation Kestrel V2 |
| V1 referans vakası | SNX-20261101-043455 — değiştirilmeden korunur |
| V2 materyal çalışma kimliği | SNX-20261103-KESTREL-V2 |
| İnceleme cihazı | Xiaomi Redmi Note 13 Pro |
| İşletim sistemi | Android 15 |
| Başlangıç durumu | Bootloader açık olarak gözlendi; Root kontrollerinde Root tespit edilmedi |
| Edinim aracı | Oxygen Forensic Detective / Device Extractor — yüklü sürüm ve desteklenen yöntem gerçek ekrandan kaydedilecek |
| Rapor dili | Türkçe |

## 2. Referans raporun yapısı ve Kestrel'e uyarlama

| Referans PDF sayfaları | Referans bölüm | Kestrel V2 karşılığı | Gerekli kanıt |
|---|---|---|---|
| 1 | Kapak | Proje adı, vaka kimliği, öğrenci/teslim bilgileri | Son rapor kapak sayfası |
| 2–4 | Olay özeti, olay yeri, taraflar, teslim | Kurgusal Kestrel senaryosu ve eğitim laboratuvarı kapsamı | Vaka dosyası, kurgusal olay yeri planı, gerçek teslim/çalışma günlüğü |
| 3 | Cihazın fiziksel durumu | Redmi'nin ön, arka, yan, ekran ve bağlantı portu görüntüleri | Kullanıcı tarafından çekilmiş orijinal fotoğraflar |
| 4 | Teslim eden/alan ve zincir | Cihazın kime, ne zaman ve hangi durumda teslim edildiğinin kaydı | İmzalı teslim formu veya eğitim laboratuvarı işlem kaydı |
| 5 | Bilgisayara bağlantı hazırlığı | Windows/Oxygen bilgisayarı ve Redmi bağlantısı | Cihaz tanıma ekranı, ADB/USB durumu, otomatik eşitleme kontrolü |
| 6–8 | Oxygen sürümü ve çıkarım yöntemi seçimi | Oxygen sürümü, lisans durumu, Redmi'nin algılanması ve sunulan yöntemler | About ekranı, Connected devices, cihaz/OS tanıma ve yöntem listesi |
| 9–13 | Android Agent hazırlığı ve tamamlanması | Root öncesi gerçek edinim, izinler, işlem ilerlemesi ve sonuç | Gerçek oturum ekran kayıtları, çıktı klasörü, log ve özet |
| 14–18 | İçe aktarma metaverisi ve analiz yapılandırması | Vaka/kanıt kimliği, saat dilimi, hash ve analiz seçenekleri | Import sihirbazı ekranları, seçilen ayarlar, hash raporu |
| 19 | Root öncesi analiz | İlk edinimde bulunan ve bulunmayan kayıtlar | Oxygen'in gerçek Pre-Root raporu ve artefakt envanteri |
| 20–25 | Root hazırlığı ve uygulaması | Yalnızca Pre-Root edinim tamamlanıp korunduktan sonra değerlendirilecek cihaz-özel Root çalışması | Root yöntemi, dosya kaynağı, hash, cihaz durumu ve gerçek ekran kayıtları |
| 26–30 | Root sonrası dosya sistemi çıkarımı | Oxygen'in cihaz için fiilen sunduğu Root sonrası edinim | Yöntem adı, kapsam, hata/uyarı, çıktı boyutu, log ve hash |
| 31–34 | Root sonrası analiz ve karşılaştırma | Aynı veri türlerinin Pre-Root / Post-Root karşılaştırması | Gerçek artefakt listeleri, kaynak veritabanları, timeline ve analiz ekranları |
| 35–41 | Bulut hesabı çıkarımı | Bu projenin temel kapsamı dışında; yalnızca ayrı ve açık yetki varsa | Ayrı onay, kendi test hesabı, ayrı vaka ve güvenli kimlik bilgisi yönetimi |
| 42 | Sonuç | Kestrel'in gerçek bulguları, kısıtlamaları ve Root öncesi/sonrası farkları | Kanıt matrisi ve karşılaştırma tablosu |

## 3. Kestrel için rapor bölümleri

### Bölüm A — Vaka ve senaryo

1. Proje amacı ve ders kapsamı.
2. Kurgusal Kestrel olay özeti.
3. Kişiler, hesaplar ve takma adlar.
4. Kurgusal olay zaman çizelgesi.
5. Alternatif açıklamalar ve araştırma soruları.
6. Sentetik materyaller ile gerçek cihaz/laboratuvar etkinliğinin ayrımı.

V2 CSV materyalleri (karakter, ileti ve olay zaman çizelgesi) **SENTETİK-SENARYO** olarak raporlanır. Bunlar yerel Android SMS, WhatsApp veya Telegram veritabanı değildir.

### Bölüm B — Cihazın ilk durumu

1. Model, ürün kodu, Android sürümü, güvenlik yaması ve build bilgisi.
2. Bootloader / Verified Boot durumu.
3. Root kontrolünde kullanılan yöntem ve elde edilen çıktı.
4. USB hata ayıklama ve bilgisayar bağlantısı.
5. Pil seviyesi, tarih/saat ve saat dilimi.
6. Ön/arka/yan/ekran/port fotoğrafları.
7. Kişisel bilgiler görüntülerde maskelenir; orijinal fotoğraf kopyaları korunur.

Root kontrol sonucu mutlak “Root yoktur” biçiminde yazılmaz. Kullanılan kontrollerde Root tespit edilmediği ve kontrollerin sınırları belirtilir.

### Bölüm C — Root öncesi Oxygen edinimi

1. Oxygen Forensic Detective ve Device Extractor sürümü.
2. Lisans/kurulum durumu.
3. Cihazın uygulamada nasıl tanındığı.
4. Oxygen'in Redmi için gerçekten gösterdiği edinim yöntemleri.
5. Seçilen yöntem ve bunun gerekçesi.
6. Edinim öncesi/sonrası cihaz ekranları.
7. Gerçek başlama ve bitiş zamanı (UTC ve Europe/Istanbul).
8. Çıktı klasörü, edinim günlüğü, özet, hata/uyarı ve hash değerleri.
9. Oxygen tarafından desteklenmeyen veya alınamayan veri türleri.

**Yöntem adı Oxygen arayüzünde ne yazıyorsa aynen kaydedilir.** Android Agent edinimi “tam fiziksel imaj” olarak adlandırılmaz. Araç yalnızca kullanıcı verisi/mantıksal edinim gösteriyorsa rapor da bunu söyler. Full File System, Physical veya Deleted Data desteği varsayılmaz.

### Bölüm D — Root öncesi analiz

Aşağıdaki veri türleri yalnızca Oxygen çıktısında gerçekten görülürse raporlanır:

- Rehber/Contacts
- SMS/MMS/RCS
- Gelen, giden ve cevapsız aramalar
- Takvim
- Fotoğraf/video ve EXIF
- İndirilen dosyalar ve belge metaverisi
- Tarayıcı geçmişi/indirmeler
- Uygulama verileri ve uygulama sürümleri
- Wi-Fi/Bluetooth ve diğer cihaz kayıtları (varsa)
- Zaman çizelgesi
- Dosya sistemi ve mevcut klasörler

Her bulguda kaynak veri türü, kaynak uygulama/veritabanı, kayıt zamanı, saat dilimi, kayıt sayısı, çıkarım yöntemi, ekran/rapor referansı ve sınırlamalar bulunur.

### Bölüm E — Root kararı ve Root işlemi

1. Root öncesi özgün edinim ve hash doğrulaması.
2. Pre-Root kopyasının salt okunur/korunan yedek alana alınması.
3. Cihaz-özel Root uygunluğu ve veri kaybı riski.
4. Kullanılacak dosyaların resmi/güvenilir kaynağı, model/build uyumu, sürümü ve SHA-256 değeri.
5. İşlem öncesi ve işlem sonrası cihaz durumu.
6. İşlemin doğurabileceği açılmama, veri silinmesi ve sistem değişikliği riskleri.
7. Başarısızlık veya veri kaybı olursa bunun dürüstçe kaydı.

**Önemli:** Bu referans PDF'deki Samsung Odin, CF-Auto-Root, SuperSU, TWRP ve Samsung J7 dosyaları Redmi Note 13 Pro için kullanılmaz. Redmi için bir Root yöntemi ancak tam cihaz varyantı, firmware/build, Android güvenlik yaması ve güvenilir kaynakla doğrulanırsa ayrıca değerlendirilebilir. Sadece Bootloader'ın açık olması Root bulunduğu veya Root işleminin risksiz olduğu anlamına gelmez.

### Bölüm F — Root sonrası edinim ve analiz

1. İşlem sonrası cihaz durumu ve Root doğrulaması.
2. Oxygen'in sunduğu Root sonrası edinim yöntemleri.
3. Seçilen yöntemin kapsamı ve araç uyarıları.
4. Ayrı çıktı klasörü ve ayrı edinim kimliği.
5. Gerçek işlem günlükleri, hata/uyarılar ve hash.
6. Gerçekten ek erişim sağlanan veri kategorileri.
7. Root sonrası verinin Pre-Root edinimden farklılaştığı noktalar.
8. Kısıtlamalar ve alternatif açıklamalar.

Bir dosyanın Root sonrası görünmesi, tek başına Root'un onu kurtardığını kanıtlamaz. Uygulama sürümü, senkronizasyon, yeni kullanıcı etkinliği, edinim yöntemi ve zaman farkı dikkate alınır.

### Bölüm G — Karşılaştırma ve sonuç

| Veri kategorisi | Root öncesi | Root sonrası | Fark | Kaynak / açıklama |
|---|---|---|---|---|
| Rehber | Gerçek Oxygen sonucu | Gerçek Oxygen sonucu | Hesaplanacak | Kaynak ve erişim yöntemi |
| SMS/MMS | Gerçek Oxygen sonucu | Gerçek Oxygen sonucu | Hesaplanacak | Kaynak ve erişim yöntemi |
| Arama kayıtları | Gerçek Oxygen sonucu | Gerçek Oxygen sonucu | Hesaplanacak | Kaynak ve erişim yöntemi |
| Medya dosyaları | Gerçek Oxygen sonucu | Gerçek Oxygen sonucu | Hesaplanacak | Orijinal dosya ve EXIF |
| Uygulama verileri | Gerçek Oxygen sonucu | Gerçek Oxygen sonucu | Hesaplanacak | Uygulama/sürüm/kapsam |
| Tarayıcı | Gerçek Oxygen sonucu | Gerçek Oxygen sonucu | Hesaplanacak | Profil ve veri kaynağı |
| Dosya sistemi | Gerçek Oxygen sonucu | Gerçek Oxygen sonucu | Hesaplanacak | Yöntem ve erişim kapsamı |
| Timeline | Gerçek Oxygen sonucu | Gerçek Oxygen sonucu | Hesaplanacak | Saat dilimi ve kaynak |
| Silinmiş veriler | Yalnızca gerçekten bulunursa | Yalnızca gerçekten bulunursa | Hesaplanacak | Kurtarma yönteminin kanıtı |

Sonuç, yalnızca doğrulanmış bulgular ve iki edinimin sınırları üzerinden yazılır. Tek bir mesaj, dosya adı veya uygulama etiketi suçun gerçekleştiğini ya da bir kişiyi tek başına kanıtlamaz.

## 4. Oxygen import ayarları için referans değerlendirmesi

Referans PDF'nin 14–18. sayfalarında zaman dilimi, hash, arşiv ayrıştırma, anahtar kelime arama ve dosya tarama ayarları gösterilir. Kestrel için şu yaklaşım önerilir:

- Zaman dilimi: raporlamada **Europe/Istanbul** ve UTC değeri birlikte korunur.
- Bütünlük: SHA-256 kullanılır; özgün edinim dosyaları değiştirilmez.
- Dosya türleri: proje sorularına göre seçilir; seçilmeyen türler raporda belirtilir.
- Arşiv tarama: yalnızca analiz için gereken düzeyde yapılır; orijinal arşiv korunur.
- Anahtar kelimeler: Kestrel V2 sözlüğü Türkçe karakterlerle ve UTF-8 bağlamında hazırlanır. Sadece ASCII/Windows-1252'ye güvenilmez.
- Zararlı dosya taraması: içe aktarılan materyaller çalıştırılmaz. Tarama aracı/ayarları ve uyarıları kayıt altına alınır.
- Zaman aşımı veya sezgisel tarama ayarı değiştirilirse gerekçe ve etkisi belirtilir.

Bu ayarlar, PDF'de gösterildi diye otomatik olarak en doğru ayarlar sayılmaz. Kullanılan Oxygen sürümünün sunduğu gerçek seçenekler ve proje kapsamı esas alınır.

## 5. Referans PDF'de tespit edilen tutarlılık ve yöntem uyarıları

1. **Cihaz farkı:** Referans rapor Samsung SM-J700H / Android 6.0.1 içindir; Kestrel cihazı Redmi Note 13 Pro / Android 15'tir. Root aracı ve firmware adımları aktarılmaz.
2. **Tarih farkı:** Olay özeti 05.10.2025 tarihini verirken, olay/çıkarım kimliklerinde Kasım 2025 ifadeleri ve 05.11.2025 tarihli çıktı yolu görülür. Kestrel'de olay zamanı, edinim zamanı ve rapor tarihi birbirinden ayrı tutulur.
3. **Edinim terminolojisi:** Android Agent / mantıksal kullanıcı verisi çıkarımı, kendiliğinden Physical Image veya Full File System Image değildir. Kullanılan gerçek yöntem doğru adlandırılır.
4. **Root etkisi:** Root “sistemde kalıcı değişiklik yapmadan” diye tanımlanmaz. Root, cihaz yazılımını değiştirir ve delil durumuna etki edebilir; önce Pre-Root edinim korunur.
5. **Post-Root kapsam iddiası:** Root sonrası daha çok veri görülebilir; ancak otomatik olarak tüm uygulama verileri, silinmiş kayıtlar veya bütün /data içeriği elde edilmiş sayılmaz. Oxygen raporundaki gerçek kapsam temel alınır.
6. **Sonuç dili:** Referans PDF'nin son bölümündeki kesinlik dili Kestrel'e taşınmaz. Bulgular birden çok bağımsız kaynakla desteklenmedikçe “kesin kanıt” olarak adlandırılmaz.
7. **Bulut çıkarımı:** PDF'nin 35–41. sayfalarındaki Facebook oturum dosyası/Access Token ve Cloud Extractor bölümü Kestrel'in temel kapsamı değildir. Token veya parola rapora, GitHub'a veya ekran görüntüsüne açık şekilde konmaz. Bulut incelemesi ancak kendi test hesabı, açık yetki ve ayrı kayıtlı kapsamla değerlendirilir.

## 6. Gizlilik ve rapor paylaşımı

Referans PDF'de kişisel adlar, e-posta/hesap bilgileri ve kısmen maskelenmiş Access Token içeriği bulunan ekranlar yer alır. Dosya açık GitHub deposuna veya herkese açık bağlantıya yüklenmemelidir. Dağıtılacak kopyada bu alanlar güvenli biçimde redakte edilir; orijinal kaynak dosya korunur. Aktif token olma ihtimali varsa hesap sahibi üzerinden geçersizleştirilmesi değerlendirilir.

Kestrel raporunda:
- Gerçek telefon numaraları ve e-posta adresleri maskelenir.
- IMEI ve seri numarası yalnızca gerektiği kadar gösterilir.
- ADB seri numarası ve hesap belirteçleri genel rapordan çıkarılır.
- Ekran görüntülerinde özel mesaj, bildirim, hesap ve kurtarma bilgisi görünmez.
- Vaka materyallerinde gerçek kişilere ait veri kullanılmaz.

## 7. Rapor için ekran görüntüsü ve ek listesi

- [ ] Kapak ve vaka özeti.
- [ ] Redmi cihazının ön/arka/yan/port fotoğrafları.
- [ ] Başlangıç cihaz durumu ve Root kontrolünün gerçek çıktısı.
- [ ] Oxygen sürüm/Lisans ve cihaz algılama ekranı.
- [ ] Oxygen'in sunduğu Root öncesi edinim yöntemleri.
- [ ] Root öncesi edinim tamamlanma ekranı, log ve çıktı klasörü.
- [ ] Pre-Root hash manifesti.
- [ ] Oxygen import vaka bilgileri ve saat dilimi/hash ayarları.
- [ ] Root öncesi artefakt analizi.
- [ ] Root öncesi dosya ve kayıt envanteri.
- [ ] Root işlemine geçiş kararı ve risk kaydı.
- [ ] Root sonrası cihaz durumunun doğrulanması.
- [ ] Root sonrası Oxygen yöntem ve kapsam ekranı.
- [ ] Root sonrası edinim tamamlanma ekranı, log ve hash.
- [ ] Post-Root artefakt analizi.
- [ ] Karşılaştırma tablosu.
- [ ] Sınırlamalar, sonuç ve kaynakça.

Her ekran görüntüsü gerçek işlem oturumundan alınır; ekran maketi veya sonradan üretilmiş arayüz kanıt olarak kullanılmaz.

## 8. Mevcut Kestrel V2 durumu

| İş | Durum |
|---|---|
| V2 sentetik karakter, ileti ve olay CSV'leri | Depoda mevcut; SENTETİK-SENARYO |
| Gerçek SMS/arama test akışı | Kullanıcı tarafından kontrollü ikinci hat ile çalıştırılabilir; henüz gerçek işlem kanıtı sunulmadı |
| Orijinal dış kaynak fotoğraflar | Commons kaynak ve lisans dokümantasyonu/indirme aracı mevcut; indirmenin çalıştığı ayrıca doğrulanmalı |
| Redmi kamerası ile çekilen fotoğraflar | Protokol ve salt okunur aktarım aracı mevcut; çekim/aktarımı henüz doğrulanmadı |
| Root öncesi cihaz durum raporu | Önceki kayıt mevcut |
| Oxygen sürümü ve lisansı | Bu belge hazırlanırken doğrulanmadı |
| Root öncesi Oxygen edinimi | Henüz doğrulanmadı |
| Root işlemi | Başlatılmadı |
| Root sonrası Oxygen edinimi | Yapılmadı |
| Nihai Root öncesi/sonrası karşılaştırma | Gerçek Oxygen çıktıları bekliyor |

**Sonraki gerçek işlem:** Oxygen Forensic Detective sürümünü ve Connected devices ekranında Redmi için sunulan yöntemleri doğrulamak. Bu doğrulama yapılmadan bir edinim türü varsayılmaz ve Root başlatılmaz.
