# Shadow Nexus — Kestrel Eğitim Laboratuvarı

Bazzite Linux yardımcı komutları ve Android uygulaması, Operasyon Kestrel adlı kurgusal mobil adli bilişim senaryosu için hazırlanmıştır.

## Temel bilgiler
- Hedef bilgisayar: Bazzite Linux.
- Hedef telefon: Redmi Note 13 Pro / Android 15; Root erişimi varsayılmaz.
- Önceki vaka: SNX-20261101-043455. Bu modül önceki vaka dosyalarını değiştirmez.
- Yeni ek vaka: SNX-20261103-KESTREL-ADDON.
- Senaryo kişileri, takvim etkinlikleri ve belgeler kurgusaldır.

## Android uygulaması
Android uygulaması Android 10 ve üzeri cihazlara kurulabilir. Derleme yönergesi: android/README_TR.md.

Uygulama:
- eğitim belgelerini İndirilenler klasörüne kaydeder;
- görünür sentetik filigran taşıyan bir eğitim görselini Galeri'ye kaydeder;
- Kişi Ekle ekranını açar; rehbere ekleme kullanıcı onayıyla yapılır;
- Takvim ekranını açar; etkinlik kaydı kullanıcı onayıyla yapılır;
- Bazzite üzerinde çalışan yerel Kestrel portalını açabilir;
- uygulama içindeki işlemler için dışa aktarılabilir bir etkinlik günlüğü oluşturur.

Uygulama SMS, arama kaydı, WhatsApp veya Telegram veritabanlarına yazmaz.

## Bazzite komutları
Depo kök dizininden:
```bash
bash shadow-nexus/snctl yardim
bash shadow-nexus/snctl denetle
bash shadow-nexus/snctl cihaz-bilgisi
bash shadow-nexus/snctl vaka-olustur
bash shadow-nexus/snctl portal-baslat
bash shadow-nexus/snctl durum
bash shadow-nexus/snctl ozet-hash
bash shadow-nexus/snctl hash-dogrula
```

Telefonun Chrome tarayıcısında yerel portalı açmak için bilgisayarda portal başlatıldıktan sonra:
```bash
bash shadow-nexus/scripts/adb-ters-baglantiyi-baslat
```
Telefon adresi: http://127.0.0.1:34871/

Durdurmak için:
```bash
bash shadow-nexus/snctl portal-durdur
bash shadow-nexus/scripts/adb-ters-baglantiyi-durdur
```

## Kanıt sınıflandırması
- GERÇEK-LAB-ETKİNLİĞİ: cihaz, kullanıcı veya yerel sunucu üzerinde gerçekten gözlenen etkinlik.
- SENTETİK-SENARYO: kurgusal eğitim kişileri, olayları ve içerikleri.
- DIŞ-KAYNAK: kaynağı ve lisansı kaydedilmiş dış materyal.
- ÇIKARILAN-KANIT: yalnızca Oxygen raporunda gerçekten görülen kayıt.

Bir dosyanın telefona kaydedilmiş olması, Oxygen tarafından kesinlikle çıkarılabileceği anlamına gelmez. Sonuçlar yalnızca gerçek çıkarım raporuna göre yazılmalıdır.

## Güvenlik
- Telefon üzerinde otomatik silme veya veri tabanı değiştirme işlemi yoktur.
- Sistem saati değiştirilmez; yapay zaman damgaları oluşturulmaz.
- Root, Bootloader kilidi açma veya güvenlik açığı kullanımı yoktur.
- USB edinim aracı bu modülden bağımsızdır; Kestrel için çalıştırılmamalıdır.
