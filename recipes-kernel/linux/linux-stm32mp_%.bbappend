FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

# -------------------------------------------------------------
# Append hook files
#
SRC_URI += "file://pre-push"
SRC_URI += "file://checkscore.py"

HOOK_FILES = "pre-push"
HOOK_FILES += "checkscore.py"

# -------------------------------------------------------------
# Defconfig
#
KERNEL_CONFIG_FRAGMENTS_append_stm32mpcommonaws += "${WORKDIR}/fragments/4.19/fragment-01-aws.config"
SRC_URI += "file://4.19/fragment-01-aws.config;subdir=fragments"
