SUMMARY = "AWS IoT Greengrass IDT"
HOMEPAGE = "https://aws.amazon.com/greengrass/"


LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/BSD-3-Clause;md5=550794465ba0ec5312d6919e203a55f9"


FILEEXTRAPATHS_prepend := "$(THISDIR)/$(PN):"

SRC_URI = " \
    file://ld-2.28.so;name=workraoundlibrpi \
    file://aws_certif_update.sh \
"


S = "${WORKDIR}"


inherit extrausers
EXTRA_USERS_PARAMS += "\
	useradd -d /home/AWStest -p 6MjtR.O3wKMWk AWStest; \
	"

# Disable tasks not needed for the binary package
do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install() {
 install -d ${D}${base_libdir}
 install -d ${D}/greengrass
 install -m 0755 ${S}/ld-2.28.so ${D}${base_libdir}
 install -m 0644 ${S}/aws_certif_update.sh ${D}/greengrass/

 #create the symbolic link ld-linux.so.3
 ln -sf ${base_libdir}/ld-2.28.so ${D}${base_libdir}/ld-linux.so.3
}

RDEPENDS_${PN} = "bash"

FILES_${PN} = "${base_libdir}/ld-2.28.so ${base_libdir}/ld-linux.so.3 /greengrass/aws_certif_update.sh"
