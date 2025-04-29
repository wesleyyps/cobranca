#!/bin/bash
echo 'Setting up pre-requisites for application "cobranca" in development environment'

ENV_FILE="./.env"
DOCKER_VOLUMES_PATH="./docker-volumes"
MYSQL_VOLUME_PATH="${DOCKER_VOLUMES_PATH}/mysql"
MAILER_VOLUME_PATH="${DOCKER_VOLUMES_PATH}/mailer"

if ! test -f "${ENV_FILE}"; then
    cp ./.env-example "${ENV_FILE}"
fi
if ! test -d "${DOCKER_VOLUMES_PATH}"; then
    mkdir "${DOCKER_VOLUMES_PATH}"
    chmod 755 "${DOCKER_VOLUMES_PATH}"
fi
if ! test -d "${MYSQL_VOLUME_PATH}"; then
    mkdir "${MYSQL_VOLUME_PATH}"
fi
if ! test -d "${MAILER_VOLUME_PATH}"; then
    mkdir "${MAILER_VOLUME_PATH}"
fi

echo "Copying application-development.properties into application.properties"
cp src/main/resources/application-development.properties src/main/resources/application.properties