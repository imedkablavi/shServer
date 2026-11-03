# Operation Kestrel v2 — Tasarım Notu

## Belge durumu

| Alan | Değer |
|---|---|
| Senaryo | Operation Kestrel |
| Tasarım sürümü | V2.0 — TASLAK |
| Tasarım kimliği | KESTREL-SCENARIO-V2-DRAFT |
| Ana vaka V1 | SNX-20261101-043455 |
| Cihaz durumu eki | SNX-20261103-KESTREL-ADDON |
| İnceleme cihazı | Redmi Note 13 Pro / Android 15 |
| İçerik sınıfı | SENTETİK-SENARYO |
| Gerçek Oxygen edinimi | Henüz yapılmadı |

> Bu belge senaryo tasarım paketidir; gerçek edinim vakası, gerçek suç kaydı veya Oxygen raporu değildir. V2 taslak materyalleri V1 vaka klasörüne eklenmemeli ve V1 dosyalarının üzerine yazılmamalıdır.

## 1. V2'nin amacı

V1 paketinde gerçek laboratuvar portalına erişim ve güvenli laboratuvar koordinasyon mesajları vardır. V1'deki sekiz sentetik mesaj, önceki görsel taslaktaki sunucu ihlali anlatısını desteklememektedir.

V2, öğretim görevi için ayrı bir kurgusal olay örgüsü ve buna ait sentetik eğitim materyalleri tanımlar. Amaç; şüpheli yetkisiz erişim, kurum dosyasının dışarı aktarılması ve kayıtların kaldırılmaya çalışılması iddialarını mobil adli bilişim açısından değerlendirmektir.

V2'deki mesajlar ve zaman çizelgesi yalnızca eğitim senaryosudur. Bunlar Android SMS, WhatsApp, Telegram veya arama kaydı değildir. Cihaz üzerinde uygulamanın özel veritabanları elle düzenlenmez; sentetik materyal dosya olarak sunulursa dosya kaynağı açıkça belirtilir.

## 2. V1 ile V2 arasındaki kesin sınır

| İçerik | Sınıflandırma | Kullanım |
|---|---|---|
| V1 sentetik iletişim veritabanı | SENTETİK-SENARYO | Değiştirilmeden korunur; laboratuvar koordinasyonu |
| V1 telefon HTTP günlükleri | GERÇEK-LAB-ETKİNLİĞİ | Yalnızca yerel portal isteklerini gösterir |
| V2 mesaj CSV dosyası | SENTETİK-SENARYO | Kurgusal iletişim örneği |
| V2 olay CSV dosyası | SENTETİK-SENARYO | Kurgusal olay örgüsü |
| Gerçek Redmi cihaz bilgileri | GERÇEK-CIHAZ | ADB/Oxygen çıktısıyla desteklenir |
| Oxygen tarafından gerçekten çıkarılan kayıtlar | ÇIKARILAN-KANIT | Yalnızca gerçek edinimden sonra bu sınıfa alınır |

V1'deki SNX-20261101-043455 klasörü salt okunur kaynak olarak kalır. V2 tasarım dosyaları ayrı dizinde tutulur. V2 için gerçek cihaz edinimi başlatılırsa yeni bir çalışma kimliği ve ayrı edinim klasörü kullanılır.

## 3. Karakter ve takma ad eşleştirmesi

V1 veri kümesindeki kurgusal kişiler:
- P-001 — Deniz Arslan
- P-002 — Kerem Yilmaz
- P-003 — Selin Kaya

Önceki görsel taslakta kullanılan Shadow ve Kemal (H) adları ile bu kişiler arasında doğrulanmış bir eşleştirme yoktur. V2 materyalinde aşağıdaki ilişki yalnızca önerilen eşleştirme olarak kaydedilmiştir:

| Görsel taslak etiketi | Önerilen kayıt | Durum |
|---|---|---|
| Shadow | P-001 Deniz Arslan | ONAY BEKLİYOR |
| Kemal (H) | P-002 Kerem Yilmaz | ONAY BEKLİYOR |
| root@c2 | Kurgusal sistem/hesap etiketi | İnsan kimliği değil; kişiyle eşleştirilmez |
| Selin Kaya | P-003 Selin Kaya | V2 kurgusal BT koordinatörü |

