#!/usr/bin/env python3
"""Flaş bellek adli bilişim deneyi için Türkçe örnek veri üreticisi."""

from __future__ import annotations

import csv
import json
import math
import struct
import subprocess
import sys
import wave
from pathlib import Path

from docx import Document
from openpyxl import Workbook
from PIL import Image, ImageDraw, ImageFont
from pptx import Presentation
from pptx.util import Inches, Pt


def office_files(folder: Path) -> None:
    document = Document()
    document.add_heading("Adli Bilişim Laboratuvarı - Örnek Word Belgesi", 0)
    document.add_paragraph(
        "Bu belge, silme ve biçimlendirme sonrası dosya sistemi incelemesi "
        "için kontrollü laboratuvar ortamında oluşturulmuştur."
    )
    document.add_heading("Deney bilgileri", level=1)
    document.add_paragraph("Konu: Flaş bellek üzerinde dosya silme ve imaj alma")
    document.add_paragraph("Dosya türü: Microsoft Word Open XML (.docx)")
    document.add_paragraph("Veri niteliği: Tamamen sentetik eğitim verisi")
    document.save(folder / "ornek-rapor.docx")

    workbook = Workbook()
    sheet = workbook.active
    sheet.title = "Deney Verileri"
    sheet.append(["Sıra", "Dosya Türü", "İşlem", "Açıklama"])
    sheet.append([1, "DOCX", "Oluşturuldu", "Örnek Word belgesi"])
    sheet.append([2, "XLSX", "Oluşturuldu", "Örnek Excel çalışma kitabı"])
    sheet.append([3, "PPTX", "Oluşturuldu", "Örnek PowerPoint sunusu"])
    sheet.append([4, "PNG/JPG", "Oluşturuldu", "Örnek görüntü dosyaları"])
    sheet.append([5, "WAV/MP4", "Oluşturuldu", "Örnek ses ve video dosyaları"])
    workbook.save(folder / "ornek-veri.xlsx")

    presentation = Presentation()
    slide = presentation.slides.add_slide(presentation.slide_layouts[0])
    slide.shapes.title.text = "Flaş Bellek Adli Bilişim Deneyi"
    slide.placeholders[1].text = (
        "Dosya silme, imaj alma ve biçimlendirme sonrası inceleme\n"
        "Sentetik laboratuvar verisi"
    )
    slide2 = presentation.slides.add_slide(presentation.slide_layouts[1])
    slide2.shapes.title.text = "Deneyin Amacı"
    body = slide2.placeholders[1].text_frame
    body.text = "Dört farklı aşamada alınan imajları karşılaştırmak."
    body.add_paragraph().text = "Silinen dosya kayıtlarını incelemek."
    body.add_paragraph().text = "SHA-256 özetleriyle imaj bütünlüğünü doğrulamak."
    presentation.save(folder / "ornek-sunum.pptx")


