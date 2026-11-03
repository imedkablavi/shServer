# Operation Kestrel — Uygulama ve Karşılaştırmalı İnceleme Planı

## 1. Çalışma ilkeleri

- Ana vaka: `SNX-20261101-043455`.
- Cihaz durumu ek kaydı: `SNX-20261103-KESTREL-ADDON`.
- İnceleme cihazı: Redmi Note 13 Pro, Android 15, ürün kodu `sapphire`.
- Başlangıçta Bootloader açık olarak gözlenmiştir.
- ADB Shell kimliği `uid=2000(shell)` olarak gözlenmiştir.
- `su` komutu ve bilinen Root yöneticisi paketleri kontrolünde bulunamamıştır.
- Bu sonuçlar Root bulunmadığını mutlak olarak kanıtlamaz; raporda kullanılan yöntem açıkça yazılmalıdır.
- Önceki edinim tamamlanmadan Root kurulumu veya başka sistem değişikliği yapılmaz.
- USB adli edinim betiği bu telefon çalışmasının parçası değildir ve çalıştırılmaz.

## 2. Aşama A — Var olan senaryoyu sabitleme

1. `SNX-20261101-043455` klasöründeki mevcut dosyalar envanterlenir.
2. Önceki `run_summary.json`, telefon kaynaklı günlükler ve sunucu erişim günlüğü okunur.
3. Önceki `SHA256SUMS.txt` veya manifest varsa doğrulanır.
4. Telefon kopyası ile bilgisayar kopyası ayrı ayrı incelenir.
5. Dosya içerikleri değiştirilmeden ana kaynak kopya korunur.
6. Yeniden üretim gerekiyorsa bunun yeni bir çalışma olduğu ayrı Run ID ile kaydedilir; önceki çalışma üzerine yazılmaz.

Beklenen önceki dosya konumları (cihaz ve çalışma notlarıyla teyit edilmelidir):

- Bilgisayar: `$HOME/ShadowNexus_Cases/SNX-20261101-043455`
- Telefon: `/sdcard/Download/ShadowNexus_Cases/SNX-20261101-043455`

## 3. Aşama B — Senaryo bütünlüğünü kontrol etme

- Kurgusal karakterler ve konuşmaların yalnızca sentetik kaynaklarda bulunduğu doğrulanır.
- Dosya adları, zaman çizelgesi ve sunucu olayları arasında tutarlılık kontrolü yapılır.
- Gerçek alan adları veya üçüncü taraf IP adresleri hedef olarak kullanılmaz.
- Gerçek sunucu etkinliği yalnızca araştırmacının kontrolündeki yerel laboratuvar ortamında üretilir.
- Botnet, C2, RAT veya exploit çalıştırılmaz; bunlara ilişkin içerik statik ve açıkça sentetik eğitim materyaliyle sınırlıdır.
- Gerçek uygulama kayıtları yalnızca uygulamanın normal kullanıcı arayüzü ve izinli test hesaplarıyla oluşturulabilir; uygulama veritabanları elle düzenlenmez.

## 4. Aşama C — Root öncesi Oxygen incelemesi

Root öncesi aşamanın başlangıç cihaz durumu zaten kaydedilmiştir. Ancak henüz Oxygen edinimi tamamlanmış değildir.

1. Oxygen sürümü ve lisans durumu kaydedilir.
2. Windows'un cihazı algıladığı doğrulanır.
3. Oxygen'in cihaz için gösterdiği desteklenen edinim yöntemleri görüntülenir ve ekran kaydı alınır.
4. Seçilen yöntemin kapsamı, gereksinimleri ve uyarıları kaydedilir.
5. Edinim başlatılmadan önce vaka kimliği, cihaz, tarih-saat ve saat dilimi kaydedilir.
6. Edinim çıktısı Oxygen'in oluşturduğu biçimde korunur.
7. Oxygen raporu, günlükleri, hash bilgileri ve edinim yöntemi saklanır.
8. Başarısız olan veya desteklenmeyen veri türleri ayrıca listelenir.

Oxygen ekranında görünmeyen veya desteklenmeyen bir edinim yöntemi varsayılmaz. Physical veya Full File System edinimi mevcut kabul edilmez.

## 5. Aşama D — Root aşamasına geçiş kararı

Root yalnızca Root öncesi edinim tamamlanıp doğrulanmış kopyası güvenli biçimde saklandıktan, mevcut senaryo verisinin kaybı riski değerlendirildikten ve ders gereksinimleriyle uyum teyit edildikten sonra ele alınır.

Cihazın Bootloader durumu başlangıçta açık gözlenmiştir; bu durum tekrar kilit açma gerekmediğini gösterir, fakat Root bulunduğu anlamına gelmez. Root kurulumu yine de önyükleme imajı, cihaz yapısı ve firmware ile ilgili risk taşır.

Root yöntemi cihazın tam yazılım yapısı ve yama düzeyiyle doğrulanmadan dosya flaşlanmaz. Rastgele boot image, doğrulanmamış Magisk paketi veya bilinmeyen betik kullanılmaz. Root işlemi veri kaybına veya açılmama sorununa neden olursa bu durum kayıt altına alınır ve sonuçlar gizlenmez.