Bu eşleştirme onaylanmadan raporda aynı kişi oldukları kesin ifadeyle yazılmaz. root@c2 bir hesap/etiket olarak kalır; tek başına insan kimliğini göstermez.

## 4. V2 kurgusal olay örgüsü

Senaryoya göre 12–17 Ekim 2026 tarihleri arasında Kestrel Teknoloji Araştırma'ya ait kurgusal test sunucusunda yetkisiz oturum, kurum dosyasının dışarı aktarılması ve bazı notların kaldırılmaya çalışılması iddiaları ortaya çıkar.

Kurgusal iletişim içeriğinde:
1. Yazılı erişim onayı bulunmadığı halde oturum açıldığına ilişkin bir ifade,
2. Sunucu envanterine ilişkin notların toplandığı iddiası,
3. Yetkisiz oturumun günlüklerde göründüğüne ilişkin konuşma,
4. root@c2 etiketinin geçtiği bir not,
5. K-17 arşivinin kurum dışı paylaşım alanına aktarıldığı iddiası,
6. Tor kullanıldığına ilişkin doğrulanmamış ifade,
7. Önceki notları veya konuşma izlerini kaldırmaya çalışma iddiası

yer alır.

Bu mesajların hiçbiri tek başına gerçek bir erişimi, dosya aktarımını, Tor bağlantısını veya silme işlemini ispatlamaz. Gerçek cihaz ve sunucu kayıtlarıyla desteklenmeyen her iddia SENTETİK-SENARYO olarak kalır.

## 5. İki ayrı zaman alanı

V2 olay zamanları, görsel taslaktaki hikâyenin kurgusal yerel zamanlarıdır (+03:00). Gerçek telefon/laboratuvar etkinliği ise gerçekleştiği anda üretilen gerçek zaman damgasıyla ayrı kaydedilir.

- scenario_time_local: yalnızca senaryo için kurgulanmış zaman.
- actual_event_time_utc: gerçek cihaz veya laboratuvar kaydında bulunan zaman.
- acquisition_time_utc: Oxygen ediniminin gerçek başlangıç/bitiş zamanı.

Bu alanlar birbirine dönüştürülerek veya geriye dönük ayarlanarak birleştirilmez. Sistem saati değiştirilmez ve sahte geçmiş cihaz etkinliği oluşturulmaz.

## 6. Güvenli uygulama sınırları

- Gerçek üçüncü taraf IP veya alan adı hedef alınmaz.
- Sunucu erişimi, dosya aktarımı ve olay günlükleri yalnızca kurgusal/statik eğitim materyalinde temsil edilir.
- Gerçek laboratuvar trafiği yalnızca araştırmacının kontrolündeki yerel portalda tutulur.
- RAT, zararlı yazılım, botnet, gerçek C2 veya exploit çalıştırılmaz.
- WhatsApp/Telegram/SMS veritabanları elle oluşturulmaz veya değiştirilmez.
- Sentetik CSV/JSON dosyaları, native uygulama kayıtları olarak tanıtılmaz.
- Gerçek edinim çıktıları ve hash'leri ayrı korunur.

## 7. Öğretim görevi açısından açık kalan konu

Kullanılan Redmi Note 13 Pro'nun yönergede belirtilen “eski telefon” koşulunu karşılayıp karşılamadığı öğretim elemanından teyit edilmelidir. Bu teknik proje dosyası, öğretim elemanının cihaz seçimine ilişkin onayı yerine geçmez.

## 8. Sonraki işlem sırası

1. V2 tasarımındaki takma ad eşleştirmesini kesinleştirmek.
2. Senaryo materyallerini telefona yalnızca normal dosya aktarımı/kullanıcı arayüzü ile yerleştirmek; kaynağı dosya olarak kaydetmek.
3. Root öncesi Oxygen edinimini tamamlamak ve ham çıktıyı/hash'leri korumak.
4. Root işlemini ancak ilk edinim doğrulandıktan sonra değerlendirmek.
5. Root sonrası edinimi ayrı klasöre kaydetmek.
6. İki gerçek Oxygen raporunu aynı kanıt matrisi üzerinden karşılaştırmak.

**Mevcut durum:** V2 senaryo materyali taslağı hazırlandı. Gerçek Oxygen edinimi, Root işlemi ve Root sonrası inceleme henüz yapılmadı.
