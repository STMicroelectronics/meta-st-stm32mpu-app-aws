## meta-st-stm32mpu-app-demo-aws
## OpenEmbedded meta layer to install a AWS greengrass application.

ref : https://aws.amazon.com/fr/greengrass/

This document describe the process to add the "AWS greengrass" application in the openSTlinux distribution, and how to configure the target to execute the AWS greengrass certification included the Hardware Security Integration test group.

Notes :
1. This process has been tested with the MMDV-v1.1.0.
2. The Greengrass application is delivered by Amazon as  binaries for a RASPBIAN distribution.
3. The AWS IoT Greengrass version installed is v1.9.3, AWS IoT Device Tester version used for AWS certification is IDT v2.0.0
4. The target is configured to Support the Greengrass Over-the-Air Updates (OTA)

## Process for installation :
#### Install the openSTlinux distribution yocto environment on your Host.
ref : [STM32MP1 Distribution Package - OpenSTLinux distribution](https://wiki.st.com/stm32mpu/wiki/STM32MP1_Distribution_Package_-_OpenSTLinux_distribution)

#### Clone following git repository into [your STM32MP1 Distribution path]/layers/meta-st/
> git clone ssh://${USER}@gerrit.st.com:29418/stm32mpuapp/meta/meta-st-stm32mpu-app-aws.git meta-st-demo-aws

#### Update of the meta-security layer to support the pkcs11 with tpm2

> cd  [your STM32MP1 Distribution path]/layers/meta-security

>git chekout warrior

(commit : 4f7be0d252f68d8e8d442a7ed8c6e8a852872d28)

#### Enable TPM build
Apply the following patch in the file _[your STM32MP1 Distribution path]/layers/meta-st/meta-st-openstlinux/conf/distro/include/openstlinux.inc_

> DISTRO_FEATURES_append = " tpm2 "

#### Setup the build environment
Executes the command, on the host :
> source [your STM32MP1 Distribution path]/layers/meta-st/scripts/envsetup.sh

Select your DISTRO (ex: openstlinux-weston)
and Select the demo-aws MACHINE (ex: stm32mp1-demo-aws)

#### Build the image
In the folder _[your STM32MP1 Distribution path]//build-openstlinuxweston-stm32mp1-demo-aws_

Executes the command :
> bitbake st-image-demo-aws

#### Flash the sdscard
tsv file _FlashLayout\_sdcard\_ stm32mp157c-demo-aws-mx-trusted.tsv_ is located in
_[your STM32MP1 Distribution path]/build-openstlinuxweston-stm32mp1-demo-aws/tmp-glibc/deploy/images/stm32mp1-demo-aws/flashlayout_st-image-demo-aws_

ref : [STM32CubeProgrammer](https://wiki.st.com/stm32mpu/wiki/STM32CubeProgrammer)

#### Run the scripts for some extra configuration on the target  (to execute only one time after the first boot)
Executes the commands, on the target :

> source /greengrass/tpm_update.sh

> source /greengrass/awsgreengrass_certif.sh

#### TPM2 token intialisation
Note : keep the PINs (123456) and PKCS11 STORE folder (usr/local/pkcs11_tpm), scripts and greengrass config files examples use these values.

Executes the commands, on the target :

> cd /tools

> ./tpm2_ptool.py init --pobj-pin=123456 --path=/usr/local/pkcs11_tpm

> ./tpm2_ptool.py addtoken --pid=1 --pobj-pin=123456 --sopin=123456 --userpin=123456 --label=greengrass --path=/usr/local/pkcs11_tpm

> ./tpm2_ptool.py addkey --algorithm=rsa2048 --label=greengrass --userpin=123456 --key-label=greenkey --path=/usr/local/pkcs11_tpm

#### OPTIONAL : Verifications with pkcs11-tool

Executes this command on the target to verify the token created.

> pkcs11-tool --module /usr/lib/libtpm2_pkcs11.so.0 -L

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

Go to the Amazon site to [Configure Your Host Computer to Access Your Device Under Test](https://docs.aws.amazon.com/greengrass/latest/developerguide/device-config-setup.html#Configure Your Host Computer to Access Your Device Under Test)

C) Configure the IDT

Example of of the config folder install for Windows.

>c:\devicetester_greengrass_win\devicetester_greengrass_win\configs\

See the Amazon site [Setting Configuration to Run the AWS IoT Greengrass Qualification Suite](https://docs.aws.amazon.com/greengrass/latest/developerguide/set-config.html)

There is a configuration file example installed  on your Host :

> /[your STM32MP1 Distribution path]/layers/meta-st/meta-st-demo-aws/recipes-aws/greengrasstests/greengrasstests/device-hsm.json

Note : With this example the certification tests are performed in Root.

D) Execute the tests, go to the Amazon site to [Running Tests](https://docs.aws.amazon.com/greengrass/latest/developerguide/run-tests.html#Running Tests)

### Process to to create a Certificat Signature Request using the hardware-protected private key

1) Install openssl on the target.

The packages are stored on your Host :
>  /[your STM32MP1 Distribution path]/build-openstlinuxweston-stm32mp1-demo-aws/tmp-glibc/deploy/deb/cortexa7t2hf-neon-vfpv4

<pre><code>
package openssl-bin_1.1.1a-r0_armhf.deb (usr/bin/openssl)
package openssl_1.1.1a-r0_armhf.deb (usr/lib/ssl-1.1/openssl.cnf ....; etc/ssl/certs and private folder)
package openssl-conf_1.1.1a-r0_armhf.deb (/etc/ssl/openssl.cnf)
</pre></code>

Executes the commands, on the Host :
>scp openssl-bin_1.1.1a-r0_armhf.deb root@_IP address of the board_://root

>scp openssl_1.1.1a-r0_armhf.deb root@_IP address of the board_://root

>scp openssl-conf_1.1.1a-r0_armhf.deb root@_IP address of the board_://root



Executes the commands, on the target:
>cd /root

>dpkg -i openssl-bin_1.1.1a-r0_armhf.deb

>dpkg -i openssl-conf_1.1.1a-r0_armhf.deb

>dpkg -i openssl_1.1.1a-r0_armhf.deb

>sync

2) Update openssl configuration to use module tpm2_pkcs11

add the following lines in /etc/ssl/openssl.cnf :
<pre><code>
openssl_conf = openssl_init
[openssl_init]
engines=engine_section
[engine_section]
pkcs11 = pkcs11_section
[pkcs11_section]
engine_id = pkcs11
dynamic_path = /usr/lib/engines-1.1/pkcs11.so
\#MODULE_PATH = /usr/lib/opensc-pkcs11.so
MODULE_PATH = /usr/lib/libtpm2_pkcs11.so.0
init = 0
</pre></code>

3) How to create a CSR "Certificat Signature Request" with openssl (Prerequisite : openssl installed)
Executes the command, on target :
>openssl req -engine pkcs11 -new -key "pkcs11:token=greengrass;object=greenkey;type=private;pin-value=123456" -keyform engine -out /usr/local/req.csr

This CSR "/usr/local/req.csr" is used to create clients certificats on the AWS amazon Cloud to store on the board.

There is a greengrass configuration file example to update with your AWS account parameter and certificats created, on the target : /greengrass/config/config_secu_example.json

You need also to download the root CA on Amazon site and stored it on the target greengrass/certs/root.ca.pem.

4) Connection to Amazon cloud

Before starting the greengrass core on the target you need to set the TPM2_PKCS11_STORE environment variable.

Executes the command on the target :
>export TPM2_PKCS11_STORE=/usr/local/pkcs11_tpm

### In case of trouble to reinit all the TPM/PKCS11 layers

How to reset the TPM and PKCS11 store :

Excecutes the commands, on the target:
>cd /usr/bin

>./tpm2_clear -Q

> rm /usr/local/pkcs11_tpm/*
