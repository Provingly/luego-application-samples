#!/bin/bash
echo "Start Provingly Server in Docker using app specific folder"
docker container run -v ./LOCAL/:/var/lib/Luego/  -d -p 9000:9000 -e PROVINGLY_SERVER_ENV='stage' provingly/luego-server-apis:1.0.0-b49