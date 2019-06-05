require ../../../meta-st-openstlinux/recipes-st/images/st-image-weston.bb

SUMMARY = "OpenSTLinux AWS greengrass image based on weston image"

PREFERRED_VERSION_greengrass = "1.8.0"

#openssl needed for certification testing
#RDEPENDS_${PN} += " openssl"

IMAGE_AWSGREENGRASS_PART = "   \
    greengrass \
    openssl \
    tpm2-pkcs11 \
"

#
# INSTALL addons
#
CORE_IMAGE_EXTRA_INSTALL += " \
    ${IMAGE_AWSGREENGRASS_PART}        \
"
