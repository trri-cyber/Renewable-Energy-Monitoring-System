# Renewable-Energy-Monitoring-System
A Spring Boot microservices-based Renewable Energy Monitoring System featuring Solar, Wind, Battery, and Energy Distribution services integrated through Spring Cloud Gateway, with REST APIs, MySQL, validation, and exception handling.


# 🌱 Renewable Energy Monitoring System

A **Spring Boot Microservices-based Renewable Energy Monitoring System** designed to monitor renewable energy generation, battery storage, and energy distribution.

The system is built using **Spring Boot**, **Spring Cloud Gateway**, **Spring Data JPA**, and **MySQL**, following a **Microservices Architecture** where each service is independently developed and deployed.

---

## 📌 Project Overview

This project simulates a renewable energy ecosystem where electricity generated from **Solar Panels** and **Wind Turbines** is monitored, stored in **Battery Systems**, and distributed efficiently using an **Energy Distribution Service**.

Each service manages its own business logic and communicates through a centralized **API Gateway**, providing a scalable and modular architecture.

---

# 🏗️ System Architecture

```
                    Client
                       │
                       ▼
            Spring Cloud Gateway
                       │
      ┌────────────────┼────────────────┐
      │                │                │
      ▼                ▼                ▼
 Solar Service    Wind Service    Battery Service
      │                │                │
      └────────────────┼────────────────┘
                       │
                       ▼
         Energy Distribution Service
                       │
                       ▼
                    MySQL
```

---

# 🚀 Microservices

## ☀️ Solar Service (Port: 8081)

Manages solar panel registration and energy generation.

### Features

- Register Solar Panels
- View All Solar Panels
- Retrieve Solar Panel by ID
- Update Solar Panel Details
- Delete Solar Panel
- Update Generated Energy
- Calculate Total Generated Solar Energy

---

## 🌬️ Wind Service (Port: 8082)

Handles wind turbine management and energy generation.

### Features

- Register Wind Turbines
- View All Wind Turbines
- Retrieve Wind Turbine by ID
- Search by Location
- Update Wind Turbine
- Delete Wind Turbine
- Update Generated Energy
- Calculate Total Generated Wind Energy

---

## 🔋 Battery Service (Port: 8083)

Responsible for battery registration and renewable energy storage.

### Features

- Register Battery
- View All Batteries
- Retrieve Battery by ID
- Search Batteries by Location
- Store Excess Energy
- Update Battery Details
- Delete Battery

---

## ⚡ Energy Distribution Service (Port: 8084)

Distributes renewable energy to consumers while maintaining energy balance.

### Features

- Create Energy Distribution
- Update Distribution
- Delete Distribution
- Search by Source Type
- Search by Destination
- Generate Daily Reports
- Detect Distribution Faults
- Balance Energy Supply
- Store Excess Energy in Batteries

---

## 🌐 API Gateway (Port: 8080)

Acts as the single entry point for all client requests.

### Gateway Routes

| Route | Service |
|--------|---------|
| `/solar/**` | Solar Service |
| `/wind/**` | Wind Service |
| `/battery/**` | Battery Service |
| `/distribution/**` | Energy Distribution Service |

---

# 🛠️ Technology Stack

| Technology | Purpose |
|------------|---------|
| Java 17 | Programming Language |
| Spring Boot | Backend Framework |
| Spring Cloud Gateway | API Gateway |
| Spring Data JPA | Database Access |
| Hibernate | ORM |
| MySQL | Database |
| Maven | Build Tool |
| Lombok | Boilerplate Reduction |
| Jakarta Validation | Input Validation |
| SLF4J | Logging |

---

# 📂 Project Structure

```
Renewable-Energy-Monitoring-System
│
├── APIGateway
├── SolarService
├── WindService
├── BatteryService
├── DistributionService
│
├── screenshots
├── README.md
└── .gitignore
```

---

# 🔄 Project Workflow

