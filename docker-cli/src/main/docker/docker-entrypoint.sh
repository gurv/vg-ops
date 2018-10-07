#!/bin/sh
set -ex

if [ "$1" = 'docker' ]; then
    shift
    /opt/docker "$@"
else
    /usr/sbin/sshd -D
    "$@"
fi
