# Configure recipe for CubeMX

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

KERNEL_CONFIG_FRAGMENTS_append_stm32mpcommonmx += "${WORKDIR}/fragments/5.4/fragment-01-aws.config"
SRC_URI += "file://5.4/fragment-01-aws.config;subdir=fragments"
