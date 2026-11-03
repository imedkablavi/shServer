#!/usr/bin/env python3
"""Download untouched, attribution-tracked photographs from Wikimedia Commons."""

from __future__ import annotations

import argparse
import base64
import csv
import hashlib
import html
import json
import re
import shutil
import sys
import tempfile
import urllib.parse
import urllib.request
from datetime import datetime, timezone
from pathlib import Path

CASE_ID = "SNX-20261103-KESTREL-V2"
COMMONS_API = "https://commons.wikimedia.org/w/api.php"
USER_AGENT = "ShadowNexusKestrel/2.1 (academic forensic training; source preservation)"

ASSETS = [
    {
        "file": "Usb_flash_drive_on_a_laptop_closeup.jpg",
        "title": "File:Usb flash drive on a laptop closeup.jpg",
        "page": "https://commons.wikimedia.org/wiki/File:Usb_flash_drive_on_a_laptop_closeup.jpg",
        "purpose_tr": "Dizüstü bilgisayara bağlı USB bellek — eğitim senaryosu görsel bağlamı",
        "expected_license": "CC BY 2.0",
        "expected_author": "Shixart1985",
    },
    {
        "file": "Laptop-hard-drive-exposed.jpg",
        "title": "File:Laptop-hard-drive-exposed.jpg",
        "page": "https://commons.wikimedia.org/wiki/File:Laptop-hard-drive-exposed.jpg",
        "purpose_tr": "Açılmış 2,5 inç dizüstü sabit diski — depolama aygıtı görseli",
        "expected_license": "CC BY-SA 3.0",
        "expected_author": "Evan-Amos",
    },
    {
        "file": "Datacenter_Rack.jpg",
        "title": "File:Datacenter Rack.jpg",
        "page": "https://commons.wikimedia.org/wiki/File:Datacenter_Rack.jpg",
        "purpose_tr": "Veri merkezi sunucu rafı — kurgusal Kestrel sunucusu için bağlamsal fotoğraf",
        "expected_license": "CC BY-SA 3.0",
        "expected_author": "Edmondo",
    },
]


def clean_metadata(value: object) -> str:
    if not isinstance(value, str):
        return ""
    value = re.sub(r"<br\s*/?>", " ", value, flags=re.IGNORECASE)
    value = re.sub(r"<[^>]+>", "", value)
    return html.unescape(value).strip()


def base36(number: int) -> str:
    alphabet = "0123456789abcdefghijklmnopqrstuvwxyz"
    if number == 0:
        return "0"
    output = ""
    while number:
        number, rem = divmod(number, 36)
        output = alphabet[rem] + output
    return output


def sha1_mediawiki(data: bytes) -> str:
    return base36(int.from_bytes(hashlib.sha1(data).digest(), "big"))


def commons_info(asset: dict[str, str]) -> dict[str, object]:
    params = {
        "action": "query",
        "format": "json",
        "formatversion": "2",
        "titles": asset["title"],
        "prop": "imageinfo",
        "iiprop": "url|size|mime|sha1|extmetadata",
    }
    url = COMMONS_API + "?" + urllib.parse.urlencode(params)
    request = urllib.request.Request(url, headers={"User-Agent": USER_AGENT})
    with urllib.request.urlopen(request, timeout=45) as response:
        payload = json.load(response)
    pages = payload.get("query", {}).get("pages", [])
    if not pages or "missing" in pages[0]:
        raise RuntimeError(f"Wikimedia Commons dosyası bulunamadı: {asset['title']}")
    imageinfo = pages[0].get("imageinfo", [])
    if not imageinfo:
        raise RuntimeError(f"Dosya bilgisi alınamadı: {asset['title']}")
    info = imageinfo[0]
    if info.get("mime") != "image/jpeg":
        raise RuntimeError(f"Beklenmeyen MIME türü: {info.get('mime')}")
    metadata = info.get("extmetadata", {})
    return {
        "download_url": info["url"],
        "width": info.get("width"),
        "height": info.get("height"),
        "size_bytes": info.get("size"),
        "mime": info.get("mime"),
        "commons_sha1": info.get("sha1", ""),
        "author": clean_metadata(metadata.get("Artist", {}).get("value", "")),
        "license": clean_metadata(metadata.get("LicenseShortName", {}).get("value", "")),
        "license_url": clean_metadata(metadata.get("LicenseUrl", {}).get("value", "")),
        "description": clean_metadata(metadata.get("ImageDescription", {}).get("value", "")),
        "date_original": clean_metadata(metadata.get("DateTimeOriginal", {}).get("value", "")),
        "credit": clean_metadata(metadata.get("Credit", {}).get("value", "")),
        "source_page": asset["page"],
    }