## 6. Aşama E — Root sonrası inceleme

1. Root işleminin yöntemi, sürümü, kullanılan dosyaların kaynağı ve zamanları kaydedilir.
2. İşlem sonrası cihazın açılış, güvenlik ve Root durumu doğrulanır.
3. Senaryo verisinin korunup korunmadığı kontrol edilir.
4. Oxygen'in desteklediği yeni edinim seçenekleri gözlemlenir.
5. Root sonrası edinim ayrı klasöre ve ayrı Run ID ile kaydedilir.
6. İlk edinimin üzerine yazılmaz.
7. İki edinimden çıkan veri kümeleri aynı sorulara göre karşılaştırılır.

## 7. Aşama F — Karşılaştırma

Karşılaştırma, Root sonrası verinin sayıca daha fazla olmasına indirgenmez. Her fark için veri kaynağı ve edinim yöntemi açıklanır.

Örnek karşılaştırma grupları:

- Cihaz ve sistem bilgileri
- Rehber / arama / SMS verileri
- Tarayıcı geçmişi ve indirmeler
- Fotoğraf, video ve belgeler
- Uygulama verileri
- Dosya sistemi ve uygulama konteynerleri
- Zaman çizelgesi
- Silinmiş veri kalıntıları (yalnızca gerçekten bulunursa)
- Edinim hataları ve sınırlamalar

Bir öğenin Root sonrası görünmesi tek başına Root'un onu kurtardığını kanıtlamaz; uygulama sürümü, hesap senkronizasyonu, zaman farkı, edinim yöntemi ve cihaz durumu da değerlendirilir.

## 8. Aşama G — Teslim dosyaları

Önerilen nihai klasör yapısı:

```text
Operation-Kestrel/
├── 00_Vaka-Yonetimi/
│   ├── Vaka-Dosyasi_TR.md
│   ├── Cihaz-Durum-Kaydi/
│   └── Islem-Gunlugu/
├── 01_Senaryo/
│   ├── Karakterler/
│   ├── Olay-Zaman-Cizelgesi/
│   └── Sentetik-Materyaller/
├── 02_Root-Oncesi/
│   ├── Oxygen-Edinim/
│   ├── Raporlar/
│   └── SHA256/
├── 03_Root-Islemi/
│   ├── Yontem/
│   ├── Kullanilan-Dosyalar/
│   └── Ekran-Goruntuleri/
├── 04_Root-Sonrasi/
│   ├── Oxygen-Edinim/
│   ├── Raporlar/
│   └── SHA256/
├── 05_Karsilastirma/
│   ├── Kanit-Matrisi/
│   ├── Zaman-Cizelgesi/
│   └── Sonuclar/
├── 06_Gorsel-Kayitlar/
├── 07_Nihai-Rapor/
└── 08_Sunum/
```

## 9. Fotoğraf ve video kayıt planı

Her önemli aşamada gerçek uygulama ekranı kaydedilir:

- Cihazın ilk tanıtımı ve sürüm bilgisi
- Başlangıç Root kontrolü
- Oxygen sürüm ve cihaz algılama ekranı
- Root öncesi edinim yöntemi ve tamamlanma ekranı
- Oxygen raporu ve hash kayıtları
- Root işlemi öncesi ve sonrası cihaz durumu
- Root sonrası edinim yöntemi ve tamamlanma ekranı
- Karşılaştırma tablosu ve nihai rapor

Görsellerde kişisel hesap, telefon numarası, parola, kurtarma kodu veya özel mesajlar görünüyorsa teslim öncesinde gizlenir. Kayıtlar sonradan üretilmiş ekran maketleri değil, gerçek çalışma oturumundan alınır.

## 10. Şu anki aşama

| İş | Durum |
|---|---|
| Kurgusal Operation Kestrel geçmişi | Önceki çalışma notları ve vaka panosuyla mevcut |
| İlk senaryo veri paketi | Önceki çalışmada oluşturulduğu raporlandı |
| Telefonun gerçek yerel portal etkinliği | Önceki çalışmada üretildiği raporlandı |
| 03.11.2026 cihaz durumu kaydı | Oluşturuldu ve SHA-256 hesaplandı |
| Önceki vaka klasörünün tam envanteri | Yeniden doğrulanmalı |
| Önceki manifestin doğrulanması | Yeniden doğrulanmalı |
| Oxygen sürümü / lisansı | Bilinmiyor |
| Root öncesi Oxygen edinimi | Yapılmadı |
| Root işlemi | Yapılmadı |
| Root sonrası Oxygen edinimi | Yapılmadı |
| Karşılaştırmalı rapor | Henüz hazırlanmadı |

**Bir sonraki pratik adım:** Önceki ana vaka klasörünü yeniden envanterlemek ve manifestini doğrulamak. Bu işlem tamamlanmadan yeni veri üretimi, Root veya Oxygen edinimi başlatılmaz.
