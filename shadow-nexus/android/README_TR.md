# Shadow Nexus — Android Uygulaması

## Amaç
Operasyon Kestrel için açıkça sentetik olarak işaretlenmiş eğitim materyallerini Android telefonda normal kullanıcı akışlarıyla oluşturur ve kaydeder.

## Gereksinimler
- Android 10 veya üzeri.
- APK, GitHub otomatik derleme iş akışı tarafından oluşturulur.
- Kişi ekleme ve takvim işlemleri Android'in kendi onay ekranlarında kullanıcı tarafından kaydedilir.
- Eğitim dosyaları ortak İndirilenler alanına, görsel ise Resimler alanına kaydedilir.

## APK indirme
1. GitHub deposunda **Eylemler** sekmesini açın.
2. **Android APK derlemesi** iş akışını seçin.
3. **İş akışını çalıştır** düğmesine basın veya son başarılı derlemeyi açın.
4. **shadow-nexus-kestrel-debug-apk** adlı dosya paketini indirin.
5. ZIP arşivinden app-debug.apk dosyasını çıkarın.
6. APK dosyasını telefona aktarın ve Android kurulum ekranında onaylayın.

## Yerel derleme
JDK 17, Android SDK Platform 35 ve Gradle 8.9 gerekir:
```bash
cd shadow-nexus/android
gradle --no-daemon assembleDebug
```

APK dosyası:
app/build/outputs/apk/debug/app-debug.apk

## Güvenlik ve sınırlar
- Uygulama SMS, arama kayıtları, WhatsApp veya Telegram veritabanlarına yazmaz.
- Arama ve SMS izleri yalnızca size ait test cihazları ve SIM üzerinden gerçek kullanıcı işlemleriyle oluşturulmalıdır.
- Sahte GPS veya sistem saati değişikliği yapılmaz.
- Uygulama kişileri doğrudan ve sessizce eklemez; Android Kişi Ekle ekranını açar ve kaydı kullanıcı yapar.
- Takvim etkinliği Android Takvim ekranında kullanıcı onayıyla kaydedilir.
- Tüm senaryo içerikleri SENTETİK-SENARYO olarak sınıflandırılır.
- Uygulama ek depolama izni istemeden Android'in Medya Depolama alanına eğitim belgeleri ve görsel kaydeder.