def download_bytes(url: str) -> bytes:
    request = urllib.request.Request(
        url,
        headers={
            "User-Agent": USER_AGENT,
            "Accept": "image/jpeg,*/*;q=0.8",
        },
    )
    with urllib.request.urlopen(request, timeout=90) as response:
        data = response.read()
        content_type = response.headers.get_content_type()
    if content_type != "image/jpeg":
        raise RuntimeError(f"İndirilen içerik JPEG değil: {content_type}")
    if len(data) < 10_000 or not data.startswith(b"\xff\xd8\xff"):
        raise RuntimeError("Dosya JPEG imzası taşımıyor veya beklenmeyecek kadar küçük.")
    return data


def main() -> int:
    parser = argparse.ArgumentParser(
        description="Wikimedia Commons'tan orijinal fotoğrafları değiştirmeden indirir."
    )
    parser.add_argument(
        "--case-dir",
        type=Path,
        default=Path.home() / "ShadowNexus_Cases" / CASE_ID,
        help="Kestrel V2 bilgisayar vaka klasörü.",
    )
    args = parser.parse_args()

    case_dir = args.case_dir.expanduser().resolve()
    if case_dir.name != CASE_ID:
        raise SystemExit(f"Güvenlik kontrolü: vaka klasörü {CASE_ID} olmalıdır.")
    if case_dir.exists() and not case_dir.is_dir():
        raise SystemExit(f"Vaka yolu klasör değil: {case_dir}")

    photo_dir = case_dir / "02_GERCEK_FOTOGRAFLAR"
    if photo_dir.exists():
        raise SystemExit(
            f"Hedef fotoğraf klasörü zaten var; üzerine yazılmadı: {photo_dir}"
        )

    case_dir.mkdir(parents=True, exist_ok=True)
    staging = Path(tempfile.mkdtemp(prefix=".kestrel-v2-photos-", dir=case_dir))
    records: list[dict[str, object]] = []

    try:
        for asset in ASSETS:
            info = commons_info(asset)
            data = download_bytes(str(info["download_url"]))
            actual_sha1 = sha1_mediawiki(data)
            expected_sha1 = str(info["commons_sha1"])
            if expected_sha1 and actual_sha1 != expected_sha1:
                raise RuntimeError(
                    f"Wikimedia SHA-1 doğrulaması başarısız: {asset['file']}"
                )

            destination = staging / asset["file"]
            with destination.open("xb") as output:
                output.write(data)

            record = {
                "file": asset["file"],
                "purpose_tr": asset["purpose_tr"],
                "source_page": asset["page"],
                "download_url": info["download_url"],
                "author": info["author"],
                "license": info["license"],
                "license_url": info["license_url"],
                "description": info["description"],
                "date_original": info["date_original"],
                "credit": info["credit"],
                "expected_author_from_research": asset["expected_author"],
                "expected_license_from_research": asset["expected_license"],
                "width": info["width"],
                "height": info["height"],
                "size_bytes": len(data),
                "sha1_wikimedia_base36": actual_sha1,
                "sha256": hashlib.sha256(data).hexdigest(),
                "mime": info["mime"],
                "downloaded_utc": datetime.now(timezone.utc).isoformat(),
                "changed_after_download": False,
            }
            records.append(record)

        manifest_path = staging / "FOTOGRAF_PROVENANSI_TR.csv"
        with manifest_path.open("w", encoding="utf-8-sig", newline="") as stream:
            writer = csv.DictWriter(stream, fieldnames=list(records[0].keys()))
            writer.writeheader()
            writer.writerows(records)

        (staging / "FOTOGRAF_PROVENANSI_TR.json").write_text(
            json.dumps(
                {
                    "case_id": CASE_ID,
                    "classification": "DIŞ-KAYNAK / GERÇEK-FOTOĞRAF",
                    "created_utc": datetime.now(timezone.utc).isoformat(),
                    "transformation": "NONE — original JPEG bytes preserved",
                    "warning": (
                        "Fotoğraflar gerçek fotoğraf dosyalarıdır; Redmi telefon kamerasıyla "
                        "çekilmemiştir. EXIF alanları dosyayla birlikte korunmuştur, ancak tek "
                        "başına olay zamanı veya konum kanıtı sayılmaz."
                    ),
                    "assets": records,
                },
                ensure_ascii=False,
                indent=2,
            )
            + "\n",
            encoding="utf-8",
        )

        readme = [
            "# Kestrel V2 — Gerçek Fotoğraf Kaynak Kaydı",
            "",
            "Bu klasörde Wikimedia Commons'tan alınan orijinal JPEG fotoğrafları bulunur.",
            "Görseller AI ile üretilmemiş, birleştirilmemiş, kırpılmamış, yeniden boyutlandırılmamış veya üzerine yazı eklenmemiştir.",
            "",
            "## Kullanım ve kanıt sınırlaması",
            "",
            "- Fotoğraflar gerçek fotoğraflardır; ancak Redmi Note 13 Pro kamerasıyla çekilmemiştir.",
            "- EXIF bilgileri orijinal JPEG baytlarıyla birlikte korunur. EXIF tek başına fotoğrafın olay yerinde veya belirtilen tarihte çekildiğini ispatlamaz.",
            "- Fotoğraf, kişi veya Kestrel olayıyla ilişkilendirme iddiası taşımaz; yalnızca eğitim senaryosuna bağlam sağlar.",
            "- Her fotoğrafın yazarı, kaynak sayfası ve lisansı CSV/JSON manifestlerinde tutulur.",
            "- Yeniden dağıtımda her dosyanın lisans ve atıf şartına uyulmalıdır.",
            "",
            "## Dosyalar",
            "",
        ]
        for record in records:
            readme.extend(
                [
                    f"### {record['file']}",
                    f"- Açıklama: {record['purpose_tr']}",
                    f"- Yazar: {record['author']}",
                    f"- Lisans: {record['license']}",
                    f"- Lisans bağlantısı: {record['license_url']}",
                    f"- Kaynak sayfası: {record['source_page']}",
                    f"- Boyut: {record['width']} × {record['height']}",
                    f"- SHA-256: {record['sha256']}",
                    "",
                ]
            )
        (staging / "KULLANIM_VE_LISANS_TR.md").write_text(
            "\n".join(readme), encoding="utf-8"
        )

        with (staging / "SHA256SUMS.txt").open("w", encoding="utf-8") as stream:
            for record in records:
                stream.write(f"{record['sha256']}  {record['file']}\n")
            for name in (
                "FOTOGRAF_PROVENANSI_TR.csv",
                "FOTOGRAF_PROVENANSI_TR.json",
                "KULLANIM_VE_LISANS_TR.md",
            ):
                digest = hashlib.sha256((staging / name).read_bytes()).hexdigest()
                stream.write(f"{digest}  {name}\n")

        staging.rename(photo_dir)
    except Exception:
        shutil.rmtree(staging, ignore_errors=True)
        raise

    print("BAŞARILI: Orijinal fotoğraflar indirildi; görüntü baytları değiştirilmedi.")
    print(f"Vaka: {CASE_ID}")
    print(f"Klasör: {photo_dir}")
    print(f"Fotoğraf sayısı: {len(records)}")
    for record in records:
        print(
            f"- {record['file']} | {record['width']}x{record['height']} | "
            f"SHA-256 {record['sha256']}"
        )
    print("Lisans/atıf: KULLANIM_VE_LISANS_TR.md")
    print("Not: Bu fotoğraflar cihaz kamerası çekimi veya Kestrel olay kanıtı değildir.")
    return 0


if __name__ == "__main__":
    try:
        raise SystemExit(main())
    except Exception as exc:
        print(f"HATA: {exc}", file=sys.stderr)
        raise SystemExit(1)
