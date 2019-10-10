#!/bin/bash
cd /lib
ln -sf ld-2.28.so ld-linux.so.3
cd
useradd -d /home/AWStest -p 6MjtR.O3wKMWk AWStest
chmod go+rw /dev/tpm0
usermod -aG sudo AWStest
echo 'AWStest ALL=(ALL) NOPASSWD: ALL' >> /etc/sudoers
echo 'SetEnv "TPM2_PKCS11_STORE=/usr/local/pkcs11_tpm"' >>  /etc/ssh/sshd_config
mkdir /home/root/.ssh
mkdir /home/AWStest/.ssh
chown AWStest:AWStest /home/AWStest/.ssh
sync






