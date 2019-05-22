## meta-st-stm32mpu-app-demo-aws
## OpenEmbedded meta layer to install a AWS greengrass application.

ref : https://aws.amazon.com/fr/greengrass/

This document describe the process to add the "AWS greengrass" application in the openSTlinux distribution, and how to configure the target to execute the AWS greengrass certification included the Hardware Security Integration test group.<br>
The Hardware Security Module used is the STM4RasPI expansion board (component TPM2 ST33TPHF20SPI).

Notes :
1. This process has been tested with the STM32MP1 OpenSTLinux distribution MMDV-v2.0.0 (openstlinux-5.4-dunfell-mp1-20-06-24).
2. This process has been tested with following software release :
- meta-java: "3b65eea96eddde97169ca5e00be01a9dbd257786"
- meta-virtualization: "ff997b6b3ba800978546098ab3cdaa113b6695e1"
- meta-security: "c74cc97641fd93e0e7a4383255e9a0ab3deaf9d7"
3. The Greengrass application is delivered by Amazon as binaries for a RASPBIAN distribution.
4. The AWS IoT Greengrass version installed is v1.10.1, AWS IoT Device Tester version used for AWS certification is IDT v3.2.0
5. The target is configured to Support the Greengrass Over-the-Air Updates (OTA)

