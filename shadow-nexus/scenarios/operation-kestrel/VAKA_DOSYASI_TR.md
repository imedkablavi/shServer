# Operation Kestrel — Vaka Dosyası

## 1. Belge kimliği

| Alan | Değer |
|---|---|
| Proje | Shadow Nexus |
| Senaryo | Operation Kestrel |
| Ana vaka kimliği | `SNX-20261101-043455` |
| Ek cihaz-durum kaydı | `SNX-20261103-KESTREL-ADDON` |
| Senaryo türü | Kurgusal sunucu ihlali ve mobil cihaz incelemesi |
| İnceleme cihazı | Xiaomi Redmi Note 13 Pro, Android 15 |
| Çalışma dili | Türkçe |
| Veri sınıflandırması | SENTETİK-SENARYO + GERÇEK-LAB-ETKİNLİĞİ |

> Bu dosya, önceki Operation Kestrel çalışmasını düzenler. Yeni ve bağımsız bir suç hikâyesi oluşturmaz. Görsel taslakta geçen `DF-2026-041` etiketi doğrulanmış ana vaka kimliği değildir; dosya ve araçlarda kullanılacak kanonik kimlik `SNX-20261101-043455` olarak korunur.

## 2. Vaka özeti

Kurgusal Kestrel Teknoloji Araştırma şirketi, geliştirme ve test ortamında bulunan bir sunucuda yetkisiz erişim şüphesi bildirmiştir. Olayın ardından sunucuya ait bazı denetim kayıtlarında olağan dışı oturumlar, dosya envanterinde beklenmeyen değişiklikler ve bir arşiv dosyasının dışarı aktarılmış olabileceğine ilişkin göstergeler bulunduğu iddia edilmektedir.

İncelemenin odağındaki Redmi telefonun, olayla ilişkili kişiler arasındaki iletişim, dosya paylaşımı, web erişimi ve laboratuvar etkinliklerine ilişkin izleri barındırdığı varsayılmaktadır. Telefon tek başına olayın gerçekleştiğini kanıtlamaz. Bulgular; telefon kayıtları, kurgusal sunucu kayıtları ve kontrollü laboratuvarda gerçekten üretilen etkinlikler arasında ilişki kurularak değerlendirilecektir.

Sunucu, alan adları, kullanıcı adları, dosyalar, mesaj içerikleri ve olay örgüsü eğitim amacıyla kurgulanmıştır. Gerçek sistemlere erişim, gerçek veri sızdırma, zararlı yazılım veya botnet faaliyeti bu çalışmanın parçası değildir.

## 3. Soruşturmanın amaçları

1. Telefonun olayla bağlantılı olabilecek iletişim ve dosya izlerini belirlemek.
2. Tarayıcı, indirme ve uygulama verilerinin hangi bölümünün erişilebilir olduğunu incelemek.
3. Telefonda bulunan verileri, eğitim senaryosuna ait sunucu kayıtlarıyla zaman ve içerik bakımından karşılaştırmak.
4. Root öncesi ve Root sonrası incelemelerde erişilebilen veri kümelerindeki farkları ölçmek.
5. Her bulgunun kaynağını ve sınıflandırmasını koruyarak yeniden üretilebilir bir rapor hazırlamak.
6. Elde edilemeyen veya doğrulanamayan verileri açıkça raporlamak.

## 4. Kurgusal ortam ve varlıklar

| Varlık | Açıklama | Sınıflandırma |
|---|---|---|
| Kestrel Teknoloji Araştırma | Kurgusal kurum | SENTETİK-SENARYO |
| `kestrel-srv-01` | Yalnızca laboratuvarda temsil edilen sunucu | SENTETİK-SENARYO |
| `kestrel-lab.test` | Dokümantasyon için ayrılmış örnek alan adı | SENTETİK-SENARYO |
| `K-17` | Kurgusal prototip ve denetim dosyası kümesi | SENTETİK-SENARYO |
| Redmi Note 13 Pro | Gerçek inceleme cihazı | GERÇEK-CIHAZ |
| Yerel Kestrel portalı | Bazzite üzerinde çalıştırılan kontrollü eğitim portalı | GERÇEK-LAB-ETKİNLİĞİ |

