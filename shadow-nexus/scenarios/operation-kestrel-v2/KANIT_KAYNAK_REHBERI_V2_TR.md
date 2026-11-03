# Operation Kestrel v2 — Kanıt Kaynağı ve Kullanım Rehberi

## 1. Materyallerin sınıflandırılması

| Dosya | Sınıf | Ne gösterir? | Ne göstermez? |
|---|---|---|---|
| KARAKTERLER_V2_SIMULATED.csv | SENTETİK-SENARYO | Kurgusal kişi ve hesap kayıtlarını | Gerçek Android rehberini |
| MESAJLASMA_V2_SIMULATED.csv | SENTETİK-SENARYO | Kurgusal mesaj metinlerini ve senaryo zamanlarını | SMS, WhatsApp veya Telegram kayıtlarını |
| OLAY_ZAMAN_CIZELGESI_V2_SIMULATED.csv | SENTETİK-SENARYO | Kurgusal olay örgüsünü | Cihazın gerçek etkinlik zaman çizelgesini |
| Gerçek ADB cihaz raporu | GERÇEK-CIHAZ | Komut çalıştırıldığında gözlenen cihaz durumunu | Root'un mutlak yokluğunu veya geçmiş olayları |
| Oxygen edinim raporu | ÇIKARILAN-KANIT | Oxygen'in gerçekten elde ettiği kayıtları | Araçta gösterilmeyen/çıkarılmayan verileri |

## 2. Telefonda kullanma ilkesi

Sentetik materyaller telefona normal dosya aktarımı veya uygulamanın normal kullanıcı arayüzü üzerinden dosya olarak yerleştirilebilir. Dosyanın telefonda bulunması, onu native mesaj, kişi, arama kaydı veya uygulama veritabanı kaydı haline getirmez.

Bu nedenle:
- CSV/JSON/Metin materyalleri dosya olarak korunur.
- Uygulama iç veritabanları elle düzenlenmez.
- WhatsApp veya Telegram arayüzünde sahte konuşma oluşturularak gerçek uygulama kaydı izlenimi verilmez.
- Android SMS/Call Log sağlayıcıları üzerinde doğrudan SQL veya içerik sağlayıcı yazımı yapılmaz.
- Dosyanın adı, yolu, boyutu, aktarım zamanı ve SHA-256 değeri kaydedilir.
- Oxygen raporunda bu öğe dosya olarak görünürse kaynak sınıfı yine SENTETİK-SENARYO olarak kalır.

## 3. Gerçek cihaz ve laboratuvar etkinliği

Gerçek cihaz durumu ve gerçek laboratuvar işlemleri sentetik olay zamanlarından ayrı tutulur.

- Cihaz modeli, Android sürümü, güvenlik yaması ve Bootloader bilgisi gerçek ADB çıktısından kaydedilir.
- Root durumu, kullanılan kontrol komutlarıyla birlikte raporlanır. Şu anki başlangıç kontrolleri Root tespit etmemiştir; bu, bütün olası Root yöntemlerinin mutlak olarak bulunmadığını kanıtlamaz.
- Yerel portal etkinliği yalnızca 127.0.0.1 üzerinden ve araştırmacının kontrol ettiği laboratuvar ortamında yapılır.
- Sunucu günlükleri, telefon etkinliği ve gerçek edinim zamanları ham kaynaklarından korunur.
- V2'nin 12–17 Ekim 2026 tarihleri yalnızca senaryo zamanlarıdır; gerçek cihaz saatleri geriye alınmaz.

## 4. Oxygen incelemesi için kontrol listesi

Root öncesi edinim:
- [ ] Oxygen sürümü ve lisans durumu kaydedildi.
- [ ] Cihaz modeli ve Android sürümü doğrulandı.
- [ ] Oxygen'in sunduğu edinim yöntemi ve kapsamı kaydedildi.
- [ ] Başlangıç/bitiş zamanları ve saat dilimi kaydedildi.
- [ ] Orijinal edinim çıktısı değiştirilmeden saklandı.
- [ ] Oxygen raporu ve işlem günlükleri korundu.
- [ ] SHA-256 değerleri hesaplandı ve tekrar doğrulandı.
- [ ] Desteklenmeyen veya elde edilemeyen veri türleri not edildi.

Root sonrası edinim:
- [ ] Root işleminin yöntemi ve kullanılan dosyalar belgelendi.
- [ ] Cihazın işlem sonrası durumu doğrulandı.
- [ ] Ayrı bir vaka/çalışma kimliği kullanıldı.
- [ ] Root sonrası Oxygen edinimi ayrı klasöre kaydedildi.
- [ ] Root öncesi edinimle aynı veri türleri karşılaştırıldı.
- [ ] Her farkın edinim yöntemi ve olası alternatif açıklaması değerlendirildi.

Root öncesi edinim doğrulanmadan Root işlemi başlatılmaz.

## 5. Raporlama dili

Doğru:
- “V2 senaryo CSV dosyasında Deniz Arslan adına kurgusal bir mesaj bulunmaktadır.”
- “Oxygen dışa aktarımında belirtilen dosya yolu altında CSV materyali görülmüştür.”
- “Oxygen raporunda WhatsApp verisi elde edildiği gösterilmemiştir.”
- “Kurgusal mesaj, kurum dışına arşiv aktarıldığını iddia etmektedir; gerçek aktarım bağımsız kayıtla doğrulanmamıştır.”

Yanlış:
- “WhatsApp mesajı bulundu” — kaynak gerçekten WhatsApp değilse.
- “Telegram C2 hesabı tespit edildi” — yalnızca statik root@c2 metni varsa.
- “Veri sızdırıldığı kanıtlandı” — yalnızca sentetik ileti varsa.
- “Silinmiş mesajlar kurtarıldı” — gerçek kurtarma çıktısı yoksa.

## 6. Öğretim teslimi

Ekran görüntüsü ve video, gerçek cihaz ve gerçek Oxygen oturumundan alınmalıdır. Senaryo CSV/JSON dosyaları ekran maketi gibi sunulmamalı; sentetik materyal olarak açıklanmalıdır. Öğretim elemanının “eski telefon” koşuluna ilişkin onayı ayrıca alınmalıdır.
