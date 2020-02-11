#!/bin/bash

cd /deploy/officemanager-back
docker-compose down
docker-compose up -d --build
echo "Build success"
