#!/bin/bash
mvn -Dspring.profiles.active=tomcat,localDB,development tomcat7:run
