FROM eclipse-temurin:17
LABEL authors="matthieuchollet"

COPY target/*.jar /app/risk.jar

WORKDIR /app

EXPOSE 6001

ENTRYPOINT ["java", "-jar", "risk.jar"]
