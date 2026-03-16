# FinanCity | Neobank

FinanCity is a robust personal finance management application built with Spring Boot, MySQL, and Liquibase, fully containerized using Docker. It helps users make and track transactions, manage budgets, and visualize their financial health.

This is the backend. For the frontend, please visit [financity-web github repository](https://github.com/iustinflorian/financity-web).

## How to run ?

You don't need Java or MySQL installed locally. The entire stack runs inside Docker containers.
### **1. Prerequisites**
- Docker Desktop installed and running.
  
- A Git client to clone the repository.
### **2. Clone the repository to your local machine**
```
git clone https://github.com/iustinflorian/financity.git
cd financity
```

### **3. Configure environment variables:**
- The application requires an **.env** file for secure configurations (email services, database connection).
  
- Create a file named .env in the root directory.
  
- An *example* is provided in the main folder.

### **4. Running the application:**
Launch the entire stack (backend + database) with a single command:

```
docker-compose up --build -d
```

### **5. Testing REST endpoints can be done using Postman:**

- Download [Postman Collection](./financity.postman_collection.json)

- Open Postman and click import.

- Select the file.

Database is available at localhost:3307 (internally, the app uses 3306).

## **Tech Stack**
-Backend: Java 21, Spring Boot 4.x

-Database: MySQL 8.0

-Migration Tool: Liquibase

-Containerization: Docker & Docker Compose

-Security: Spring Security

## **License**
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
