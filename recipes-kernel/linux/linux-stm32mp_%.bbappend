FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

# -------------------------------------------------------------
# Defconfig
#
KERNEL_CONFIG_FRAGMENTS_append_stm32mpcommonaws += "${WORKDIR}/fragments/4.19/fragment-01-aws.config"
SRC_URI += "file://4.19/fragment-01-aws.config;subdir=fragments"
