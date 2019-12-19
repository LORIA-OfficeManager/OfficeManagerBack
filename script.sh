#!/bin/bash

cd /deploy/officemanager-back
fkill :51000
docker-compose down
docker-compose up -d
sudo chmod -R 777 db_officeManager
java -Dserver.port=51000 -jar target/OfficeManager-0.0.1-SNAPSHOT.jar &
