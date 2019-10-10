
# UPDATE RELEASE ON MASTER BRANCH
#Author: William Roberts <william.c.roberts@intel.com>  2019-06-13 10:22:12
#Committer: GitHub <noreply@github.com>  2019-06-13 10:22:12
#LIC_FILES_CHKSUM = "file://LICENSE;md5=91b7c548d73ea16537799e8060cea819"
LIC_FILES_CHKSUM = "file://LICENSE;md5=99f008394f3e7e4e588cbc2ece4bdbe8"

#SRCREV = "74ba065e5914bc5d713ca3709d62a5751b097369"
SRCREV = "3e8847c9a52a6adc80bcd66dc1321210611654be"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

#SRC_URI = "git://github.com/tpm2-software/tpm2-tools.git;branch=3.X"
SRC_URI = "git://github.com/tpm2-software/tpm2-tools.git;branch=master \
          file://bootstrap_fixup.patch \
          "

do_configure_prepend () {
    ${S}/bootstrap -I m4
}


inherit autotools-brokensep
