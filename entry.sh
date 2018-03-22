#!/usr/bin/env bash

sudo -HEu ${OPENFIRE_USER} mkdir -p ${OPENFIRE_DATA_DIR}/plugins

sudo -HEu ${OPENFIRE_USER} cp /fixed_plugins/*.jar ${OPENFIRE_DATA_DIR}/plugins/
exec /sbin/entrypoint-org.sh
