#!/bin/bash
echo "Start Provingly Server in Docker using shared app folder"
docker container run -v /var/lib/Luego/:/var/lib/Luego/ -d -p 9000:9000 -e PROVINGLY_SERVER_ENV='stage' provingly/luego-server-apis:1.0.0-b49