## meta-st-stm32mpu-app-awsgreengrass

OpenEmbedded meta layer to install a AWS greengrass application.

https://aws.amazon.com/fr/greengrass/

## Installation of the meta layer

* Clone following git repositories into [your STM32MP1 Distribution path]/layers/meta-st/
   > cd [your STM32MP1 Distribution path]/layers/
   > git clone https://github.com/digi-embedded/meta-digi.git
   > cd [your STM32MP1 Distribution path]/layers/meta-st/
   > git clone ssh://${USER}@gerrit.st.com:29418/stm32mpuapp/meta/meta-st-stm32mpu-app-awsgreengrass.git

* Setup the build environement
   > source [your STM32MP1 Distribution path]/layers/meta-st/scripts/envsetup.sh
   > * Select your DISTRO (ex: openstlinux-weston)
   > * Select the awsgreengrass MACHINE (ex: stm32mp1-awsgreengrass)

* Build your image
   > bitbake st-image-awsgreengrass
