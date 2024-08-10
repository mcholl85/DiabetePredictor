FROM maven:3.8.5-openjdk-17 AS build
LABEL authors="matthieuchollet"

WORKDIR /app

COPY . .

RUN mvn clean package -DskipTests

FROM eclipse-temurin:17

WORKDIR /app

COPY --from=build /app/target/*.jar risk.jar

EXPOSE 6001

ENTRYPOINT ["java", "-jar", "risk.jar"]
