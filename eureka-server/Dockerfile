FROM eclipse-temurin:17
LABEL authors="matthieuchollet"

COPY target/*.jar /app/eureka-server.jar

WORKDIR /app

EXPOSE 8761

ENTRYPOINT ["java", "-jar", "eureka-server.jar"]
