#INSANE_SKIP_${PN} +="dev-so"

do_install_append() {
    ln -sf ${libdir}/libtss2-tcti-tabrmd.so.0.0.0 ${D}${libdir}/libtss2-tcti-tabrmd.so
}


