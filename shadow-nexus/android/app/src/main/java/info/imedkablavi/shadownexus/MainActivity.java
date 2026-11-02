package info.imedkablavi.shadownexus;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.CalendarContract;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
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

        TrainingContact(String name, String company, String note) {
            this.name = name;
            this.company = company;
            this.note = note;
        }
    }

    private final TrainingContact[] contacts = new TrainingContact[] {
        new TrainingContact("Kestrel Eğitim - Maya Reed", "Kestrel Eğitim Laboratuvarı",
            "SENTETİK-SENARYO: Tamamen kurgusal eğitim kişisi."),
        new TrainingContact("Kestrel Eğitim - Omar Vale", "Kestrel Eğitim Laboratuvarı",
            "SENTETİK-SENARYO: Tamamen kurgusal eğitim kişisi."),
        new TrainingContact("Kestrel Eğitim - Lina North", "Kestrel Eğitim Laboratuvarı",
            "SENTETİK-SENARYO: Tamamen kurgusal eğitim kişisi.")
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
        addAction(page, "Sentetik eğitim görselini Galeri'ye kaydet",
            "Üzerinde görünür SENTETİK EĞİTİM GÖRSELİ filigranı bulunan bir PNG oluşturur.",
            v -> saveTrainingImage());
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
            startActivity(intent);
            recordEvent("REHBER_FORMU_ACILDI", "Kişi formu açıldı: " + person.name + ". Kaydedilip kaydedilmediği kullanıcı onayından sonra doğrulanmalıdır.");
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

    private void saveTrainingImage() {
        int width = 1200;
        int height = 1500;
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.rgb(236, 242, 247));
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.rgb(18, 48, 72));
        canvas.drawRoundRect(70, 90, 1130, 1410, 40, 40, paint);
        paint.setColor(Color.WHITE);
        paint.setTypeface(Typeface.create("sans-serif", Typeface.BOLD));
        paint.setTextSize(74);
        canvas.drawText("OPERASYON", 130, 270, paint);
        canvas.drawText("KESTREL", 130, 365, paint);
        paint.setTypeface(Typeface.create("sans-serif", Typeface.NORMAL));
        paint.setTextSize(38);
        canvas.drawText("Mobil adli bilişim eğitim senaryosu", 130, 470, paint);
        paint.setColor(Color.rgb(255, 205, 90));
        canvas.drawRoundRect(130, 600, 1070, 760, 22, 22, paint);
        paint.setColor(Color.rgb(36, 48, 60));
        paint.setTypeface(Typeface.create("sans-serif", Typeface.BOLD));
        paint.setTextSize(44);
        canvas.drawText("SENTETİK EĞİTİM GÖRSELİ", 170, 700, paint);
        paint.setColor(Color.WHITE);
        paint.setTypeface(Typeface.create("sans-serif", Typeface.NORMAL));
        paint.setTextSize(34);
        canvas.drawText("Gerçek olay veya fotoğraf değildir.", 130, 900, paint);
        canvas.drawText("Tüm kişi ve kayıtlar kurgusaldır.", 130, 960, paint);
        canvas.drawText("Shadow Nexus / Kestrel Laboratuvarı", 130, 1250, paint);

        String name = "Kestrel_Sentetik_Egitim_Gorseli_" +
            new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ROOT).format(new Date()) + ".png";
        ContentValues values = new ContentValues();
        values.put(MediaStore.MediaColumns.DISPLAY_NAME, name);
        values.put(MediaStore.MediaColumns.MIME_TYPE, "image/png");
        values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/ShadowNexus-Kestrel");
        values.put(MediaStore.MediaColumns.IS_PENDING, 1);
        Uri uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        if (uri == null) {
            bitmap.recycle();
            showMessage("Galeri görseli için kayıt oluşturulamadı.");
            return;
        }
        try (OutputStream output = getContentResolver().openOutputStream(uri, "w")) {
            if (output == null) throw new IllegalStateException("Görsel akışı açılamadı.");
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, output);
            ContentValues ready = new ContentValues();
            ready.put(MediaStore.MediaColumns.IS_PENDING, 0);
            getContentResolver().update(uri, ready, null, null);
            recordEvent("SENTETIK_GORSEL_KAYDEDILDI", name);
            showMessage("Filigranlı eğitim görseli Galeri'ye kaydedildi.");
        } catch (Exception ex) {
            recordEvent("GORSEL_KAYIT_HATASI", ex.toString());
            showMessage("Görsel kaydedilemedi: " + ex.getMessage());
        } finally {
            bitmap.recycle();
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
