# Getting Started
## Wallet Demo

### Wallet Demo Architecture Document

**Written By:** Daniel Castro  
**Senior Analyst**  
**Date:** 30/11/2024

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