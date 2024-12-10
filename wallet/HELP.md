# Getting Started
## Wallet Demo

### Wallet Demo Architecture Document

**Written By:** Daniel Castro  
**Senior Analyst**  
**Date:** 30/11/2024

---

## Background
This document outlines the architecture of the Wallet application, which is designed as a proof of concept (PoC) to demonstrate basic functionalities using the Spring framework.

## Requirements

### Functional Requirements
- **Create Wallet:** Allow users to create wallets.
- **Retrieve Balance:** Provide the ability to check the current balance of a user's wallet.
- **Retrieve Historical Balance:** Enable users to access the balance of their wallet at a specific point in the past.
- **Deposit Funds:** Facilitate the process for users to deposit money into their wallets.
- **Withdraw Funds:** Enable users to withdraw money from their wallets.
- **Transfer Funds:** Support the transfer of funds between user wallets.

### Non-Functional Requirements
- This service is mission-critical; any downtime would severely impact the platform's operational capabilities.
- Given that the service manages monetary transactions, it is essential to ensure comprehensive traceability of all operations to facilitate auditing of wallet balances.

## Technology Stack
Spring Boot was chosen due to my familiarity with Java and its alignment with the functional requirements of this application.

## Overall Architecture
- **Service Interface:** Exposes a REST API to clients. This layer handles incoming requests and forwards them to the Business Logic layer.
- **Business Logic:** Validates requests received from the Service Interface layer and forwards them to the Data Access layer.
- **Data Access:** Translates requests from the Business Logic layer into database queries (e.g., SQL statements) and returns the results.
- **Logging:** Records logs to both a file and the console.

Information on how to execute and test the endpoints can be accessed at: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html).

---

## Run the Application
Use the commands below to run the application.

### To Compile:
```bash
mvn clean compile jib:build