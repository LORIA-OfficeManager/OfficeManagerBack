#!/bin/bash

cd /deploy/officemanager-back
fkill :51000
docker-compose down
docker-compose up -d --build
echo "Build success"
