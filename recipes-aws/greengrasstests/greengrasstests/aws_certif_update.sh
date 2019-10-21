#!/bin/bash
cd /lib
ln -sf ld-2.28.so ld-linux.so.3
cd
echo 'SetEnv "TPM2_PKCS11_STORE=/usr/local/pkcs11_tpm"' >>  /etc/ssh/sshd_config
mkdir /home/root/.ssh
sync
