FROM eclipse-temurin:17
LABEL authors="matthieuchollet"

COPY target/*.jar /app/cloud-gateway.jar

WORKDIR /app

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "cloud-gateway.jar"]
