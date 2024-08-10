# Microservices Application DiabetePredictor with Docker Compose

This project contains a microservices-based architecture using Spring Boot and Docker. The application includes services for managing patients, notes, risk analysis, and a client UI built with Next.js. Each service is containerized using Docker and orchestrated with Docker Compose.

## Table of Contents

- [Overview](#overview)
- [Architecture](#architecture)
- [Setup and Installation](#setup-and-installation)
- [Services](#services)
    - [PostgreSQL](#postgresql)
    - [MongoDB](#mongodb)
    - [Eureka Server](#eureka-server)
    - [Patients Service](#patients-service)
    - [Notes Service](#notes-service)
    - [Risk Service](#risk-service)
    - [API Gateway](#api-gateway)
    - [Client UI](#client-ui)
- [Network](#network)
- [Volumes](#volumes)
- [Health Checks](#health-checks)
- [Contributing](#contributing)
- [License](#license)

## Overview

This project demonstrates a typical microservices setup with various independent services communicating through a centralized service registry (Eureka Server). Each service is deployed as a Docker container. This setup is ideal for development, testing, and production deployment.

## Architecture

-	Service Registry: Eureka Server for service discovery.
-	Database Services: PostgreSQL for the Patients service and MongoDB for the Notes service.
-	Microservices: Individual services for managing patients, notes, and risk analysis.
-	API Gateway: A centralized gateway to route requests to the respective services.
-	Client UI: A Next.js-based user interface.

## Setup and Installation

Ensure you have Docker and Docker Compose installed on your machine before proceeding.

### Steps to Run the Application

1. Clone the Repository:
```
git clone <repository-url>
cd <repository-directory>
```

2.	Build and Run the Containers:
```
docker-compose up --build
```

3.	Access the Application:
- Client UI: http://localhost:3000/patients/
- API Gateway: http://localhost:8080/patients
- Eureka Dashboard: http://localhost:8761

### Services

#### PostgreSQL

- Image: postgres:latest
- Ports: 5432:5432
- Environment Variables:
  - POSTGRES_USER: user
  - POSTGRES_PASSWORD: password
  - POSTGRES_DB: patientdb
- Volumes:
  - ./backend-patient/src/main/resources/data/ to /docker-entrypoint-initdb.d/
  - postgres_data for persistent storage

#### MongoDB

- Image: mongo
- Ports: 27017:27017
- Environment Variables:
  - MONGO_INITDB_ROOT_USERNAME: user
  - MONGO_INITDB_ROOT_PASSWORD: password
  - MONGO_INITDB_DATABASE: notes
- Volumes:
  - ./notes/src/main/resources/data/mongo-init.js to /docker-entrypoint-initdb.d/mongo-init.js
  - mongodb_data for persistent storage

#### Eureka Server

- Container Name: eureka-server
- Ports: 8761:8761
- Environment Variables:
  - SPRING_EUREKA_URL: http://localhost:8761/eureka/
- Healthcheck:
  - URL: http://localhost:8761/actuator/health

#### Patients Service

- Build Context: ./backend-patient
- Ports: Exposed internally at 5001
- Environment Variables:
  - SPRING_DATASOURCE_URL: jdbc:postgresql://postgres:5432/patientdb
  - SPRING_DATASOURCE_USERNAME: user
  - SPRING_DATASOURCE_PASSWORD: password
  - SPRING_EUREKA_URL: http://eureka-server:8761/eureka/
- Dependencies:
  - Waits for PostgreSQL and Eureka to be ready

#### Notes Service

- Build Context: ./notes
- Ports: Exposed internally at 4001
- Environment Variables:
  - MONGO_DATASOURCE_HOST: mongodb
  - MONGO_DATASOURCE_NAME: notes
  - MONGO_DATASOURCE_USERNAME: user
  - MONGO_DATASOURCE_PASSWORD: password
  - SPRING_EUREKA_URL: http://eureka-server:8761/eureka/
- Dependencies:
  - Waits for MongoDB and Eureka to be ready

#### Risk Service

- Build Context: ./risk
- Ports: Exposed internally at 6001
- Environment Variables:
  - API_GATEWAY_URL: http://gateway:8080/
  - SPRING_EUREKA_URL: http://eureka-server:8761/eureka/
- Dependencies:
  - Waits for Eureka to be ready

#### API Gateway

- Build Context: ./cloud-gateway
- Ports: 8080:8080
- Environment Variables:
  - SPRING_APPLICATION_PATIENT_URL: http://patients:5001/
  - SPRING_APPLICATION_NOTE_URL: http://notes:4001/
  - SPRING_APPLICATION_RISK_URL: http://risk:6001/
  - SPRING_EUREKA_URL: http://eureka-server:8761/eureka/
- Dependencies:
  - Waits for all services to be ready

#### Client UI

- Build Context: ./client-ui
- Ports: 3000:3000
- Environment Variables:
  - NEXT_PUBLIC_API_URL: http://localhost:8080

### Network

All services communicate over a single Docker bridge network named medilabo-network.

### Volumes

- postgres_data: Persistent storage for PostgreSQL data.
- mongodb_data: Persistent storage for MongoDB data.

### Health Checks

- Eureka Server includes a health check endpoint at http://localhost:8761/actuator/health.

### Contributing

Contributions are welcome! Please fork the repository and create a pull request with your changes.

### License

This project is licensed under the MIT License.

Feel free to customize this README further based on your specific project details or additional configurations.
