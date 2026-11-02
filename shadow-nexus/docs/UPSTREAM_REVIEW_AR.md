# مراجعة AutoPoD-Mobile upstream

المصدر: https://github.com/FORMOBILE/formobile.eu

## نتيجة القراءة
المشروع البحثي يجمع وحدات contacts وphonecalls وbrowser وpictures وmessenger وGmail وGoogle Calendar، ويعتمد على ADB وأتمتة واجهة الجهاز.

## أسباب عدم التشغيل المباشر
- config upstream يتضمن أجهزة وserials محددة وأمثلة بيانات أشخاص تجريبية؛ لا تطابق إعداد Redmi الحالي.
- contacts_main.py يستدعي delete_contacts قبل الإضافة؛ خطر حذف جهات الاتصال الموجودة.
- pictures/main_pictures.py يحذف مجلد عمل محلياً ويستدعي تعديل timestamps.
- phonecalls/phonecall_main.py يفترض وجود PERSON_B في فهرس ثابت، ويتحكم بالمكالمات عن طريق keyevents؛ التوافق غير مضمون.
- WhatsApp automation تستخدم Selenium وXPath/Web selectors قديمة؛ لا نعتبرها موثوقة أو متوافقة.
- GPS spoofing غير مطلوب لقضيتنا ولا نشغله.
- setup.py يطلب إعداد OAuth وحسابات Google وWhatsApp Web.
- requirements مثبتة بإصدارات قديمة، وبيئة Python الحالية غير متحققة.

## قرار Shadow Nexus
نستفيد من مفهوم السيناريو وتوزيع الوحدات، ولا ننفذ upstream scripts على الهاتف. نستخدم أنشطة Android العادية والاختيارية، مع تصنيف واضح وتوثيق.
