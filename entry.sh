#!/usr/bin/env bash

cp /fixed_plugins/*.jar /var/lib/openfire/plugins/
exec /sbin/entrypoint-org.sh
