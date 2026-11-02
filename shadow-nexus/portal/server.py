#!/usr/bin/env python3
"""Operasyon Kestrel salt okunur eğitim portalı ve erişim günlüğü."""
from http.server import SimpleHTTPRequestHandler, ThreadingHTTPServer
from pathlib import Path
from urllib.parse import urlparse
import json
import os
import time

ROOT = Path(os.environ.get("SN_PORTAL_ROOT", Path(__file__).parent)).resolve()
PORT = int(os.environ.get("SN_PORT", "34871"))
LOG = Path(os.environ.get("SN_ACCESS_LOG", ROOT / ".runtime" / "erisim-kaydi.jsonl"))

class PortalHandler(SimpleHTTPRequestHandler):
    def __init__(self, *args, **kwargs):
        super().__init__(*args, directory=str(ROOT), **kwargs)

    def log_message(self, fmt, *args):
        LOG.parent.mkdir(parents=True, exist_ok=True)
        row = {
            "gozlem_utc": time.strftime("%Y-%m-%dT%H:%M:%SZ", time.gmtime()),
            "istemci_adresi": self.client_address[0],
            "yontem": self.command,
            "yol": urlparse(self.path).path,
            "sunucu_mesaji": fmt % args,
            "siniflandirma": "GERÇEK-LAB-ETKİNLİĞİ"
        }
        with LOG.open("a", encoding="utf-8") as stream:
            stream.write(json.dumps(row, ensure_ascii=False) + "\n")

    def do_GET(self):
        if urlparse(self.path).path == "/api/ping":
            body = json.dumps({
                "durum": "hazir",
                "siniflandirma": "GERÇEK-LAB-ETKİNLİĞİ",
                "mesaj": "Kestrel laboratuvar sunucusu isteği aldı."
            }, ensure_ascii=False).encode("utf-8")
            self.send_response(200)
            self.send_header("Content-Type", "application/json; charset=utf-8")
            self.send_header("Content-Length", str(len(body)))
            self.end_headers()
            self.wfile.write(body)
            return
        super().do_GET()

    def do_POST(self):
        self.send_error(405, "Bu eğitim portalı salt okunurdur.")

    def do_PUT(self):
        self.send_error(405, "Bu eğitim portalı salt okunurdur.")

    def do_DELETE(self):
        self.send_error(405, "Bu eğitim portalı salt okunurdur.")

if __name__ == "__main__":
    print(f"Kestrel portalı 127.0.0.1:{PORT} adresinde hazır.", flush=True)
    ThreadingHTTPServer(("127.0.0.1", PORT), PortalHandler).serve_forever()
