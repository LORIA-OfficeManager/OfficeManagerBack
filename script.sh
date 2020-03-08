#!/bin/bash

cd /deploy/officemanager-back
docker-compose down --volumes
docker-compose up -d --build
echo "Build success"
