# Copyright (C) 2019, Digi International Inc.

require greengrass.inc

#
# The Amazon Greengrass Core Product includes the following third-party software/licensing:
# github.com/aws/aws-sdk-go/; version 1.15.65 -- https://github.com/aws/aws-sdk-go/
# github.com/coreos/go-systemd/; version 10 -- https://github.com/coreos/go-systemd/
# github.com/docker/docker; version 1.12.0-rc4 -- https://github.com/docker/docker
# github.com/docker/go-units; version 0.3.1 -- https://github.com/docker/go-units
# github.com/go-ini/ini; version 1.32.0 -- https://github.com/go-ini/ini
# github.com/jmespath/go-jmespath; version 0.2.2 -- https://github.com/jmespath/go-jmespath
# github.com/mwitkow/go-http-dialer; version 0.1 -- https://github.com/mwitkow/go-http-dialer
# github.com/opencontainers/runc; version 1.0.0-rc3 -- https://github.com/opencontainers/runc
# github.com/opencontainers/runtime-spec; version 1.0.0-rc5 -- https://github.com/opencontainers/runtime-spec
# github.com/pquerna/ffjson; version 1.0 -- https://github.com/pquerna/ffjson
# github.com/vishvananda/netlink; version 0.1 -- https://github.com/vishvananda/netlink
#
# And the following Licenses:
LIC_FILES_CHKSUM = " \
    file://ggc/core/THIRD-PARTY-LICENSES;md5=53b6a4caa097863bc3971d5e0ac6d1db \
"

SRC_URI[arm.md5sum] = "1b8bc05b42853c16a4d35877052ef0c5"
SRC_URI[arm.sha256sum] ="4d98adab4ac3de466f67daa511d4ca06b1703a573e836ce67d7fcef96789cc7e"

do_install_append() {
  install -m 0644 ${WORKDIR}/config_secu_example.json ${D}/greengrass/config/config_secu_example.json
  install -m 0644 ${WORKDIR}/tpm_update.sh ${D}/greengrass/tpm_update.sh
}

RDEPENDS_${PN} += "opensc openssl libp11"
