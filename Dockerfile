# Alpine because it's lighter
FROM openjdk:8-jdk-alpine
MAINTAINER Ramazan Sakin <ramazansakin63@gmail.com>

# Add JAR file and run it as entrypoint
ADD target/credit-service.jar credit-service.jar
ENTRYPOINT ["java", "-jar", "credit-service.jar"]

# Expose the port
EXPOSE 8091