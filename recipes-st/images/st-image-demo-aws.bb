require ../../../meta-st-openstlinux/recipes-st/images/st-image-weston.bb

SUMMARY = "OpenSTLinux AWS greengrass image based on weston image"

PREFERRED_VERSION_greengrass = "1.8.0"

#openssl needed for certification testing
#RDEPENDS_${PN} += " openssl"

IMAGE_AWSGREENGRASS_PART = "\
    greengrass              \
    openssl                 \
"

IMAGE_TPM2PKCS11_PART = "   \
    tpm2-pkcs11             \
    sqlite3                 \
    python-pyyaml           \
    python-cryptography     \
    python-sqlite3          \
"

#
# INSTALL addons
#
CORE_IMAGE_EXTRA_INSTALL += "   \
    ${IMAGE_AWSGREENGRASS_PART} \
    ${IMAGE_TPM2PKCS11_PART}    \
"
