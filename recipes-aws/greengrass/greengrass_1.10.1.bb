require greengrass.inc

LICENSE = "MIT"
LIC_FILES_CHKSUM = " \
    file://ggc/core/THIRD-PARTY-LICENSES;md5=1f0ad815f019455e3a0efe55e888a69a \
"

SRC_URI_arm = " \
    https://d1onfpft10uf5o.cloudfront.net/greengrass-core/downloads/${PV}/greengrass-linux-armv7l-${PV}.tar.gz;name=arm \
    file://greengrass.service \
    file://greengrass-init \
    file://config_secu_example.json \
    file://tpm_update.sh \
"


SRC_URI[arm.md5sum]        = "f9b1181efe9b0c65dd490b01e9193b61"
SRC_URI[arm.sha256sum]     = "9e77cc841558a15326b6145ddab2b145db8dc735a3710cb4dd0feb023d16ae2f"


# Release specific configuration

do_install_append() {
  install -m 0644 ${WORKDIR}/config_secu_example.json ${D}/greengrass/config/config_secu_example.json
  install -m 0644 ${WORKDIR}/tpm_update.sh ${D}/greengrass/tpm_update.sh
}

RDEPENDS_${PN} += "ca-certificates python3-json python3-numbers sqlite3"
RDEPENDS_${PN} += "opensc openssl libp11"

INSANE_SKIP_${PN} += " libdir"

FETCHCMD_wget = "/usr/bin/env wget -t 10 -T 30 --passive-ftp --no-check-certificate"

