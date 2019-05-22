#!/bin/bash
cd
echo 'SetEnv "TPM2_PKCS11_STORE=/usr/local/pkcs11_tpm"' >>  /etc/ssh/sshd_config
mkdir /home/root/.ssh
sync
