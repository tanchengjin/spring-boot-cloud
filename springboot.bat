@echo off
title Middleware
cd /d E:\smart\Middleware
java -jar -Dspring.config.location=E:/smart/Middleware/config/application-prod.yml Middleware.jar --spring.profiles.active=prod
@pause