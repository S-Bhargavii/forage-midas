# Midas
This project is the repository for the JPMC Advanced Software Engineering Forage program.

## 📘 Overview

**Midas Core** is a Spring Boot microservice that performs the following:

- Consumes user transaction messages from a **Kafka topic**.
- Stores valid transactions in an in-memory **H2 database**.
- Makes POST requests to the Incentive microservice.
- Exposes a **REST API (`/balance`)** for querying user balances.


## ⚙️ Technologies Used and Learnt

- **Java 17+**
- **Spring Boot**
- **Spring Web**
- **Spring Kafka**
- **Spring Data JPA**
- **H2 Database**
- **RestTemplate (HTTP client)**


## Certificate

<img src="https://github.com/user-attachments/assets/a8db2adb-65f9-47a5-837d-f869764201b6" width="500"/>
