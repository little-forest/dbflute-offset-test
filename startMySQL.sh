#!/bin/sh

DB_NAME=dbflute-test

docker run --rm --name ${DB_NAME} \
  -p 3306:3306 \
  -e MYSQL_ROOT_PASSWORD=rootpass \
  -e MYSQL_DATABASE=${DB_NAME} \
  -e MYSQL_USER=test \
  -e MYSQL_PASSWORD=testpass \
  -d mysql:5.7 \
  --character-set-server=utf8mb4 \
  --collation-server=utf8mb4_unicode_ci \
  --skip_ssl

