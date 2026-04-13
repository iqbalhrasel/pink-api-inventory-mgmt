# Pink Feather Inventory & Sales Management System
A comprehensive **Inventory and Sales Management System** built with modern Java technologies. This system provides role-based access control (RBAC), secure JWT authentication, and robust inventory tracking capabilities.

## Features

### Core Functionality
- **Inventory Management**
    - Add, update, delete, and track products
    - Real-time stock level monitoring
    - Product categorization and search

- **Sales Management**
    - Create and process sales orders
    - Store customer info
    - Sales history and analytics

- **User Management (RBAC)**
    - Multiple roles: ADMIN, USER
    - Granular permission control

- **Security**
    - JWT-based authentication
    - Spring Security integration
    - Password encryption (BCrypt)
    - Stateless Session management

- **Database**
    - MySQL with Flyway migration management
    - Automated schema versioning
    - Data integrity and relationships

## Tech Stack

| Technology | Version | Purpose |
|------------|---------|---------|
| Java | 21      | Backend core |
| Spring Boot | 3.5.x   | Application framework |
| Spring Security | 6.x     | Authentication & Authorization |
| Spring Data JPA | -       | ORM & Database access |
| MySQL | 8.4     | Relational database |
| Flyway | -       | Database migrations |
| JWT | -       | Token-based authentication |
| Maven | -       | Build automation |

## Prerequisites

- **JDK 21**
- **MySQL 8.4**
- **Maven**
- **Git**

## Installation & Setup

### 1. Clone the Repository
```bash
git clone https://github.com/iqbalhrasel/pink-api-inventory-mgmt
```

### 2. Build the Application
```bash
mvn clean install
```

### 3. Run the Application
```bash
mvn spring-boot:run
```

The application will start at http://localhost:8080