Gerçek dış ağ hedefi kullanılmaz. Dokümantasyondaki örnek adresler çalıştırılabilir hedef olarak değerlendirilmez. Gerçek ağ etkinliği yalnızca araştırmacının kontrolündeki yerel laboratuvarla sınırlıdır.

## 5. Kişiler ve hesaplar

| Kimlik | Rol | Değerlendirme notu |
|---|---|---|
| Shadow | İncelenen telefonun kurgusal kullanıcısı | Takma addır; gerçek kişiyle ilişkilendirilmez |
| Kemal (H) | Kurgusal iletişim kişisi | Mesajlaşma senaryosunda görünür |
| `root@c2` | Kurgusal sistem/operatör hesabı | İnsan kimliği olarak kabul edilmez |
| Kestrel Sistem Sorumlusu | Kurgusal sunucu sorumlusu | Olay kayıtlarının bağlamını sağlar |

Kişi isimleri ve hesaplar eğitim verisidir. Rehber veya mesaj uygulamalarında gerçek kayıt olarak görünmeleri ancak ilgili kayıt kullanıcı arayüzünde gerçekten oluşturulmuş ve daha sonra bağımsız olarak doğrulanmışsa raporlanabilir.

## 6. Olay anlatımı

Senaryoya göre 12–17 Ekim 2026 tarihleri arasında Kestrel laboratuvar sunucusunda şüpheli etkinlikler meydana gelmiştir. İlk aşamada iletişim kanallarında sunucuya erişim ve denetim kayıtlarının incelenmesi hakkında konuşmalar yer alır. Daha sonraki aşamalarda kurgusal tarama, uzak oturum, arşiv hazırlama ve veri paylaşımı göstergeleri ortaya çıkar. Son aşamada bazı kayıtların silinmeye çalışıldığı iddia edilir.

Bu anlatı, araştırmacıya hazır bir fail veya kesin suç sonucu vermez. İnceleme; şu alternatifleri açık tutmalıdır:

- H1: Telefon kullanıcısı, yetkisiz sunucu erişimiyle ilişkili faaliyetlere katılmış olabilir.
- H2: Telefonda görülen içerik, yalnızca konuşma, araştırma veya laboratuvar çalışması olabilir; gerçek erişimi kanıtlamaz.
- H3: Cihazdaki bir kayıt başka kullanıcıya, başka cihaza veya senkronize bir hesaba ait olabilir.
- H4: Zaman damgaları, saat dilimi, senkronizasyon veya uygulama aktarımı nedeniyle farklılık gösterebilir.

Hiçbir hipotez, tek bir mesaj veya dosya adına dayanarak doğrulanmış kabul edilmez.

## 7. Kurgusal olay zaman çizelgesi

Aşağıdaki zamanlar senaryo yazımına aittir. Bunlar telefondan veya sunucudan elde edilmiş gerçek zaman damgaları değildir.

| Tarih ve saat (TR) | Kurgusal olay | Beklenen ilişkilendirilebilir iz |
|---|---|---|
| 12.10.2026 20:14 | İlk iletişim | Mesaj senaryosu / kişi ilişkisi |
| 13.10.2026 01:37 | Sunucu hakkında keşif konuşması | Tarayıcı kaydı veya kurgusal not |
| 14.10.2026 18:22 | SSH oturumuna ilişkin iddia | Kurgusal denetim kaydı ve iletişim |
| 15.10.2026 22:10 | C2 altyapısından söz edilmesi | Kurgusal Telegram konuşması / statik not |
| 16.10.2026 03:41 | Arşiv hazırlama ve aktarım iddiası | Dosya adı, indirme veya kurgusal sunucu kaydı |
| 16.10.2026 04:12 | Tor üzerinden paylaşım iddiası | Kurgusal tarayıcı/iletişim izi |
| 17.10.2026 10:28 | Kayıtların silinmesine ilişkin iddia | Kurgusal olay notu; cihazda silme işlemi yapılmaz |

