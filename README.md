# shServer ve Shadow Nexus Laboratuvarı

Bu depo, çıkarılabilir ortamların adli kopyalanması için USB araçlarını ve ayrı bir Android eğitim modülünü içerir.

## Shadow Nexus — Operasyon Kestrel

Türkçe Android uygulaması ve Bazzite yardımcı araçları: [Shadow Nexus kılavuzu](shadow-nexus/README_TR.md).

### Android uygulamasını edinme
1. GitHub deposunda **Eylemler** sekmesini açın.
2. **Android APK derlemesi** iş akışını seçin.
3. **İş akışını çalıştır** düğmesiyle derlemeyi başlatın veya son başarılı derlemeyi açın.
4. **shadow-nexus-kestrel-debug-apk** adlı dosya paketini indirin.
5. ZIP içinden app-debug.apk dosyasını çıkarın; Android telefona aktarın ve kurun.

Uygulama Android 10 ve üzerini hedefler. Rehber ve takvim kayıtları Android sistem ekranlarında kullanıcı onayıyla tamamlanır. Eğitim dosyaları ortak depolama alanına kaydedilir.

## Bazzite yardımcı komutları
```bash
bash shadow-nexus/snctl yardim
bash shadow-nexus/snctl denetle
bash shadow-nexus/snctl cihaz-bilgisi
bash shadow-nexus/snctl vaka-olustur
bash shadow-nexus/snctl portal-baslat
```

## Ayrı tutulan USB iş akışı
Mevcut scripts/usb-forensics aracı telefon eğitim modülünden bağımsızdır. Bu araç hedef USB sürücüyü yeniden biçimlendirme ve silme adımları içerir; Shadow Nexus için çalıştırılmamalıdır. Kendi yönergeleri ve açık onayları olmadan başlatmayın.

## Kanıt sınıflandırması
- GERÇEK-LAB-ETKİNLİĞİ: cihazda veya yerel sunucuda fiilen gözlenen etkinlik.
- SENTETİK-SENARYO: kurgusal eğitim kişileri, olayları ve belgeleri.
- DIŞ-KAYNAK: lisansı ve kaynağı kaydedilmiş, dışarıdan alınan materyal.
- ÇIKARILAN-KANIT: yalnızca Oxygen raporunda gerçekten görülen kayıt.

## Operation Kestrel — Vaka Belgeleri

Önceki sunucu ihlali senaryosunun devamı ve Root öncesi / Root sonrası karşılaştırmalı inceleme planı:
- [Vaka dosyası](shadow-nexus/scenarios/operation-kestrel/VAKA_DOSYASI_TR.md)
- [Kanıt matrisi](shadow-nexus/scenarios/operation-kestrel/KANIT_MATRISI_TR.md)
- [Uygulama ve karşılaştırma planı](shadow-nexus/scenarios/operation-kestrel/UYGULAMA_PLANI_TR.md)

Bu belgeler kurgusal olayları gerçek laboratuvar etkinliğinden ve Oxygen tarafından gerçekten çıkarılan kanıtlardan ayırır.
