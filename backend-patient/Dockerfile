FROM eclipse-temurin:17
LABEL authors="matthieuchollet"

COPY target/*.jar /app/backend-patient.jar

WORKDIR /app

EXPOSE 5001

ENTRYPOINT ["java", "-jar", "backend-patient.jar"]