Gerçek laboratuvar etkinlikleri bu tarihleri taklit etmek için geriye dönük oluşturulmaz. Telefonun yerel portala yaptığı gerçek istekler, kendi gerçek oluşma zamanlarıyla ayrı kaydedilir.

## 8. İnceleme soruları

1. Telefonda Kestrel senaryosuyla ilişkili kişi, hesap veya dosya izleri var mı?
2. İlgili dosyalar gerçekten cihazda mevcut mu; yolları, boyutları ve SHA-256 değerleri nedir?
3. Tarayıcı geçmişi ve indirme kayıtları mevcutsa hangi gerçek zamanları ve URL'leri gösteriyor?
4. Mesajlaşma içeriği gerçek uygulama kaydı mı, dışa aktarım mı, yoksa açıkça etiketlenmiş sentetik eğitim verisi mi?
5. Telefonla laboratuvar sunucusu arasında gerçek bir bağlantı kuruldu mu? Bu iddia hangi iki bağımsız kayıtla doğrulanabiliyor?
6. Root öncesi ve sonrası elde edilen veri kümeleri arasında hangi doğrulanabilir farklar var?
7. Hangi veri türlerine erişilemedi ve bunun teknik nedeni nedir?

## 9. Mevcut uygulama durumu

Önceki çalışmada `SNX-20261101-043455` için bir eğitim paketi oluşturulmuş ve telefondan yerel laboratuvar portalına gerçek HTTP etkinliği üretilmiştir. Önceki çalışma notunda şu sayılar raporlanmıştır:

- Telefon kaynaklı HTTP istekleri: 12
- Laboratuvar sunucusunda gözlenen istekler: 19
- Android tarayıcısına gönderilen VIEW intentleri: 5
- Tarayıcı User-Agent içeren istekler: 6
- Önceki paket manifestindeki SHA-256 girdileri: 42
- Sentetik iletişim veri kümesi: 3 kişi, 2 konuşma, 8 ileti ve 2 kurgusal arama olayı

Bu sayılar önceki çalışmanın özetinden alınmıştır; mevcut dosyalar üzerinde yeniden doğrulanmadan nihai raporda doğrulanmış sonuç olarak sunulmaz.

Sentetik iletişim veritabanı `scenario_communications_SIMULATED.sqlite3` gerçek Android SMS, arama kaydı, WhatsApp veya Telegram veritabanı değildir. Telefonun yerel portala yaptığı HTTP istekleri ise gerçek laboratuvar etkinliğidir; gerçek bir sunucu ihlali değildir.

## 10. Kapsam dışı işlemler

- Gerçek üçüncü taraf sunucularını taramak veya test etmek.
- Gerçek kimlik bilgilerini toplamak veya kullanmak.
- Gerçek zararlı yazılım, RAT, botnet veya C2 altyapısı çalıştırmak.
- Gerçek veri sızdırmak veya Tor üzerinden gerçek dosya paylaşmak.
- WhatsApp, Telegram, SMS veya arama kayıtlarının dahili veritabanlarını elle değiştirmek.
- Kurgusal zamanları gerçek cihaz zamanları gibi sunmak.
- Root öncesi çıkarımı tamamlamadan cihaz durumunu değiştirmek.

## 11. Başarı ölçütleri

- Senaryo, karakter ve olay zaman çizelgesi kendi içinde tutarlı olmalıdır.
- Her artefakt kaynak ve sınıflandırma etiketi taşımalıdır.
- Gerçek cihaz etkinliği ile sentetik içerik birbirinden ayrılmalıdır.
- Root öncesi inceleme sonuçları değiştirilemez ayrı bir klasörde korunmalıdır.
- Root sonrası inceleme aynı analiz sorularına göre karşılaştırılmalıdır.
- Bulgular, sınırlamalar ve çıkarılamayan veriler rapora dahil edilmelidir.
- Sunum görüntüleri kullanıcının kendi gerçek uygulama oturumundan alınmalıdır.
