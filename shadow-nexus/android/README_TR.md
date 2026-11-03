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


## Kestrel V2 — Türkçe sentetik kişiler ve gerçek iletişim testleri

2.1.0 sürümünde beş Türkçe eğitim kişisi bulunur:
- Deniz Arslan
- Kerem Yılmaz
- Selin Kaya
- Mert Aydın
- Ece Yıldız

Rehber ekranında telefon numarası alanları `0000000001`–`0000000005` biçiminde geçersiz yer tutuculardır. Bunlar arama veya SMS için kullanılmamalıdır. E-posta alanları `example.invalid` kullanır. Kişi kaydı yalnızca Android'in kendi formunda kullanıcı onayıyla yapılır.

### SMS ve çağrı kayıtları

- **SMS test taslağı hazırla**: Türkçe, açıkça SENTETİK-SENARYO işaretli bir metni varsayılan SMS uygulamasında taslak olarak açar. Gönderim işlemini kullanıcı yapar.
- **Arama test ekranını aç**: Varsayılan telefon çeviricisini açar. Aramayı kullanıcı başlatır.
- Gelen SMS ve aramalar ikinci, araştırmacının kontrolündeki test hattından başlatılır.
- Uygulama Telephony veya Call Log veritabanına doğrudan yazmaz; `SEND_SMS`, `WRITE_CALL_LOG` veya arama kaydı izinleri istemez.
- Uygulama günlüğündeki `SMS_TASLAGI_HAZIRLANDI` ve `ARAMA_EKRANI_ACILDI` olayları yalnızca ilgili ekranın açıldığını bildirir; gönderim veya gerçek görüşme kanıtı sayılmaz.

### Gerçek fotoğraflar

Uygulama artık sentetik bir çizimi fotoğraf gibi üretmez. Wikimedia Commons'tan lisans ve provenans bilgisiyle indirilen orijinal JPEG dosyaları aşağıdaki araçlarla telefona aktarılır:

```bash
python3 shadow-nexus/scripts/kestrel-v2-gercek-fotograf-paketi-indir.py
bash shadow-nexus/scripts/kestrel-v2-gercek-fotograflari-telefona-aktar
```

Ardından uygulamada **Aktarılan fotoğrafları Galeri'ye tanıt** düğmesi kullanılır. Fotoğraflar kırpılmaz, yeniden boyutlandırılmaz veya EXIF bilgileri değiştirilmez. Bunların Redmi kamerasıyla çekilmediği ve Kestrel olayının gerçek kanıtı olmadığı raporda açıkça belirtilmelidir.

### Güncelleme

Bu sürüm mevcut kurulum üzerine yüklenebilir; `versionCode=3`, `versionName=2.1.0` olarak artırılmıştır. APK, **Android APK derlemesi** GitHub Actions iş akışının `shadow-nexus-kestrel-debug-apk` çıktısından alınır.
