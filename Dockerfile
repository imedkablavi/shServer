FROM ubuntu:24.04
ENV DEBIAN_FRONTEND=noninteractive
RUN apt-get update \
 && apt-get install -y --no-install-recommends \
    ca-certificates dc3dd sleuthkit ewf-tools testdisk exfatprogs \
    dosfstools ntfs-3g util-linux coreutils file usbutils python3 \
 && rm -rf /var/lib/apt/lists/*
WORKDIR /work
CMD ["/bin/bash"]
