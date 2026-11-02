# Shadow Nexus — Evidence Lab (Bazzite)

> مختبر أدلة تدريبية لـ Operation Kestrel. لا يغيّر ملفات القضايا السابقة ولا يكتب إلى الهاتف إلا عبر خطوة منفصلة وصريحة.

## الحالة
- البيئة المستهدفة: Bazzite Linux / Bash / ADB.
- الهاتف المرجعي: Redmi Note 13 Pro / Android 15؛ لا نفترض Root.
- القضية الأصلية تبقى كما هي: SNX-20261101-043455.
- أي بيانات مولدة تصنف بوضوح إلى ACTUAL-LAB-ACTIVITY أو SYNTHETIC-SCENARIO.
- هذه الأدوات لا تنشئ سجلات WhatsApp/Telegram/SMS مزيفة ولا تعدل قواعد بيانات التطبيقات.

## الأوامر
من جذر المستودع:
```bash
./shadow-nexus/snctl help
./shadow-nexus/snctl doctor
./shadow-nexus/snctl device-info
./shadow-nexus/snctl case-init
./shadow-nexus/snctl portal-start
./shadow-nexus/snctl portal-stop
./shadow-nexus/snctl case-status
./shadow-nexus/snctl hash
./shadow-nexus/snctl verify
```

ابدأ بـ `doctor` ثم `device-info`. لا تشغّل بوابة الهاتف قبل قراءة `docs/SAFETY_AND_CLASSIFICATION_AR.md`.

## تخطيط الملفات
- `cases/`: قضية الإضافة الجديدة فقط، دون لمس القضية السابقة.
- `portal/`: بوابة محلية ومحتوى السيناريو.
- `scripts/`: فحوصات وأوامر Bazzite.
- `docs/`: التصنيف والإجراءات.
- `manifests/`: جرد وبصمات SHA-256.

## ضمانات
- لا يوجد أمر يمسح الهاتف أو يفتح Bootloader أو يطلب Root.
- لا يوجد حذف تلقائي لجهات الاتصال أو ملفات المستخدم.
- لا تُضبط أوقات النظام ولا تُعدّل timestamps لتزوير Timeline.
- استيراد جهات الاتصال/التقويم يدوي واختياري ويجب توثيقه كبيانات تدريبية.
- سجل البوابة يثبت طلبات HTTP التي وصلت إلى خادم المختبر فقط؛ لا يثبت وحده سجل Chrome الداخلي.
- SHA-256 يثبت تطابق بايتات الملف منذ حساب الهاش، ولا يثبت أصالة مصدره.

## AutoPoD-Mobile
المشروع upstream مرجع بحثي فقط. لا نشغّل سكربتاته مباشرة: تتضمن افتراضات أجهزة متعددة وتغييرات/حذف بيانات قد لا تناسب Redmi أو Android 15. راجع `docs/UPSTREAM_REVIEW_AR.md`.