def multimedia_files(folder: Path) -> None:
    image = Image.new("RGB", (960, 540), (20, 48, 78))
    draw = ImageDraw.Draw(image)
    font_path = "/usr/share/fonts/truetype/dejavu/DejaVuSans.ttf"
    try:
        title_font = ImageFont.truetype(font_path, 42)
        body_font = ImageFont.truetype(font_path, 25)
    except OSError:
        title_font = ImageFont.load_default()
        body_font = ImageFont.load_default()

    draw.rectangle((35, 35, 925, 505), outline=(65, 190, 200), width=5)
    draw.text((75, 125), "ADLI BILISIM LABORATUVARI", fill="white", font=title_font)
    draw.text((78, 220), "Sentetik test goruntusu - PNG/JPEG", fill=(190, 225, 235), font=body_font)
    draw.text((78, 280), "Dosya silme ve imaj alma deneyi", fill=(190, 225, 235), font=body_font)
    image.save(folder / "ornek-gorsel.png", format="PNG")
    image.save(folder / "ornek-fotograf.jpg", format="JPEG", quality=90)

    audio_path = folder / "ornek-ses.wav"
    sample_rate = 44100
    duration = 4
    frequency = 440
    with wave.open(str(audio_path), "wb") as output:
        output.setnchannels(1)
        output.setsampwidth(2)
        output.setframerate(sample_rate)
        frames = bytearray()
        for index in range(sample_rate * duration):
            value = int(12000 * math.sin(2 * math.pi * frequency * index / sample_rate))
            frames.extend(struct.pack("<h", value))
        output.writeframes(frames)

    video_path = folder / "ornek-video.mp4"
    command = [
        "ffmpeg",
        "-hide_banner",
        "-loglevel",
        "error",
        "-y",
        "-f",
        "lavfi",
        "-i",
        "testsrc2=size=640x360:rate=15",
        "-t",
        "5",
        "-an",
        "-c:v",
        "mpeg4",
        "-q:v",
        "5",
        "-pix_fmt",
        "yuv420p",
        "-movflags",
        "+faststart",
        str(video_path),
    ]
    subprocess.run(command, check=True)


def application_files(folder: Path) -> None:
    (folder / "ornek-program.cpp").write_text(
        """#include <iostream>
int main() {
    std::cout << "Adli bilisim laboratuvari ornek C++ dosyasi" << std::endl;
    return 0;
}
""",
        encoding="utf-8",
    )
    (folder / "ornek-script.py").write_text(
        """# Adli bilisim laboratuvari icin sentetik Python ornegi
kayit = {
    "deney": "Flaş bellek imaj incelemesi",
    "asama": "orijinal durum",
    "veri": "sentetik"
}
print(kayit)
""",
        encoding="utf-8",
    )
    with (folder / "ornek-kayitlar.csv").open("w", encoding="utf-8", newline="") as handle:
        writer = csv.writer(handle)
        writer.writerow(["Kayit No", "Dosya", "Kategori"])
        writer.writerow([1, "ornek-rapor.docx", "Ofis belgesi"])
        writer.writerow([2, "ornek-gorsel.png", "Goruntu"])
        writer.writerow([3, "ornek-program.cpp", "Uygulama dosyasi"])

    (folder / "ornek-metadata.json").write_text(
        json.dumps(
            {
                "deney": "USB adli bilisim",
                "veri_turu": "sentetik",
                "amac": "Dosya sistemi incelemesi",
            },
            ensure_ascii=False,
            indent=2,
        )
        + "\n",
        encoding="utf-8",
    )


def main() -> int:
    if len(sys.argv) != 2:
        print("Kullanim: python3 olustur_test_verileri.py /usb/hedef-klasor", file=sys.stderr)
        return 2

    root = Path(sys.argv[1])
    office = root / "OFIS_BELGELERI"
    multimedia = root / "MULTIMEDYA"
    applications = root / "UYGULAMA_DOSYALARI"

    for directory in (office, multimedia, applications):
        directory.mkdir(parents=True, exist_ok=True)

    office_files(office)
    multimedia_files(multimedia)
    application_files(applications)

    (root / "ACIKLAMA_TURKCE.txt").write_text(
        "BU KLASOR KONTROLLU ADLI BILISIM EGITIM DENEYI ICIN OLUSTURULMUSTUR.\n"
        "Tum dosyalar sentetiktir; gercek kisi verisi veya gercek kullanici belgesi icermez.\n"
        "Alt klasorler: OFIS_BELGELERI, MULTIMEDYA, UYGULAMA_DOSYALARI.\n",
        encoding="utf-8",
    )

    files = sorted(path for path in root.rglob("*") if path.is_file())
    print("Sentetik deney dosyalari basariyla olusturuldu.")
    print(f"Toplam dosya sayisi: {len(files)}")
    for path in files:
        print(f"{path.relative_to(root)} | {path.stat().st_size} bayt")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
