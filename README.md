## meta-st-stm32mpu-app-awsgreengrass

OpenEmbedded meta layer to install a AWS greengrass application.

https://aws.amazon.com/fr/greengrass/

## Installation of the meta layer

* Clone following git repositories into [your STM32MP1 Distribution path]/layers/meta-st/
   > cd [your STM32MP1 Distribution path]/layers/
   > git clone https://github.com/digi-embedded/meta-digi.git
   > cd [your STM32MP1 Distribution path]/layers/meta-st/
   > git clone ssh://${USER}@gerrit.st.com:29418/stm32mpuapp/meta/meta-st-stm32mpu-app-aws.git meta-st-demo-aws

* Patch meta-digi-dey layer configuration to remove unused dependencies
   > * Apply the following patch in [your STM32MP1 Distribution path]/layers/meta-digi/meta-digi-dey/conflayer.conf:
   > * -LAYERDEPENDS_digi-dey  = "core digi-arm"
   > * -LAYERDEPENDS_digi-dey += "openembedded-layer networking-layer webserver qt5-layer swupdate"
   > * +LAYERDEPENDS_digi-dey  = "core openembedded-layer networking-layer webserver qt5-layer"

* Update layers/meta-st/meta-st-stm32mp-addons
   > * Select openstlinux-19-04-05 version

* Update layers/meta-security to support pkcs11
   > * Select warrior branch : SHA1 d1269991701037c0d2cf03e178662a63d56886c9

* Enable TPM build
   > * Apply the following patch in [your STM32MP1 Distribution path]/layers/meta-st/meta-st-openstlinux/conf/distro/include/openstlinux.inc
   > * -#DISTRO_FEATURES_append = " tpm2 "
   > * +DISTRO_FEATURES_append = " tpm2 "

* Setup the build environement
   > source [your STM32MP1 Distribution path]/layers/meta-st/scripts/envsetup.sh
   > * Select your DISTRO (ex: openstlinux-weston)
   > * Select the awsgreengrass MACHINE (ex: stm32mp1-awsgreengrass)

* Add meta-digi-dey layer
   > bitbake-layers add-layer [your STM32MP1 Distribution path]/layers/meta-digi/meta-digi-dey/

* Build your image
   > bitbake st-image-awsgreengrass
