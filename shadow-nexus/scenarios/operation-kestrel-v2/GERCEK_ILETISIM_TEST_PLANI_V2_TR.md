# Operation Kestrel v2 — Gerçek SMS ve Arama Test Planı

## 1. Amaç ve sınır

Bu plan, laboratuvara ait iki kontrollü hat arasında gerçek kullanıcı işlemleri oluşturarak Android'in standart SMS ve Call Log kayıtlarının oluşmasını sağlar. Mesaj gövdeleri eğitim amacıyla SENTETİK-SENARYO olabilir; ancak gönderim/arama işlemi gerçekten kullanıcı tarafından yapılır.

Bu plan:
- SMS veya arama veritabanına doğrudan satır eklemez.
- Var olmayan geçmiş aramaları/mesajları taklit etmez.
- Üçüncü taraf veya izinsiz numaraları kullanmaz.
- Operatör ücreti doğurabilecek işlemleri otomatik başlatmaz.
- Kullanıcı onayı olmadan mesaj göndermez veya arama yapmaz.

## 2. Gerekli düzen

- İncelenen cihaz: Redmi Note 13 Pro / Android 15.
- İkinci cihaz/hat: araştırmacının kendi kontrolündeki veya açık izin alınmış test hattı.
- İki cihazda da tarih/saat otomatik olmalı; saat geriye alınmamalıdır.
- Numara/hat bilgileri, gerçek raporda kişisel veri olarak korunmalı ve dışa aktarımda maskelenmelidir.

## 3. Kurgusal kişiler ve rehber kayıtları

Shadow Nexus uygulamasındaki beş eğitim kişisi:
- Deniz Arslan [EĞİTİM]
- Kerem Yılmaz [EĞİTİM]
- Selin Kaya [EĞİTİM]
- Mert Aydın [EĞİTİM]
- Ece Yıldız [EĞİTİM]

Bu kişilerin rehbere eklenmesi Android'in Kişi Ekle ekranında kullanıcı onayıyla yapılır. Kişi kartlarındaki `0000000001`–`0000000005` değerleri geçersiz yer tutuculardır; arama veya SMS için kullanılmamalıdır. E-posta adresleri `example.invalid` alan adını kullanır.

Gerçek ikinci test hattı numarası bu kurgusal rehber numaralarından ayrı tutulur. Test numarası uygulamada yalnızca arama/SMS ekranını açmak için kullanıcı tarafından girilir.

## 4. Giden SMS testi

1. Shadow Nexus uygulamasında **SMS test taslağı hazırla** seçeneğini açın.
2. Dört Türkçe eğitim metninden birini seçin.
3. Size ait/izinli ikinci test hattının numarasını yazın.
4. Android'in varsayılan SMS uygulamasının açıldığını kontrol edin.
5. Alıcıyı ve mesaj içeriğini yeniden inceleyin.
6. Yalnızca doğru test hattı olduğundan eminseniz gönder düğmesine siz basın.
7. Gönderim durumunu Mesajlar uygulamasından ve karşı cihazdan doğrulayın.
8. Gerçek oluşma zamanını ve iki cihazın kayıtlarını ayrı ayrı not edin.

Uygulamanın etkinlik günlüğünde `SMS_TASLAGI_HAZIRLANDI` olayı bulunursa bu yalnızca taslağın açıldığını gösterir; SMS'in gönderildiğini kanıtlamaz. Gönderim kanıtı, Mesajlar uygulamasındaki gerçek konuşma ve karşı cihazdaki alım kaydıdır.

## 5. Gelen SMS testi

1. İkinci test cihazında Redmi'ye gönderilecek kısa bir Türkçe eğitim mesajı yazın.
2. Metnin başına veya sonuna `[SENTETİK-SENARYO / LAB TESTİ]` ibaresini ekleyin.
3. Mesajı ikinci cihazın normal mesajlaşma arayüzünden gönderin.
4. Redmi'de mesajın geldiğini, gönderen numarayı ve gerçek zamanı doğrulayın.
5. Mesajı silmeyin veya düzenlemeyin; Root öncesi Oxygen edinimi tamamlanana kadar koruyun.

## 6. Arama testi

Önerilen test sırası:

| Test | İşlem | Beklenen gerçek kayıt |
|---|---|---|
| CALL-001 | Redmi'den ikinci test hattını arayın, 10–20 saniye görüşün | Giden/cevaplanan çağrı |
| CALL-002 | İkinci hattan Redmi'yi arayın, cevaplayın | Gelen/cevaplanan çağrı |
| CALL-003 | İkinci hattan Redmi'yi arayın; Redmi'de reddedin veya cevaplamayın | Cevapsız/yanıtlanmayan çağrı; operatör ve dialer davranışına bağlı |
| CALL-004 | Redmi'den ikinci hattı arama ekranını açın; aramayı başlatmadan kapatın | Her cihazda iptal edilmiş aramanın görünmesi garanti değildir |

Her testten sonra her iki cihazın Telefon uygulamasındaki çağrı geçmişini kontrol edin. Süre ve çağrı türünün işletim sistemi tarafından kaydedilmesine izin verin. Zaman damgalarını sonradan değiştirmeyin.

## 7. WhatsApp / Telegram

Bu uygulamalarda veri tabanına elle kayıt yazılmaz. Uygulama içi gerçek konuşma izi gerekiyorsa yalnızca araştırmacının kendi kontrolündeki test hesapları arasında normal kullanıcı arayüzünden mesaj gönderin. Her iki uçta da konuşma ve zaman doğrulanmadan kayıt var kabul edilmez.

Bu proje şu aşamada native WhatsApp/Telegram veri tabanı oluşturduğunu iddia etmez. Oxygen'de gerçekten elde edilen kapsam ayrıca raporlanır.

## 8. Hash, günlük ve zincir

- SMS/arama işlemlerinin yaklaşık plan zamanı değil, cihazlarda görülen gerçek zamanları kaydedilir.
- Shadow Nexus uygulama günlüğü yalnızca kendi açtığı taslak/çevirici ekranlarını kaydeder; gerçek gönderim veya arama kanıtı olarak tek başına kullanılmaz.
- Oxygen ediniminden önceki mesajlar ve çağrı kayıtları silinmez.
- Root öncesi edinim çıktısı ayrı klasöre kaydedilir; SHA-256 doğrulanır.
- Root sonrası edinim ayrı çalışma kimliğiyle yapılır ve ilk edinimin üzerine yazılmaz.

## 9. Güvenlik ve raporlama

- Yalnızca kendi kontrolünüzdeki numaraları kullanın.
- Gerçek operatör ücretleri oluşabilir; veri/roaming ücretlerine dikkat edin.
- Kurgusal kişi numaraları olan 0000000001–0000000005 değerlerini aramayın.
- Gerçek SMS/arama kaydı ile SENTETİK-SENARYO mesaj gövdesini ayrı tanımlayın.
- Gerçek tarih/saat ve karşı cihaz doğrulaması olmadan geçmiş zamanlı iletişim iddiası yazmayın.

**Sınıflandırma:** Telefonun SMS/çağrı kayıtları GERÇEK-LAB-ETKİNLİĞİ; mesaj metninin kurgusal içeriği SENTETİK-SENARYO olabilir. Bu iki sınıf raporda birlikte fakat açıkça ayrılmış şekilde gösterilir.
