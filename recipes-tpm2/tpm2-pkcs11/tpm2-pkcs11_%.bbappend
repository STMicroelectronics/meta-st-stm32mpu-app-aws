#Install tools script to initialise the PKC11/TPM2 (script python : ./tpm2_ptool.py)
  
do_install_append() {
    install -d ${D}/tools/tpm2_pkcs11
    install -m 755 ${S}/tools/*.py ${D}/tools
    install -m 755 ${S}/tools/tpm2_ptool ${D}/tools
    install -m 755 ${S}/tools/tpm2_pkcs11/* ${D}/tools/tpm2_pkcs11
}

FILES_${PN} += "/tools/*"
FILES_${PN} += "/tools/tpm2_pkcs11/*"



