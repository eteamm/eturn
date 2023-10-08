#!/usr/bin/env bash
mvn clean package
echo 'Copy files...'
scp PATHTOTARGET/target/NAMEFILE.jar \
    root@IP:/home/USER/web/DOMAIN/public_shtml
echo 'Restart server...'
ssh -t root@IP << EOF
pgrep java | xargs kill -9
nohup java -jar /home/USER/web/DOMAIN/public_shtml/NAMEFILE.jar > /home/USER/web/DOMAIN/log.txt &
EOF
echo 'Bye'