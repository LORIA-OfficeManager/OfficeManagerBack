#!/bin/bash

cd /deploy/officemanager-back
fkill :51000
docker-compose down
docker-compose up -d
sudo chmod -R 777 db_officeManager/*
java -Dserver.port=51000 -Dspring.profiles.active=prod -jar target/OfficeManagerBack-1.0-SNAPSHOT
