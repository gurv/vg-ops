#!/bin/sh
set -ex

#TODO адрес Docker-хоста
#export DOCKER_HOST='tcp://docker:2375' # что здесь должно быть?
# под Linux внутри контейнера можно так определить адрес Docker-хоста
#   route -n | awk '/UG[ \t]/{print $2}'

if [ "$1" = 'docker' ]; then
    shift
    /opt/docker "$@"
else
    /usr/sbin/sshd -D
    "$@"
fi