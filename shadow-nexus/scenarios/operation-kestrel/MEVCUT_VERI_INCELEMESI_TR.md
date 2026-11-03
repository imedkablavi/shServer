# Operation Kestrel — Mevcut Veri İncelemesi ve Tutarlılık Açıkları

## 1. İncelemenin dayanağı

Bu belge, kullanıcının 3 Kasım 2026 tarihinde çalıştırdığı aşağıdaki salt okunur komutların çıktısına dayanır:

- `make -C shadow-nexus test`
- `bash shadow-nexus/scripts/kestrel-vaka-denetle`
- `bash shadow-nexus/scripts/kestrel-senaryo-incele`

İnceleme kapsamındaki ana vaka: `SNX-20261101-043455`.

Bu belge dosyaların mevcut içeriğini yorumlar; telefondan yeni veri almaz, Oxygen çıkarımı yapmaz ve olayların gerçek dünyada gerçekleştiği iddiasında bulunmaz.

## 2. Doğrulanan teknik durum

| Kontrol | Çıktıda doğrulanan sonuç |
|---|---|
| `run_summary.json` | Geçerli JSON |
| Sentetik iletişim SQLite | `PRAGMA integrity_check = ok` |
| Sentetik iletişim tabloları | persons, threads, messages, calls, events, files, case_metadata, provenance |
| Kişi sayısı | 3 |
| Konuşma sayısı | 2 |
| Mesaj sayısı | 8 |
| Simüle çağrı sayısı | 2 |
| Senaryo olayı sayısı | 4 |
| Telefon etkinliği JSON | Geçerli JSON, 12 action |
| Telefon etkinliği SQLite | `PRAGMA integrity_check = ok`, http_events tablosunda 12 kayıt |
| Yerel sunucu günlüğü | 19 boş olmayan JSONL kaydı, bozuk JSON satırı yok |
| Ana SHA-256 manifesti | Bütün listelenen girdiler eşleşti |
| Smoke test | Başarılı |
| Vaka denetimi | BAŞARILI |

Test sırasında görülen `Geçersiz vaka kodu` ve geçersiz port hata satırları, smoke testin beklediği olumsuz doğrulama kontrolleridir. Hash değişikliği testi de geçici dosyada kasıtlı bozulma oluşturup tespit edildiğini doğrular; gerçek vaka dosyasında bozulma gözlenmemiştir.

## 3. Mevcut sentetik iletişim verisinin gerçek içeriği

Mevcut sentetik SQLite veri kümesinde görülen kişiler:

| Kimlik | Görünen ad | Rol |
|---|---|---|
| P-001 | Deniz Arslan | Kurgusal cihaz sahibi |
| P-002 | Kerem Yilmaz | Kurgusal proje irtibatı |
| P-003 | Selin Kaya | Kurgusal BT koordinatörü |

Konuşmaların başlıkları:

- `Project Kestrel coordination`
- `IT handover`

Mesajların içeriği; eğitim arşivinin kontrol edilmesi, paylaşılan klasörde gerçek müşteri verisi bulunmadığının belirtilmesi, dosya adları ve SHA-256 listesinin konuşulması, yerel Bazzite laboratuvar portalına erişim ve gerçek cihaz etkinliği ile senaryo verisinin ayrı raporlanması konularındadır.

Mevcut sekiz mesajda aşağıdaki unsurlara ilişkin bir kayıt bulunmamaktadır:

- Gerçek veya kurgusal SSH oturumu açıldığını belirten bir mesaj,
- Sunucuya yetkisiz erişim sağlandığına ilişkin açık bir mesaj,
- Gerçek kimlik bilgisi veya kimlik bilgisi kullanımı,
- Botnet veya C2 kontrol eylemi,
- Gerçek veri sızdırma işlemi,
- Tor üzerinden fiilî paylaşım,
- Delil silme eylemi.

Bu nedenle mevcut iletişim veri kümesi, önceki görsel taslaktaki saldırı anlatısını desteklemez. Mevcut veri kümesi, güvenli bir laboratuvar koordinasyon konuşmasıdır.

