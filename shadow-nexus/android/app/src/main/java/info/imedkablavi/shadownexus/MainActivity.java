package info.imedkablavi.shadownexus;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.Color;
import android.net.Uri;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.os.Environment;
import android.provider.CalendarContract;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.View;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.OutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class MainActivity extends Activity {
    private final ArrayList<String> activityEvents = new ArrayList<>();
        private static final String PORTAL_URL = "http://127.0.0.1:34871/";

    private static class TrainingContact {
        final String name;
        final String company;
        final String note;
        final String phone;
        final String email;

        TrainingContact(String name, String company, String note, String phone, String email) {
            this.name = name;
            this.company = company;
            this.note = note;
            this.phone = phone;
            this.email = email;
        }
    }

    private final TrainingContact[] contacts = new TrainingContact[] {
        new TrainingContact("Deniz Arslan [EĞİTİM]", "Kestrel Eğitim Laboratuvarı",
            "SENTETİK-SENARYO: Kurgusal kişi. Telefon alanı geçersiz yer tutucudur; arama/SMS için kullanmayın.",
            "0000000001", "deniz.arslan@example.invalid"),
        new TrainingContact("Kerem Yılmaz [EĞİTİM]", "Kestrel Eğitim Laboratuvarı",
            "SENTETİK-SENARYO: Kurgusal kişi. Telefon alanı geçersiz yer tutucudur; arama/SMS için kullanmayın.",
            "0000000002", "kerem.yilmaz@example.invalid"),
        new TrainingContact("Selin Kaya [EĞİTİM]", "Kestrel Eğitim Laboratuvarı",
            "SENTETİK-SENARYO: Kurgusal kişi. Telefon alanı geçersiz yer tutucudur; arama/SMS için kullanmayın.",
            "0000000003", "selin.kaya@example.invalid"),
        new TrainingContact("Mert Aydın [EĞİTİM]", "Kestrel Eğitim Laboratuvarı",
            "SENTETİK-SENARYO: Kurgusal kişi. Telefon alanı geçersiz yer tutucudur; arama/SMS için kullanmayın.",
            "0000000004", "mert.aydin@example.invalid"),
        new TrainingContact("Ece Yıldız [EĞİTİM]", "Kestrel Eğitim Laboratuvarı",
            "SENTETİK-SENARYO: Kurgusal kişi. Telefon alanı geçersiz yer tutucudur; arama/SMS için kullanmayın.",
            "0000000005", "ece.yildiz@example.invalid")
    };

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        getWindow().setStatusBarColor(Color.rgb(15, 35, 55));
        getWindow().setNavigationBarColor(Color.rgb(15, 35, 55));
        buildScreen();
        recordEvent("UYGULAMA_ACILDI", "Kestrel eğitim uygulaması açıldı.");
    }

    private void buildScreen() {
        ScrollView scroll = new ScrollView(this);
        scroll.setFillViewport(true);
        LinearLayout page = new LinearLayout(this);
        page.setOrientation(LinearLayout.VERTICAL);
        page.setPadding(dp(20), dp(22), dp(20), dp(28));
        page.setBackgroundColor(Color.rgb(244, 247, 250));
        scroll.addView(page);

        TextView title = new TextView(this);
        title.setText("SHADOW NEXUS");
        title.setTextSize(13);
        title.setLetterSpacing(0.12f);
        title.setTextColor(Color.rgb(34, 91, 135));
        title.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        page.addView(title);

        TextView heading = new TextView(this);
        heading.setText("Operasyon Kestrel");
        heading.setTextSize(29);
        heading.setTextColor(Color.rgb(18, 36, 54));
        heading.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        heading.setPadding(0, dp(5), 0, dp(4));
        page.addView(heading);

        TextView subtitle = new TextView(this);
        subtitle.setText("Mobil adli bilişim eğitim laboratuvarı");
        subtitle.setTextSize(15);
        subtitle.setTextColor(Color.rgb(80, 96, 112));
        page.addView(subtitle);

        TextView warning = new TextView(this);
        warning.setText("EĞİTİM SENARYOSU — SENTETİK VERİ\nBu uygulamadaki kişiler, olaylar ve belgeler kurgusaldır. Gerçek bir soruşturmayı temsil etmez.");
        warning.setTextSize(14);
        warning.setTextColor(Color.rgb(91, 62, 12));
        warning.setBackgroundColor(Color.rgb(255, 239, 204));
        warning.setPadding(dp(14), dp(14), dp(14), dp(14));
        LinearLayout.LayoutParams warningParams = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        warningParams.setMargins(0, dp(18), 0, dp(18));
        page.addView(warning, warningParams);

        addSectionTitle(page, "EĞİTİM VERİLERİ");
        addAction(page, "Senaryo belgelerini İndirilenler'e kaydet",
            "CSV, TXT, VCF, ICS ve etkinlik envanteri dosyalarını ortak depolama alanına yazar.",
            v -> saveTrainingFiles());
        addAction(page, "Aktarılan fotoğrafları Galeri'ye tanıt",
            "Önce gerçek fotoğraf aktarım betiğini çalıştırın. Bu işlem üç orijinal JPEG yolunu Android Medya Tarayıcısı'na bildirir.",
            v -> scanKestrelPhotoFolder());
        addAction(page, "Galeri'deki gerçek eğitim fotoğraflarını aç",
            "Wikimedia Commons kaynaklı, orijinal JPEG dosyalarını fotoğraf seçicisinde görüntüler. Uygulama fotoğraf üretmez veya değiştirmez.",
            v -> openPhotoGallery());
        addAction(page, "Etkinlik günlüğünü dışa aktar",
            "Bu uygulamada yaptığınız işlemleri gerçek gerçekleşme zamanlarıyla JSONL olarak kaydeder.",
            v -> exportActivityLog());

        addSectionTitle(page, "ANDROID SİSTEM KAYITLARI");
        addAction(page, "Kurgusal kişiyi rehbere ekle",
            "Android Kişi Ekle ekranını açar. Kaydı yalnızca siz onaylayıp kaydedersiniz.",
            v -> chooseContact());
        addAction(page, "Kestrel takvim etkinliği oluştur",
            "Android Takvim ekranını açar. Etkinliği yalnızca siz onaylayıp kaydedersiniz.",
            v -> createCalendarEvent());

        addSectionTitle(page, "GERÇEK TEST İLETİŞİMİ");
        addAction(page, "SMS test taslağı hazırla",
            "Yalnızca size ait/izinli ikinci test numarasını girer. SMS uygulaması açılır; gönder düğmesine siz basarsınız.",
            v -> prepareSmsDraft());
        addAction(page, "Arama test ekranını aç",
            "Telefon çeviricisini açar; aramayı siz başlatırsınız. Gerçek arama kaydı ancak görüşme gerçekleşirse oluşur.",
            v -> prepareCall());
        TextView callNote = new TextView(this);
        callNote.setText("GELEN KAYITLAR: Gerçek gelen SMS veya arama için ikinci kontrollü test hattından bu telefona mesaj gönderin ya da arayın. Kaçırılan arama için aramayı cevaplamadan reddedin. Bu uygulama sahte SMS/arama satırı üretmez.");
        callNote.setTextSize(13);
        callNote.setTextColor(Color.rgb(85, 99, 112));
        callNote.setPadding(dp(12), dp(10), dp(12), dp(10));
        page.addView(callNote);

        addSectionTitle(page, "LABORATUVAR BAĞLANTISI");
        addAction(page, "Bazzite üzerindeki Kestrel portalını aç",
            "Önce bilgisayarda portalı başlatın ve ADB ters bağlantısını kurun.",
            v -> openPortal());

        TextView instructions = new TextView(this);
        instructions.setText("ÖNERİLEN SIRA\n1. Belgeleri ve görseli kaydedin.\n2. Eğitim kişilerini Android Rehber ekranından ekleyin.\n3. Takvim etkinliğini kaydedin.\n4. Portalı açıp eğitim sayfalarını Chrome'da ziyaret edin.\n5. Etkinlik günlüğünü dışa aktarın.\n6. Oxygen çıktısında gerçekten görünen kayıtları ayrı ayrı doğrulayın.");
        instructions.setTextSize(14);
        instructions.setTextColor(Color.rgb(54, 69, 82));
        instructions.setBackgroundColor(Color.WHITE);
        instructions.setPadding(dp(15), dp(15), dp(15), dp(15));
        LinearLayout.LayoutParams infoParams = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        infoParams.setMargins(0, dp(16), 0, dp(12));
        page.addView(instructions, infoParams);

        TextView footer = new TextView(this);
        footer.setText("Bu uygulama SMS, arama kaydı, WhatsApp veya Telegram veritabanlarına yazmaz. Bu tür izler yalnızca size ait test cihazlarında gerçek kullanıcı işlemleriyle oluşturulmalıdır.");
        footer.setTextSize(12);
        footer.setTextColor(Color.rgb(100, 112, 124));
        page.addView(footer);

        setContentView(scroll);
    }

    private void addSectionTitle(LinearLayout parent, String label) {
        TextView section = new TextView(this);
        section.setText(label);
        section.setTextSize(12);
        section.setLetterSpacing(0.08f);
        section.setTextColor(Color.rgb(34, 91, 135));
        section.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, dp(22), 0, dp(7));
        parent.addView(section, params);
    }

    private void addAction(LinearLayout parent, String label, String description, View.OnClickListener click) {
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(dp(13), dp(10), dp(13), dp(12));
        card.setBackgroundColor(Color.WHITE);
        LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        cardParams.setMargins(0, dp(5), 0, dp(5));
        parent.addView(card, cardParams);

        TextView desc = new TextView(this);
        desc.setText(description);
        desc.setTextSize(13);
        desc.setTextColor(Color.rgb(85, 99, 112));
        card.addView(desc);

        Button button = new Button(this);
        button.setText(label);
        button.setAllCaps(false);
        button.setTextSize(14);
        button.setOnClickListener(click);
        card.addView(button);
    }

    private void chooseContact() {
        String[] names = new String[contacts.length];
        for (int i = 0; i < contacts.length; i++) names[i] = contacts[i].name;
        new AlertDialog.Builder(this)
            .setTitle("Eğitim kişisini seçin")
            .setItems(names, (dialog, which) -> openContactForm(contacts[which]))
            .setNegativeButton("Vazgeç", null)
            .show();
    }

    private void openContactForm(TrainingContact person) {
        Intent intent = new Intent(Intent.ACTION_INSERT);
        intent.setDataAndType(ContactsContract.Contacts.CONTENT_URI, ContactsContract.Contacts.CONTENT_TYPE);
        intent.putExtra(ContactsContract.Intents.Insert.NAME, person.name);
        intent.putExtra(ContactsContract.Intents.Insert.COMPANY, person.company);
        intent.putExtra(ContactsContract.Intents.Insert.NOTES, person.note);
        try {
            intent.putExtra(ContactsContract.Intents.Insert.PHONE, person.phone);
            intent.putExtra(ContactsContract.Intents.Insert.PHONE_TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE);
            intent.putExtra(ContactsContract.Intents.Insert.EMAIL, person.email);
            intent.putExtra(ContactsContract.Intents.Insert.EMAIL_TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK);
            startActivity(intent);
            recordEvent("REHBER_FORMU_ACILDI", "Kişi formu açıldı: " + person.name + ". Telefon alanı çevrilemez sentetik yer tutucudur; kayıt kullanıcı onayından sonra doğrulanmalıdır.");
        } catch (Exception ex) {
            showMessage("Rehber uygulaması açılamadı: " + ex.getMessage());
            recordEvent("REHBER_FORMU_HATASI", ex.toString());
        }
    }

    private void createCalendarEvent() {
        Calendar start = Calendar.getInstance();
        start.add(Calendar.DAY_OF_YEAR, 1);
        start.set(Calendar.HOUR_OF_DAY, 10);
        start.set(Calendar.MINUTE, 0);
        start.set(Calendar.SECOND, 0);
        start.set(Calendar.MILLISECOND, 0);
        Calendar end = (Calendar) start.clone();
        end.add(Calendar.MINUTE, 30);

        Intent intent = new Intent(Intent.ACTION_INSERT);
        intent.setData(CalendarContract.Events.CONTENT_URI);
        intent.putExtra(CalendarContract.Events.TITLE, "Kestrel eğitim incelemesi");
        intent.putExtra(CalendarContract.Events.DESCRIPTION,
            "SENTETİK-SENARYO: Kurgusal lojistik denetim dosyasının eğitim amaçlı incelenmesi.");
        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, start.getTimeInMillis());
        intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, end.getTimeInMillis());
        intent.putExtra(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().getID());
        try {
            startActivity(intent);
            recordEvent("TAKVIM_FORMU_ACILDI", "Takvim formu açıldı; kayıt kullanıcı onayı bekliyor.");
        } catch (Exception ex) {
            showMessage("Takvim uygulaması açılamadı: " + ex.getMessage());
            recordEvent("TAKVIM_FORMU_HATASI", ex.toString());
        }
    }

    private void prepareSmsDraft() {
        String[] templates = new String[] {
            "Merhaba, Kestrel eğitim dosyalarını aldım. SHA-256 kontrolünü tamamlayınca haber vereceğim. [SENTETİK-SENARYO / LAB TESTİ]",
            "Toplantı saatini 15:30 olarak güncelledim. Eğitim kapsamı dışında gerçek müşteri verisi yok. [SENTETİK-SENARYO / LAB TESTİ]",
            "USB teslim kaydındaki farkı gördüm. Lütfen varlık envanterini yeniden kontrol et. [SENTETİK-SENARYO / LAB TESTİ]",
            "Vaka materyallerini aldım. Bu ileti yalnızca kontrollü mobil adli bilişim eğitimi içindir. [SENTETİK-SENARYO / LAB TESTİ]"
        };
        new AlertDialog.Builder(this)
            .setTitle("SMS eğitim metnini seçin")
            .setItems(templates, (dialog, which) -> promptForTestNumber(true, templates[which]))
            .setNegativeButton("Vazgeç", null)
            .show();
    }

    private void prepareCall() {
        promptForTestNumber(false, null);
    }

    private void promptForTestNumber(boolean sms, String message) {
        EditText input = new EditText(this);
        input.setSingleLine(true);
        input.setInputType(InputType.TYPE_CLASS_PHONE);
        input.setHint("+90... — yalnızca kontrollü test numarası");
        new AlertDialog.Builder(this)
            .setTitle(sms ? "Test SMS alıcısı" : "Test arama numarası")
            .setMessage("Yalnızca size ait veya açık izniniz bulunan ikinci test hattını girin. Gerçek SMS/arama ücretleri oluşabilir. Sahte kayıt oluşturulmaz.")
            .setView(input)
            .setNegativeButton("Vazgeç", null)
            .setPositiveButton(sms ? "SMS taslağını aç" : "Telefon çeviricisini aç", (dialog, which) -> {
                String number = input.getText().toString().trim();
                String digits = number.replaceAll("[^0-9]", "");
                if (digits.length() < 7 || !number.matches("[+0-9() .-]{7,24}")) {
                    showMessage("Geçerli bir test numarası girin.");
                    recordEvent("TEST_NUMARASI_REDDEDILDI", "Numara biçimi geçersizdi; iletişim uygulaması açılmadı.");
                    return;
                }
                try {
                    if (sms) {
                        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("smsto", number, null));
                        intent.putExtra("sms_body", message);
                        intent.putExtra(Intent.EXTRA_TEXT, message);
                        startActivity(intent);
                        recordEvent("SMS_TASLAGI_HAZIRLANDI", "Varsayılan SMS uygulaması açıldı. Bu günlük gönderim yapıldığını kanıtlamaz; gönderim kullanıcı onayına bağlıdır.");
                    } else {
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", number, null));
                        startActivity(intent);
                        recordEvent("ARAMA_EKRANI_ACILDI", "Varsayılan çevirici açıldı. Bu günlük arama yapıldığını kanıtlamaz; arama kullanıcı tarafından başlatılmalıdır.");
                    }
                } catch (Exception ex) {
                    recordEvent("TEST_ILETISIM_UYGULAMASI_HATASI", ex.toString());
                    showMessage("İletişim uygulaması açılamadı: " + ex.getMessage());
                }
            })
            .show();
    }

    private void openPortal() {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(PORTAL_URL)));
            recordEvent("PORTAL_LINKI_ACILDI", "Yerel laboratuvar portalı tarayıcıda açılmak üzere çağrıldı. Sunucu günlüğü ayrıca kontrol edilmelidir.");
        } catch (Exception ex) {
            showMessage("Tarayıcı açılamadı: " + ex.getMessage());
            recordEvent("PORTAL_ACMA_HATASI", ex.toString());
        }
    }

    private void saveTrainingFiles() {
        String stamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ROOT).format(new Date());
        ArrayList<String[]> files = new ArrayList<>();
        files.add(new String[] {"Kestrel_Denetim_Notlari_" + stamp + ".txt", "text/plain", auditNotes()});
        files.add(new String[] {"Kestrel_Varlik_Listesi_" + stamp + ".csv", "text/csv", assetRegister()});
        files.add(new String[] {"Kestrel_Devir_Kaydi_" + stamp + ".csv", "text/csv", transferRecord()});
        files.add(new String[] {"Kestrel_Senaryo_Zaman_Cizelgesi_" + stamp + ".csv", "text/csv", scenarioTimeline()});
        files.add(new String[] {"Kestrel_Egitim_Kisileri_" + stamp + ".vcf", "text/vcard", contactCards()});
        files.add(new String[] {"Kestrel_Takvim_Etkinlikleri_" + stamp + ".ics", "text/calendar", calendarFile()});

        int saved = 0;
        StringBuilder inventory = new StringBuilder("dosya,siniflandirma,icerik_tipi\n");
        for (String[] file : files) {
            try {
                saveToDownloads(file[0], file[1], file[2]);
                saved++;
                inventory.append(file[0]).append(",SENTETİK-SENARYO,").append(file[1]).append("\n");
                recordEvent("DOSYA_KAYDEDILDI", file[0]);
            } catch (Exception ex) {
                recordEvent("DOSYA_KAYIT_HATASI", file[0] + ": " + ex);
            }
        }
        try {
            saveToDownloads("Kestrel_Dosya_Envanteri_" + stamp + ".csv", "text/csv", inventory.toString());
            saved++;
            recordEvent("ENVANTER_KAYDEDILDI", "Senaryo dosyalarının envanteri kaydedildi.");
        } catch (Exception ex) {
            recordEvent("ENVANTER_KAYIT_HATASI", ex.toString());
        }
        showMessage(saved + " eğitim dosyası İndirilenler/ShadowNexus-Kestrel konumuna kaydedildi.");
    }

    private Uri saveToDownloads(String name, String mime, String data) throws Exception {
        ContentValues values = new ContentValues();
        values.put(MediaStore.MediaColumns.DISPLAY_NAME, name);
        values.put(MediaStore.MediaColumns.MIME_TYPE, mime);
        values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS + "/ShadowNexus-Kestrel");
        values.put(MediaStore.MediaColumns.IS_PENDING, 1);
        Uri uri = getContentResolver().insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, values);
        if (uri == null) throw new IllegalStateException("Dosya kaydı için Android depolama alanı satırı oluşturulamadı.");
        try (OutputStream output = getContentResolver().openOutputStream(uri, "w")) {
            if (output == null) throw new IllegalStateException("Dosya akışı açılamadı.");
            output.write(data.getBytes(StandardCharsets.UTF_8));
        }
        ContentValues ready = new ContentValues();
        ready.put(MediaStore.MediaColumns.IS_PENDING, 0);
        getContentResolver().update(uri, ready, null, null);
        return uri;
    }

    private void scanKestrelPhotoFolder() {
        File galleryDir = new File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
            "ShadowNexus-Kestrel-V2"
        );
        String[] names = new String[] {
            "Usb_flash_drive_on_a_laptop_closeup.jpg",
            "Laptop-hard-drive-exposed.jpg",
            "Datacenter_Rack.jpg"
        };
        String[] paths = new String[names.length];
        String[] mimeTypes = new String[names.length];
        for (int i = 0; i < names.length; i++) {
            paths[i] = new File(galleryDir, names[i]).getAbsolutePath();
            mimeTypes[i] = "image/jpeg";
        }
        MediaScannerConnection.scanFile(this, paths, mimeTypes, (path, uri) -> {
            if (uri != null) {
                recordEvent("ORIJINAL_FOTOGRAF_MEDYA_TARAMASI", "Galeri indeksine eklendi: " + new File(path).getName());
            } else {
                recordEvent("ORIJINAL_FOTOGRAF_MEDYA_TARAMASI_UYARISI", "Medya taraması URI döndürmedi: " + path);
            }
        });
        showMessage("Fotoğraf yolları Android Medya Tarayıcısı'na gönderildi. Birkaç saniye sonra Galeri'yi kontrol edin.");
    }

    private void openPhotoGallery() {
        try {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            startActivity(Intent.createChooser(intent, "Orijinal eğitim fotoğrafını seçin"));
            recordEvent("GERCEK_FOTOGRAF_SECICI_ACILDI",
                "Android fotoğraf seçicisi açıldı. Kaynak fotoğraflar ADB aktarım aracıyla Galeri klasörüne kopyalanmalıdır.");
        } catch (Exception ex) {
            recordEvent("FOTOGRAF_SECICI_HATASI", ex.toString());
            showMessage("Fotoğraf seçicisi açılamadı: " + ex.getMessage());
        }
    }

    private void exportActivityLog() {
        recordEvent("ETKINLIK_KAYDI_DISA_AKTARMA_ISTEGI", "Kullanıcı etkinlik günlüğünü dışa aktarmayı başlattı.");
        StringBuilder data = new StringBuilder();
        File savedLog = new File(getFilesDir(), "kestrel-etkinlik-kaydi.jsonl");
        try {
            if (savedLog.isFile()) {
                data.append(new String(Files.readAllBytes(savedLog.toPath()), StandardCharsets.UTF_8));
            } else {
                for (String event : activityEvents) data.append(event).append("\n");
            }
        } catch (IOException ex) {
            showMessage("Önceki etkinlik günlüğü okunamadı: " + ex.getMessage());
            return;
        }
        String name = "Kestrel_Etkinlik_Kaydi_" +
            new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ROOT).format(new Date()) + ".jsonl";
        try {
            saveToDownloads(name, "application/x-ndjson", data.toString());
            recordEvent("ETKINLIK_KAYDI_DISA_AKTARILDI", name);
            showMessage("Etkinlik günlüğü İndirilenler'e kaydedildi.");
        } catch (Exception ex) {
            showMessage("Etkinlik günlüğü kaydedilemedi: " + ex.getMessage());
        }
    }

    private void recordEvent(String type, String detail) {
        String utc = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US) {{
            setTimeZone(TimeZone.getTimeZone("UTC"));
        }}.format(new Date());
        String safe = detail.replace("\\", "\\\\").replace("\"", "\\\"")
            .replace("\n", " ").replace("\r", " ");
        String record = "{\"gerceklesme_utc\":\"" + utc +
            "\",\"siniflandirma\":\"GERÇEK-LAB-ETKİNLİĞİ\",\"olay\":\"" + type +
            "\",\"aciklama\":\"" + safe + "\"}";
        activityEvents.add(record);
        try (FileOutputStream output = new FileOutputStream(
                new File(getFilesDir(), "kestrel-etkinlik-kaydi.jsonl"), true)) {
            output.write((record + "\\n").getBytes(StandardCharsets.UTF_8));
        } catch (IOException ignored) {
            // Günlük dışa aktarma düğmesi, mevcut oturum belleğindeki kayıtları yine dışa aktarabilir.
        }
    }

    private String auditNotes() {
        return "SINIFLANDIRMA: SENTETİK-SENARYO\n" +
            "VAKA: Operasyon Kestrel\n" +
            "BELGE: Denetim Notları\n\n" +
            "Bu eğitim belgesindeki tüm kişiler, kurumlar, varlıklar ve olaylar kurgusaldır.\n" +
            "Kestrel eğitim ekibi, ekipman hareket kaydı ile devir belgesi arasında tutarsızlık senaryosu tanımlamıştır.\n" +
            "Katılımcılar varlık listesini, devir kaydını ve cihazdan gerçekten elde edilen izleri ayrı ayrı incelemelidir.\n\n" +
            "Bu dosya sentetik eğitim içeriğidir; gerçek olay kaydı değildir.\n";
    }

    private String assetRegister() {
        return "siniflandirma,varlik_kodu,aciklama,kayit_konumu,durum\n" +
            "SENTETİK-SENARYO,KST-1001,Taşınabilir görüntüleme kiti,Depo A,Kayıtlı\n" +
            "SENTETİK-SENARYO,KST-1002,Ağ test çantası,Depo A,İncelemede\n" +
            "SENTETİK-SENARYO,KST-1003,Saha bilgisayarı çantası,Depo B,Kayıtlı\n";
    }

    private String transferRecord() {
        return "siniflandirma,devir_no,varlik_kodu,kaynak,hedef,durum\n" +
            "SENTETİK-SENARYO,TR-K-204,KST-1001,Depo A,Laboratuvar C,Teslim alındı\n" +
            "SENTETİK-SENARYO,TR-K-205,KST-1002,Depo A,Laboratuvar C,İnceleme bekliyor\n" +
            "SENTETİK-SENARYO,TR-K-206,KST-1003,Depo B,Laboratuvar C,Teslim alındı\n";
    }

    private String scenarioTimeline() {
        return "siniflandirma,senaryo_sirasi,olay\n" +
            "SENTETİK-SENARYO,1,Eğitim denetiminde tutarsızlık tanımlandı\n" +
            "SENTETİK-SENARYO,2,Varlık listesi incelendi\n" +
            "SENTETİK-SENARYO,3,Devir kaydı talep edildi\n" +
            "SENTETİK-SENARYO,4,Destekleyici notlar toplandı\n";
    }

    private String contactCards() {
        StringBuilder result = new StringBuilder();
        for (TrainingContact person : contacts) {
            result.append("BEGIN:VCARD\nVERSION:3.0\n");
            result.append("FN:").append(person.name).append("\n");
            result.append("ORG:").append(person.company).append("\n");
            result.append("TEL;TYPE=CELL:").append(person.phone).append("\n");
            result.append("EMAIL;TYPE=INTERNET:").append(person.email).append("\n");
            result.append("NOTE:").append(person.note).append("\n");
            result.append("END:VCARD\n");
        }
        return result.toString();
    }

    private String calendarFile() {
        Calendar start = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        start.add(Calendar.DAY_OF_YEAR, 1);
        start.set(Calendar.HOUR_OF_DAY, 10);
        start.set(Calendar.MINUTE, 0);
        start.set(Calendar.SECOND, 0);
        Calendar end = (Calendar) start.clone();
        end.add(Calendar.MINUTE, 30);
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'", Locale.US);
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        return "BEGIN:VCALENDAR\r\nVERSION:2.0\r\nPRODID:-//Shadow Nexus//Kestrel//TR\r\n" +
            "BEGIN:VEVENT\r\nUID:kestrel-egitim-" + System.currentTimeMillis() + "@shadow-nexus.invalid\r\n" +
            "DTSTAMP:" + format.format(new Date()) + "\r\n" +
            "DTSTART:" + format.format(start.getTime()) + "\r\n" +
            "DTEND:" + format.format(end.getTime()) + "\r\n" +
            "SUMMARY:Kestrel eğitim incelemesi\r\n" +
            "DESCRIPTION:SENTETİK-SENARYO; kurgusal eğitim etkinliği\r\n" +
            "END:VEVENT\r\nEND:VCALENDAR\r\n";
    }

    private int dp(int value) {
        return Math.round(value * getResources().getDisplayMetrics().density);
    }

    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
