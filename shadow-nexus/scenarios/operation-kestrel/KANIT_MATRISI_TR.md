# Operation Kestrel — Kanıt Matrisi

## Sınıflandırma kuralları

| Etiket | Anlamı |
|---|---|
| GERÇEK-CIHAZ | İncelenen fiziksel telefon veya işletim sistemi hakkındaki doğrudan gözlem |
| GERÇEK-LAB-ETKİNLİĞİ | Kontrollü laboratuvarda gerçekten gerçekleştiği sunucu veya cihaz kaydıyla doğrulanan faaliyet |
| SENTETİK-SENARYO | Kurgusal kişi, olay, mesaj, dosya içeriği veya sunucu olayı |
| ÇIKARILAN-KANIT | Oxygen Forensic tarafından gerçekten elde edilen ve dışa aktarılan kayıt |
| DOĞRULANMADI | Varlığı veya kaynağı henüz bağımsız biçimde doğrulanmamış iddia |

Bir dosyanın telefonda bulunması, uygulama içi kayıt olduğu veya Oxygen tarafından çıkarılabildiği anlamına gelmez. Bir kayıt ancak kaynağı ve edinim yöntemi doğrulanırsa ÇIKARILAN-KANIT olarak sınıflandırılır.

## Mevcut durum ve planlanan inceleme

| Kanıt türü | Şu anki dayanak | Sınıf | Oxygen incelemesinde aranacak | Durum |
|---|---|---|---|---|
| Cihaz modeli ve Android sürümü | ADB getprop çıktısı | GERÇEK-CIHAZ | Cihaz bilgisi ve edinim özeti | Başlangıç bilgisi kaydedildi |
| Bootloader / Verified Boot | ADB getprop çıktısı | GERÇEK-CIHAZ | Araç raporunda destekleniyorsa cihaz durumu | Başlangıç bilgisi kaydedildi |
| Root durumu | ADB shell id, su araması ve bilinen yönetici paketleri kontrolü | GERÇEK-CIHAZ | Edinim yönteminin Root gereksinimi | Root tespit edilmedi; mutlak yokluk kanıtı değildir |
| Cihaz durum raporu | `Root-Oncesi-Cihaz-Durumu.txt` | GERÇEK-LAB-ETKİNLİĞİ | Vaka ekinde rapor olarak korunur | SHA-256 hesaplandı |
| Yerel portal HTTP istekleri | Önceki çalışmanın telefon ve sunucu günlükleri | GERÇEK-LAB-ETKİNLİĞİ | Tarayıcı/uygulama izi bulunup bulunmadığı | Önceki özette raporlandı; ham günlükler yeniden doğrulanmalı |
| Portal dosyası indirmeleri | Önceki çalışmanın dosya listesi | GERÇEK-LAB-ETKİNLİĞİ | İndirilen dosyaların yolu, zamanı ve hash'i | 5 dosya indirildiği raporlandı; yeniden doğrulanmalı |
| Senaryo karakterleri | Kurgusal senaryo dosyaları | SENTETİK-SENARYO | Gerçek kişi kaydıyla karıştırılmamalı | Sentetik |
| 2 konuşma / 8 ileti | `scenario_communications_SIMULATED.sqlite3` | SENTETİK-SENARYO | Yalnızca eğitim veri seti olarak incelenir | WhatsApp/Telegram/SMS kaydı değildir |
| 2 arama olayı | Aynı sentetik veri kümesi | SENTETİK-SENARYO | Android Call Log ile eşitlenmemeli | Gerçek çağrı kaydı değildir |
| Android Contacts | Daha önce kullanıcı tarafından kaydedildiği doğrulanmadı | DOĞRULANMADI | Kişiler veritabanı ve kaynak bilgisi | Henüz doğrulanmadı |
| Android SMS/MMS | Gerçek kayıt doğrulanmadı | DOĞRULANMADI | Mesaj, gönderici/alıcı, zaman, ekler | Henüz doğrulanmadı |
| WhatsApp | Gerçek hesap verisi ve kayıt doğrulanmadı | DOĞRULANMADI | Uygulama sürümü, elde etme yöntemi, kayıt kapsamı | Henüz doğrulanmadı |
| Telegram | Gerçek hesap verisi ve kayıt doğrulanmadı | DOĞRULANMADI | Yerel/uzak veri ayrımı ve elde etme yöntemi | Henüz doğrulanmadı |
| Tarayıcı geçmişi | Gerçek sayfalar açıldı; çıkarımda geçmiş doğrulanmadı | GERÇEK-LAB-ETKİNLİĞİ / DOĞRULANMADI | URL, ziyaret zamanı, profil ve tarayıcı veri kaynağı | Oxygen'de kontrol edilecek |
| Fotoğraf / video | Kestrel olayına ilişkin gerçek kamera kaydı doğrulanmadı | DOĞRULANMADI | EXIF, oluşturulma zamanı, dosya kaynağı | Henüz doğrulanmadı |
| Konum geçmişi | Gerçek GPS kaydı doğrulanmadı | DOĞRULANMADI | Kaynak uygulama, koordinat, zaman hassasiyeti | Henüz doğrulanmadı |
| Wi-Fi / ağ geçmişi | Kestrel olayına ilişkin kayıt doğrulanmadı | DOĞRULANMADI | Mevcut ve desteklenen sistem izleri | Henüz doğrulanmadı |
| Kurgusal SSH / Nmap olayları | Önceki senaryo panosu ve kurgusal zaman çizelgesi | SENTETİK-SENARYO | Gerçek cihaz günlüğü olarak sunulmaz | Kurgusal iddia |
| Kurgusal C2 / botnet / Tor atıfları | Görsel ve senaryo materyali | SENTETİK-SENARYO | Statik bağlam olarak değerlendirilir | Gerçek zararlı faaliyet yok |
| Silme girişimi | Senaryo anlatısındaki iddia | SENTETİK-SENARYO | Cihazda silme işlemi yapılmaz; varsa gerçek kalıntı ayrıca doğrulanır | Gerçek silme kanıtı yok |
| Oxygen çıkarım dosyaları | Henüz edinim yapılmadı | ÇIKARILAN-KANIT değil | Yöntem, günlük, hash, dışa aktarım | Bekliyor |

