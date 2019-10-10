#!/bin/bash
mkdir /usr/local/pkcs11_tpm
cd /usr/lib
ln -sf libtss2-tcti-device.so.0.0.0 libtss2-tcti-device.so
ln -sf libtss2-tcti-tabrmd.so.0.0.0 libtss2-tcti-tabrmd.so
export TPM2_PKCS11_STORE=/usr/local/pkcs11_tpm
sync