## 4. Gerçek laboratuvar etkinliğinin kapsamı

Telefondan alınan etkinlik JSON ve SQLite kopyalarında 12 HTTP olayı görülmüştür:

- 6 portal/API sayfasına GET isteği,
- 5 eğitim dosyasına GET indirme isteği,
- 1 `POST /api/upload` isteği.

Kayıtlar `127.0.0.1:34871` adresindeki yerel Kestrel portalına yöneliktir. İsteklerin HTTP durumu, byte sayısı ve yanıt SHA-256 değerleri telefon etkinliği SQLite tablosunda tutulmuştur.

Yerel sunucu günlüklerinde 19 kayıt vardır. Bu sayı telefonun 12 action sayısıyla aynı şey değildir. Sunucu günlüğünde tekrar eden sayfa istekleri, `favicon.ico` için bir 404 ve bir POST işlemi de görülmektedir.

**Doğru yorum:** Telefon, kontrollü yerel laboratuvar portalını açmış, eğitim sayfalarını ve dosyalarını istemiş ve bir test yüklemesi yapmıştır.

**Desteklenmeyen yorum:** Bu kayıtlar üçüncü taraf bir sunucuya saldırı yapıldığını, SSH ile sisteme girildiğini veya veri sızdırıldığını kanıtlamaz.

## 5. Zaman çizelgesi tutarsızlıkları

Sentetik iletişim kayıtları 30 Ekim 2026 tarihinde `+03:00` yerel saat dilimiyle oluşturulmuştur. Gerçek telefon portal etkinliği 1 Kasım 2026 tarihinde UTC zaman damgalarıyla kaydedilmiştir.

Örneğin ilk gerçek portal isteği `2026-11-01T01:35:01Z` civarındadır. Bu, `Europe/Istanbul` için `2026-11-01 04:35:01+03:00` saatine karşılık gelir.

Mevcut `Kestrel_Timeline.csv` içindeki dört sentetik olay:

| Olay | Sentetik zaman | Açıklama |
|---|---|---|
| EV-001 | 30.10.2026 14:05 | Senaryo başlangıcı |
| EV-002 | 30.10.2026 16:33 | Sekiz sentetik mesajın bulunduğu konuşma |
| EV-003 | 30.10.2026 15:58 | İki sentetik arama kaydı |
| EV-004 | 30.10.2026 16:45 | Daha sonra üretilecek ayrı HTTP laboratuvar işlemi hakkında açıklama |

Tutarsızlıklar:

1. CSV satır sırası kronolojik değildir: EV-002, EV-003'ten sonra yazılmıştır.
2. EV-004'ün açıklaması ayrı HTTP işleminin o çalışma sırasında üretileceğini söyler; fakat doğrulanmış telefon HTTP kayıtları 1 Kasım'dadır.
3. 30 Ekim sentetik zamanları ile 1 Kasım gerçek laboratuvar zamanları aynı olay zaman çizelgesinde etiket olmadan birleştirilmemelidir.

Bu nedenle nihai analizde `simulated_time_local` ve gerçek edinim zamanları ayrı alanlarda tutulmalı; sentetik olaylar gerçek telefon zaman damgasıymış gibi sunulmamalıdır.

## 6. Önceki görsel taslak ile mevcut veri arasındaki fark

Önceki görsel taslakta şu unsurlar gösterilmiştir:

- Shadow, Kemal (H) ve `root@c2` adları,
- WhatsApp ve Telegram benzeri konuşma ekranları,
- SSH erişimi, Nmap, botnet, C2, Tor, arşivleme ve delil silme iddiaları,
- Gerçek sunucu hedefi gibi görünen IP ve alan adı örnekleri.

Bunlar görsel taslakta bulunan kurgusal anlatı unsurlarıdır. Mevcut SQLite içinde kişi adları Deniz Arslan, Kerem Yilmaz ve Selin Kaya'dır; uygulama etiketi `Shadow Nexus Lab Messenger` şeklindedir. Görseldeki isimlerle mevcut sentetik verideki isimler arasında doğrulanmış bir eşleştirme yoktur.

