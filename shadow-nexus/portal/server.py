#!/usr/bin/env python3
"""Local, read-only training portal. Logs incoming HTTP requests; does not alter device data."""
from http.server import SimpleHTTPRequestHandler, ThreadingHTTPServer
from pathlib import Path
from urllib.parse import urlparse
import json, os, time

ROOT = Path(os.environ.get("SN_PORTAL_ROOT", Path(__file__).parent)).resolve()
PORT = int(os.environ.get("SN_PORT", "34871"))
LOG = Path(os.environ.get("SN_ACCESS_LOG", ROOT / ".runtime" / "access.jsonl"))

class Handler(SimpleHTTPRequestHandler):
    def __init__(self, *args, **kwargs):
        super().__init__(*args, directory=str(ROOT), **kwargs)
    def log_message(self, fmt, *args):
        LOG.parent.mkdir(parents=True, exist_ok=True)
        row = {
            "observed_utc": time.strftime("%Y-%m-%dT%H:%M:%SZ", time.gmtime()),
            "client_ip": self.client_address[0],
            "method": self.command,
            "path": urlparse(self.path).path,
            "status_message": fmt % args,
            "classification": "ACTUAL-LAB-ACTIVITY"
        }
        with LOG.open("a", encoding="utf-8") as f:
            f.write(json.dumps(row, ensure_ascii=False) + "\n")
    def do_POST(self):
        self.send_error(405, "Read-only training portal")
    def do_PUT(self):
        self.send_error(405, "Read-only training portal")
    def do_DELETE(self):
        self.send_error(405, "Read-only training portal")

if __name__ == "__main__":
    print(f"Serving {ROOT} on 127.0.0.1:{PORT}", flush=True)
    ThreadingHTTPServer(("127.0.0.1", PORT), Handler).serve_forever()
