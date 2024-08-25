# Microservices Application DiabetePredictor

This project implements a microservices architecture using Spring Cloud for centralized configuration management and Eureka for service discovery. Each microservice is containerized using Docker and orchestrated with Docker Compose.

## Table of Contents

- [Overview](#overview)
- [Architecture](#architecture)
- [Prerequisites](#prerequisites)
- [Configuration](#configuration)
- [Network](#network)
- [Volumes](#volumes)
- [Health Checks](#health-checks)
- [Contributing](#contributing)
- [License](#license)

## Overview

- **PostgreSQL**: Database for patient data.
- **MongoDB**: Database for notes.
- **Eureka Server**: Service discovery server.
- **Config Server**: Centralized configuration management.
- **Patients Service**: Microservice for managing patients.
- **Notes Service**: Microservice for note-taking.
- **Risk Service**: Microservice for risk assessment.
- **API Gateway**: Routes requests to the appropriate services.
- **Next.js**: Frontend application.

## Architecture

-	Service Registry: Eureka Server for service discovery.
-	Database Services: PostgreSQL for the Patients service and MongoDB for the Notes service.
-	Microservices: Individual services for managing patients, notes, and risk analysis.
-	API Gateway: A centralized gateway to route requests to the respective services.
-	Client UI: A Next.js-based user interface.

## Prerequisites

Before starting, ensure you have the following installed:

- [Docker](https://docs.docker.com/get-docker/)
- [Docker Compose](https://docs.docker.com/compose/install/)

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

### Configuration

#### Spring Cloud Config
The services are configured to use the centralized configuration server :
```
spring:
  application:
    name: <microservice-name>
  cloud:
    config:
      uri: http://${CONFIG_SERVER_URL}:${CONFIG_SERVER_PORT}
      username: ${SPRING_SECURITY_USERNAME}
      password: ${SPRING_SECURITY_PASSWORD}
```

### Network

All services communicate over a single Docker bridge network named medilabo-network.

### Volumes

- postgres_data: Persistent storage for PostgreSQL data.
- mongodb_data: Persistent storage for MongoDB data.

### Health Checks

Health checks are configured to ensure critical services are functioning correctly:

- Eureka Server: http://localhost:8761/actuator/health
- Config Server: http://localhost:9101/actuator/health

## Green Code Principles in the Project

### Implemented Green Code Practices

In this project, we have implemented several practices aligned with the principles of Green Code to reduce the overall energy consumption and improve efficiency. These practices focus on minimizing resource waste and optimizing performance across the microservices architecture.

#### 1. Pagination for Efficient Data Retrieval
We have implemented pagination when retrieving patient data, which significantly reduces the volume of data transferred and processed at any given time. This minimizes memory usage, processing power, and ultimately energy consumption. The pagination is currently applied to the Patients service and could be extended to other areas, such as the Notes service.

#### 2. Caching Mechanism with React Query and Next.js
On the frontend, we utilize the caching mechanism provided by React Query and Next.js. This reduces the number of network requests by caching previously retrieved data. This not only enhances performance and user experience but also reduces the server load and energy consumption associated with redundant API calls.

#### 3. Scalable Microservices Architecture
The use of microservices allows for targeted scalability, ensuring that only the necessary services are scaled based on demand, rather than the entire application. This minimizes energy consumption by avoiding the scaling of idle services.

#### 4. Future Enhancements
In the future, we plan to:
- Implement pagination for patient notes to further reduce data load and resource consumption.
- Store the calculated diabetes risk to avoid unnecessary recalculations during every request. This will help in conserving processing power and optimize database queries.

### Contributing

Contributions are welcome! Please fork the repository and create a pull request with your changes.

### License

This project is licensed under the MIT License.

Feel free to customize this README further based on your specific project details or additional configurations.
