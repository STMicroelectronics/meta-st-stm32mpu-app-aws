## meta-st-stm32mpu-app-demo-aws

OpenEmbedded meta layer to install a AWS greengrass application.

https://aws.amazon.com/fr/greengrass/

## Installation of the meta layer

* Clone following git repositories into [your STM32MP1 Distribution path]/layers/meta-st/
   > git clone ssh://${USER}@gerrit.st.com:29418/stm32mpuapp/meta/meta-st-stm32mpu-app-aws.git meta-st-demo-aws

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
   > * Select the demo-aws MACHINE (ex: stm32mp1-demo-aws)

* Build your image
   > bitbake st-image-demo-aws
