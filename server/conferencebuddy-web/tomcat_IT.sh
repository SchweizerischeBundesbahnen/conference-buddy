#!/bin/bash
mvn -Pintegration-test-start-and-stop-tomcat -Dspring.profiles.active=tomcat,localDB -Dtomcat.http.port=8080 clean post-integration-test -DXX:+CMSClassUnloadingEnabled -DXX:MaxPermSize=128M -Ddemo.baseUrl=http://localhost:8080/conferencebuddy-web/