## Process for installation :
#### Install the openSTlinux distribution yocto environment on your Host.
ref : [STM32MP1 Distribution Package - OpenSTLinux distribution](https://wiki.st.com/stm32mpu/wiki/STM32MP1_Distribution_Package_-_OpenSTLinux_distribution)

#### Clone following git repository into [your STM32MP1 Distribution path]/layers/meta-st/
 > **PC $>** cd [your STM32MP1 Distribution path]/layers/meta-st<br>
 > **PC $>** (ST internal) git clone ssh://${USER}@gerrit.st.com:29418/stm32mpuapp/meta/meta-st-stm32mpu-app-aws.git<br>
 > **PC $>** git clone https://github.com/STMicroelectronics/meta-st-stm32mpu-app-aws.git<br>
 > **PC $>** cd meta-st-stm32mpu-app-aws<br>
 > **PC $>** git checkout remotes/origin/dunfell

#### Add TPM2 recipes
 > **PC $>** cd [your STM32MP1 Distribution path]/layers<br>
 > **PC $>** git clone git://git.yoctoproject.org/meta-security<br>
 > **PC $>** cd meta-security<br>
 > **PC $>** git checkout remotes/origin/dunfell<br>

#### Setup the build environment
Executes the command, on the host :
 > **PC $>** cd [your STM32MP1 Distribution path]<br>
 > **PC $>** (ST internal) DISTRO=openstlinux-weston MACHINE=stm32mp1-demo-aws source layers/meta-st/scripts/envsetup-cache.sh<br>
 > **PC $>** DISTRO=openstlinux-weston MACHINE=stm32mp1-demo-aws source layers/meta-st/scripts/envsetup.sh

#### Add Virtualization (docker) in OpenSTLinux distribution
 > **PC $>** cd [your STM32MP1 Distribution path]/layers<br>
 > **PC $>** git clone git://git.yoctoproject.org/meta-virtualization<br>
 > **PC $>** cd meta-virtualization<br>
 > **PC $>** git checkout remotes/origin/dunfell<br>
 > **PC $>** cd [your STM32MP1 Distribution path]/build-openstlinuxweston-stm32mp1-demo-aws<br>
 > **PC $>** bitbake-layers add-layer [your STM32MP1 Distribution path]/layers/meta-virtualization


Apply the following update in the file _[your STM32MP1 Distribution path]/layers/meta-st/meta-st-openstlinux/conf/distro/openstlinux-weston.conf_

```
 DISTRO_FEATURES_append = "virtualization"
```

#### Add JAVA JDK in OpenSTLinux distribution
 > **PC $>** cd [your STM32MP1 Distribution path]/layers<br>
 > **PC $>** git clone git://git.yoctoproject.org/meta-java<br>
 > **PC $>** cd meta-java<br>
 > **PC $>** git checkout remotes/origin/dunfell<br>
 > **PC $>** cd [your STM32MP1 Distribution path]/build-openstlinuxweston-stm32mp1-demo-aws<br>
 > **PC $>** bitbake-layers add-layer [your STM32MP1 Distribution path]/layers/meta-java

Apply the following update in the file _[your STM32MP1 Distribution path]/build-openstlinuxweston-stm32mp1-demo-aws/conf/local.conf_

```
 # Possible provider: cacao-initial-native and jamvm-initial-native
 PREFERRED_PROVIDER_virtual/java-initial-native = "cacao-initial-native"

 # Possible provider: cacao-native and jamvm-native
 PREFERRED_PROVIDER_virtual/java-native = "jamvm-native"

 # Optional since there is only one provider for now
 PREFERRED_PROVIDER_virtual/javac-native = "ecj-bootstrap-native"
```

#### Increase the ROOFS partition size
Update the file _[your STM32MP1 Distribution path]/ layers/meta-st/meta-st-stm32mp/conf/machine/include/st-machine-common-stm32mp.inc_

```
IMAGE_ROOTFS_MAXSIZE = "2097152"
```

#### Enable TPM build
Apply the following update in the file _[your STM32MP1 Distribution path]/layers/meta-st/meta-st-openstlinux/conf/distro/include/openstlinux.inc_

```
 DISTRO_FEATURES_append = " tpm2 "
```

#### Build the image
In the folder _[your STM32MP1 Distribution path]//build-openstlinuxweston-stm32mp1-demo-aws_

Executes the command :
 > **PC $>** bitbake st-image-demo-aws

#### Flash the sdscard
The tsv file _FlashLayout\_sdcard\_stm32mp157c-demo-aws-mx-trusted.tsv_ is located in<br>
_[your STM32MP1 Distribution path]/build-openstlinuxweston-stm32mp1-demo-aws/tmp-glibc/deploy/images/stm32mp1-demo-aws/flashlayout_st-image-demo-aws_

ref : [STM32CubeProgrammer](https://wiki.st.com/stm32mpu/wiki/STM32CubeProgrammer)

#### Run the scripts for some extra configuration on the target  (to execute only one time after the first boot)
Executes the commands, on the target :

```
  Board $>source /greengrass/tpm_update.sh

  Board $>source /greengrass/aws_certif_update.sh
```

#### TPM2 token intialisation
Note : keep the PINs (123456) and PKCS11 STORE folder (usr/local/pkcs11_tpm), scripts and greengrass config files examples use these values.
Executes the commands, on the target :

```
  Board $>cd /tools

  Board $>./tpm2_ptool.py init --primary-auth=123456 --path=$TPM2_PKCS11_STORE

  Board $>./tpm2_ptool.py addtoken --pid=1 --sopin=123456 --userpin=123456 --label=greengrass --path $TPM2_PKCS11_STORE

  Board $>./tpm2_ptool.py addkey --algorithm=rsa2048 --label="greengrass" --userpin=123456 --key-label=greenkey --path=$TPM2_PKCS11_STORE

```
#### OPTIONAL : Verifications with pkcs11-tool

Executes this command on the target to verify the token created.

```
  Board $> pkcs11-tool --module /usr/lib/libtpm2_pkcs11.so.0 -L
```

Output :

<pre><code>
Available slots:
   Slot 0 (0x1): greengrass STMicro
     token label        : greengrass
     token manufacturer : STMicro
     token model        :
    token flags        : login required, rng, token initialized, PIN initialized
     hardware version   : 1.38
     firmware version   : 74.8
     serial num         : 0000000000000000
     pin min/max        : 5/128
</code></pre>


**AT THIS STEP, THE CONFIGURATION OF THE BOARD IS COMPLETED TO BE USED WITH THE AWS IoT Device Tester.**

## Process to execute the AWS Greengrass certification testing
A) Go to the Amazon site to  [AWS IoT Device Tester for AWS IoT Greengrass Versions](https://docs.aws.amazon.com/greengrass/latest/developerguide/dev-test-versions.html)

Install the AWS IoT Device Tester.

B) Configure your ssh connection (ssh keys)

Go to the Amazon site to [Configure Your Host Computer to Access Your Device Under Test](https://docs.aws.amazon.com/greengrass/latest/developerguide/device-config-setup.html#configure-host)

C) Configure the IDT

Example of of the config folder install for Windows.

>c:\devicetester_greengrass_win\devicetester_greengrass_win\configs\

See the Amazon site [Setting Configuration to Run the AWS IoT Greengrass Qualification Suite](https://docs.aws.amazon.com/greengrass/latest/developerguide/set-config.html)

There is a configuration file example installed  on your Host :

> /[your STM32MP1 Distribution path]/layers/meta-st/meta-st-demo-aws/recipes-aws/greengrasstests/greengrasstests/device-hsm.json

Note : With this example the certification tests are performed in Root.

D) Execute the tests, go to the Amazon site to [Running Tests](https://docs.aws.amazon.com/greengrass/latest/developerguide/run-tests.html)

### Process to to create a Certificat Signature Request using the hardware-protected private key

1) Update **openssl** tool configuration to use module tpm2_pkcs11

add the following lines in /etc/ssl/openssl.cnf at the beginning of the file (after "HOME        = .):
<pre><code>
openssl_conf = openssl_init
[openssl_init]
engines=engine_section
[engine_section]
pkcs11 = pkcs11_section
[pkcs11_section]
engine_id = pkcs11
dynamic_path = /usr/lib/engines-1.1/pkcs11.so
MODULE_PATH = /usr/lib/libtpm2_pkcs11.so.0
init = 0
</pre></code>

2) How to create a CSR "Certificat Signature Request" with **openssl**
Executes the command, on target :
```
 Board $>openssl req -engine pkcs11 -new -key "pkcs11:token=greengrass;object=greenkey;type=private;pin-value=123456" -keyform engine -out /usr/local/req.csr
```

This CSR "**/usr/local/req.csr**" is used to create clients certificats on the AWS amazon Cloud to store on the board.<br>
There is a greengrass configuration file example to update with your AWS account parameter and certificats created, on the target : **/greengrass/config/config\_secu\_example.json**<br>
You need also to download the root CA on Amazon site and stored it on the target **greengrass/certs/root.ca.pem**.

3) Connection to Amazon cloud

Before starting the greengrass core on the target you need to set the **TPM2\_PKCS11\_STORE** environment variable.

Executes the command on the target :
```
 Board $>export TPM2_PKCS11_STORE=/usr/local/pkcs11_tpm
```

### In case of trouble to reinit all the TPM/PKCS11 layers

How to reset the TPM and PKCS11 store :

Executes the commands, on the target:
```
 Board $>cd /usr/bin
 Board $>./tpm2_clear -Q
 Board $>rm /usr/local/pkcs11_tpm/*
```
