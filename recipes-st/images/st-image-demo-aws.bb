require ../../../meta-st-openstlinux/recipes-st/images/st-image-weston.bb

SUMMARY = "OpenSTLinux AWS greengrass image based on weston image"

PREFERRED_VERSION_greengrass = "1.9.3"

IMAGE_AWSGREENGRASS_PART = "\
    greengrass              \
"


# for greengrass certification testing with TPM
IMAGE_AWSGREENGRASSTEST_PART = "\
    greengrasstests      \
    sudo                 \
"

#  sqlite3 already in greengrass.inc RDEPENDS_${PN}
IMAGE_TPM2PKCS11_PART = "   \
    tpm2-pkcs11             \
    tpm2-tss-engine         \
    python-pyyaml           \
    python-cryptography     \
    python-sqlite3          \
"


#
# INSTALL addons
#
CORE_IMAGE_EXTRA_INSTALL += "   \
    ${IMAGE_AWSGREENGRASS_PART} \
    ${IMAGE_AWSGREENGRASSTEST_PART} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'tpm2', '${IMAGE_TPM2PKCS11_PART}', '', d)}\
"

IMAGE_FEATURES += "\
    ${@bb.utils.contains('CORE_IMAGE_EXTRA_INSTALL', 'greengrasstests', 'ssh-server-openssh', '', d)}\
"
