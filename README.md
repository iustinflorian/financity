# FinanCity | Neobank

FinanCity is a robust personal finance management application built with Spring Boot, MySQL, and Liquibase, fully containerized using Docker. It helps users make and track transactions, manage budgets, and visualize their financial health.

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
- The application requires an **.env** file for secure configurations (email services, database connection).\
- Create a file named .env in the root directory.
- An *example* is provided in the main folder.

### **4. Running the application:**
Launch the entire stack (backend + database) with a single command:

```
docker-compose up --build -d
```

The app will be available by accessing [http://localhost:8080].

Database is available at localhost:3307 (internally, the app uses 3306).

### **5. Tech Stack**
-Backend: Java 21, Spring Boot 4.x

-Database: MySQL 8.0

-Migration Tool: Liquibase

-Containerization: Docker & Docker Compose

-Security: Spring Security
