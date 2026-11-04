FROM ubuntu:24.04
ENV DEBIAN_FRONTEND=noninteractive
RUN apt-get update \
 && apt-get install -y --no-install-recommends \
    ca-certificates dc3dd sleuthkit ewf-tools testdisk exfatprogs \
    dosfstools ntfs-3g util-linux coreutils file usbutils python3 \
    python3-pip python3-docx python3-openpyxl python3-pil python3-lxml \
    python3-typing-extensions ffmpeg fonts-dejavu-core \
 && python3 -m pip install --break-system-packages --no-cache-dir --no-deps python-pptx==1.0.2 \
 && rm -rf /var/lib/apt/lists/*
WORKDIR /work
CMD ["/bin/bash"]