Bu eşleştirme açıkça kararlaştırılmadan isimlerin aynı kişi olduğu varsayılmamalıdır. Görsel taslak, ekran görüntüsü veya Oxygen çıkarım raporu değildir.

## 7. Dosya envanterindeki eksik metadata

`Kestrel_Asset_Register.csv` içindeki üç varlık satırında `sha256` alanı boştur. Ana vaka manifesti dosyaların mevcut sürümlerini doğrular; ancak Asset Register satırlarının kendi başına içerik hash'i taşıdığı anlamına gelmez.

İleride yeni senaryo sürümü hazırlanırsa her varlık için:
- dosya adı,
- göreli yol,
- boyut,
- MIME türü,
- SHA-256,
- sınıflandırma,
- üretim kaynağı

alanları doldurulmalıdır. Mevcut kanıt dosyaları geriye dönük değiştirilmemelidir.

## 8. Kanıt boşlukları

Mevcut çıktı aşağıdaki kanıtların varlığını göstermemektedir:

- Android Contacts sağlayıcısında özgün kişi kayıtları,
- Android SMS/MMS kayıtları,
- Android Call Log kayıtları,
- WhatsApp veya Telegram uygulamasına ait gerçek yerel konuşma verileri,
- Olayla ilişkili gerçek kamera fotoğrafı/video,
- Olayla ilişkili konum geçmişi,
- Kurgusal saldırı anlatısını destekleyen gerçek cihaz SSH istemcisi geçmişi veya uygulama kalıntıları,
- Üçüncü taraf sunucuya erişim ya da veri dışa aktarımı.

Bu liste, cihazda kesinlikle bu verilerin bulunmadığını kanıtlamaz; yalnızca şimdiye kadar sunulan vaka çıktısının bunları göstermediğini belirtir.

## 9. Önerilen sürümleme kararı

1. `SNX-20261101-043455` mevcut vaka verisi değiştirilmeden korunur.
2. Mevcut sentetik veri kümesi `Kestrel v1 — Laboratuvar koordinasyonu` olarak etiketlenir.
3. Önceki görseldeki sunucu ihlali anlatısı ayrı ve açıkça sentetik bir senaryo taslağı olarak tutulur.
4. İki veri kümesi, kişi eşleştirmesi ve zaman çizelgesi doğrulanmadan tek bir gerçek olay gibi birleştirilmez.
5. İleride Kestrel v2 oluşturulursa yeni vaka/run kimliği kullanılır. V1 dosyaları üzerine yazılmaz.
6. Gerçek uygulama kayıtları gerekiyorsa yalnızca yetkili test hesapları ve uygulamanın normal arayüzleriyle oluşturulur. Uygulama veritabanları elle değiştirilmez.
7. Gerçek laboratuvar trafiği yalnızca araştırmacının kontrol ettiği yerel sunucuya yöneltilir. Gerçek dış hedef, zararlı yazılım, RAT, botnet veya gerçek veri sızdırma kullanılmaz.
8. Root öncesi Oxygen edinimi tamamlanıp çıktıları ve hash'leri korumaya alınmadan Root aşamasına geçilmez.

## 10. Sonuç

Mevcut vaka dosyası teknik açıdan bütünlük denetimlerini geçmiştir. Ancak senaryo içeriği, önceki Operation Kestrel saldırı anlatısıyla henüz tutarlı değildir. Şu anki gerçek telefon etkinliği yalnızca yerel eğitim portalına erişim ve dosya indirme/yükleme işlemlerini doğrulamaktadır.

Bu nedenle mevcut vaka, **başlangıç laboratuvar etkinliği ve senaryo koordinasyon veri kümesi** olarak değerlendirilmeli; sunucu ihlali hikâyesinin tamamlanmış kanıt paketi olarak sunulmamalıdır.
