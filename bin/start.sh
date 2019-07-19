#!/usr/bin/env bash

JAVA="/usr/bin/java"
SCALAVERSION="2.12"

function main() {
  exec $@
}

function exec() {
  ${JAVA} -cp target/scala-${SCALAVERSION}/http_tools-assembly-0.1.jar $1
}

main $@