## Önceki çalışmada raporlanan dosyalar

Aşağıdaki isimler önceki çalışmanın özetinde indirilmiş eğitim dosyaları olarak listelenmiştir:

- `Kestrel_Audit_Notes.txt`
- `Kestrel_Asset_Register.csv`
- `Kestrel_Transfer_Record.csv`
- `Kestrel_Timeline.csv`
- `scenario_seed.json`

Bu adlar tek başına dosyanın mevcut olduğunu, açıldığını veya Oxygen tarafından çıkarıldığını kanıtlamaz. Her dosyanın varlığı, boyutu, konumu ve SHA-256 değeri yeniden envanterlenmelidir.

## Hash ve bütünlük

1. Kaynak dosyalar ilk alındığında SHA-256 hesaplanır.
2. Orijinal edinim çıktıları salt okunur veya erişimi kısıtlı alanda saklanır.
3. Analiz kopyaları ayrı klasörde tutulur.
4. Her kopyalama ve dönüştürme işlemi günlükte belirtilir.
5. Hash eşleşmesi dosya bütünlüğünü destekler; tek başına kaynağın doğruluğunu veya olay iddiasını kanıtlamaz.

## Root öncesi / sonrası karşılaştırma sütunları

Her artefakt için aşağıdaki alanlar doldurulmalıdır:

- Artefakt türü
- Veri kaynağı / uygulama
- Root öncesi edinimde bulundu mu?
- Root sonrası edinimde bulundu mu?
- Kayıt sayısı
- Zaman damgası ve saat dilimi
- Dosya yolu / uygulama konteyneri
- Edinim yöntemi
- Hash değeri
- Araç uyarısı veya hata
- Güven düzeyi ve sınırlamalar

Karşılaştırma aynı vaka içeriğine dayanmalıdır. Root aşamasına geçmeden önce ilk edinim tamamlanmalı ve ilk edinim çıktısı korunmalıdır.