```
Solar Panel
      │
      ▼
Generate Solar Energy
      │
      ▼
Wind Turbine
      │
      ▼
Generate Wind Energy
      │
      ▼
Calculate Total Generated Energy
      │
      ▼
Energy Distribution Request
      │
      ▼
Enough Energy Available?
      │
 ┌────┴────┐
 │         │
 ▼         ▼
Yes        No
 │          │
 ▼          ▼
Distribute  Store Excess Energy
Energy      in Battery
 │
 ▼
Generate Reports & Monitor Faults
```

---

# 📖 REST API Endpoints

## ☀️ Solar Service

| Method | Endpoint |
|---------|----------|
| POST | `/solar/solar-panels` |
| GET | `/solar/solar-panels` |
| GET | `/solar/solar-panels/{id}` |
| PUT | `/solar/solar-panels/{id}` |
| DELETE | `/solar/solar-panels/{id}` |
| PUT | `/solar/solar-panels/{id}/generation` |
| GET | `/solar/solar-panels/total-generated` |

---

## 🌬️ Wind Service

| Method | Endpoint |
|---------|----------|
| POST | `/wind/wind-turbines` |
| GET | `/wind/wind-turbines` |
| GET | `/wind/wind-turbines/{id}` |
| GET | `/wind/wind-turbines/location/{location}` |
| PUT | `/wind/wind-turbines/{id}` |
| DELETE | `/wind/wind-turbines/{id}` |
| PUT | `/wind/wind-turbines/{id}/generation` |
| GET | `/wind/wind-turbines/total-generated` |

---

## 🔋 Battery Service

| Method | Endpoint |
|---------|----------|
| POST | `/battery/batteries` |
| GET | `/battery/batteries` |
| GET | `/battery/batteries/{id}` |
| GET | `/battery/batteries/location/{location}` |
| POST | `/battery/batteries/{id}/store` |
| PUT | `/battery/batteries/{id}` |
| DELETE | `/battery/batteries/{id}` |

---

## ⚡ Energy Distribution Service

| Method | Endpoint |
|---------|----------|
| POST | `/distribution/distributions` |
| GET | `/distribution/distributions` |
| GET | `/distribution/distributions/{id}` |
| GET | `/distribution/distributions/source/{sourceType}` |
| GET | `/distribution/distributions/destination/{destination}` |
| PUT | `/distribution/distributions/{id}` |
| DELETE | `/distribution/distributions/{id}` |
| POST | `/distribution/distributions/balance` |
| POST | `/distribution/distributions/store-excess` |
| GET | `/distribution/distributions/reports/daily` |
| GET | `/distribution/distributions/faults` |

---

# ✅ Key Features

- Microservices Architecture
- Spring Cloud Gateway
- RESTful APIs
- CRUD Operations
- Energy Monitoring
- Battery Storage Management
- Energy Distribution
- Daily Report Generation
- Fault Detection
- Request Validation
- Global Exception Handling
- SLF4J Logging
- MySQL Integration

---

# ▶️ Getting Started

## Clone the Repository

```bash
git clone https://github.com/trri-cyber/Renewable-Energy-Monitoring-System.git
```

---

## Configure MySQL

Update the database configuration in each microservice:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/renewable_energy
spring.datasource.username=root
spring.datasource.password=your_password
```

---

## Run the Services

Start the applications in the following order:

1. Solar Service
2. Wind Service
3. Battery Service
4. Energy Distribution Service
5. API Gateway

The application will be available through:

```
http://localhost:8080
```

---

# 📸 Screenshots

The `screenshots` folder contains:

- API Gateway Routing
- Solar Service APIs
- Wind Service APIs
- Battery Service APIs
- Distribution Service APIs
- MySQL Database

---

# 📬 Postman Collection

The complete Postman collection is available in the `postman` folder for testing all REST APIs.

---

# 🔮 Future Enhancements

- Service Discovery (Eureka)
- Circuit Breaker (Resilience4j)
- Docker Containerization
- Kubernetes Deployment
- JWT Authentication
- Swagger / OpenAPI Documentation
- Monitoring with Prometheus & Grafana

---

# 👨‍💻 Author

**Trri Cyber**

GitHub: https://github.com/trri-cyber

---

# 📄 License

This project was developed for educational purposes to demonstrate **Spring Boot Microservices**, **Spring Cloud Gateway**, **REST APIs**, and **MySQL** integration.
