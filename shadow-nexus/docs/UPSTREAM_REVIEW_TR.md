# AutoPoD-Mobile Kaynak Kod İncelemesi

Kaynak: https://github.com/FORMOBILE/formobile.eu

## Genel değerlendirme
Araştırma projesinde rehber, telefon araması, tarayıcı, fotoğraf, mesajlaşma, Gmail ve Google Takvim modülleri bulunur. Otomasyon ADB ve arayüz etkileşimine dayanır.

## Doğrudan çalıştırmama nedenleri
- Örnek yapılandırma belirli eski telefon modelleri, seri numaraları ve örnek kişiler içerir; Redmi Note 13 Pro cihazımızla eşleşmez.
- Rehber modülü yeni kayıt eklemeden önce mevcut kişileri silen kod içerir.
- Görsel modülü yerel çalışma dizinini silebilir ve dosya zaman bilgilerini değiştirebilir.
- Arama modülü birden fazla cihaz ve sabit kişi sırası varsayar.
- WhatsApp otomasyonu eski Selenium seçicilerine ve WhatsApp Web arayüzüne bağlıdır.
- GPS sahteleme modülü bu eğitim çalışması için gerekli değildir ve kullanılmayacaktır.
- Kurulum, Google ve WhatsApp oturumları için ek kimlik doğrulama gerektirir.
- Bağımlılık sürümleri eskidir; Android 15 ve güncel Python ile uyumluluk doğrulanmamıştır.

## Karar
AutoPoD-Mobile yalnızca senaryo tasarımı ve araştırma kaynağı olarak kullanılır. Kodları doğrudan telefonda çalıştırılmaz. Shadow Nexus uygulaması Android'in standart kullanıcı ekranlarını kullanır ve verileri açıkça eğitim senaryosu olarak işaretler.
