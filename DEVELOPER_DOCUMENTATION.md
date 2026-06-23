# BankingApp Developer Documentation

---

# Table of Contents

1. Project Overview
2. System Architecture
3. Technology Stack
4. Project Structure
5. Request Flow
6. Database Design
7. Authentication Flow
8. Role-Based Access Control
9. Backend Architecture
10. Frontend Architecture
11. API Endpoints
12. Database Schema
13. Session Management
14. Security Features
15. Running the Project
16. Code Conventions
17. Future Improvements

---

# 1. Project Overview

BankingApp is a full-stack banking management application developed using Java Servlets, JDBC, MySQL, and React.

The project demonstrates enterprise application architecture through separation of concerns using:

- MVC Pattern
- Repository Pattern
- Service Layer
- Role-Based Authentication
- Session Management
- React Frontend
- REST-style Servlet Communication

The system supports banking operations for customers and employees while enforcing access permissions based on employee roles.

---

# 2. System Architecture

```
                React Frontend
                       │
             Axios HTTP Requests
                       │
                       ▼
              Java Servlet Layer
                       │
                       ▼
               Service Layer
                       │
                       ▼
            Repository Interfaces
                       │
                       ▼
       Repository Implementations
                       │
                       ▼
               JDBC / MySQL
```

---

# 3. Technology Stack

## Frontend

- React
- React Router
- Axios
- Tailwind CSS

## Backend

- Java 24
- Java Servlets
- JSP (legacy)
- JDBC
- Apache Tomcat

## Database

- MySQL

---

# 4. Project Structure

```
BankingApp

backend/

    servlet/

        LoginServlet
        AccountServlet
        CustomerServlet
        TransactionServlet
        EmployeeServlet
        LogoutServlet

    service/

        AccountService
        CustomerService
        EmployeeService
        TransactionService

    repository/

        AccountRepository
        CustomerRepository
        EmployeeRepository

    repositoryimpl/

        AccountRepositoryImpl
        CustomerRepositoryImpl
        EmployeeRepositoryImpl

    model/

    util/

    database/

frontend/

    src/

        api/

        components/

        forms/

        layouts/

        pages/

        styles/

database/

README.md

DEVELOPER_DOCUMENTATION.md
```

---

# 5. Request Flow

Example: Customer Login

```
User

↓

React Login Form

↓

Axios POST

↓

LoginServlet

↓

UserService

↓

UserRepository

↓

MySQL

↓

User Object

↓

Servlet Response

↓

React Router

↓

Customer Dashboard
```

---

# 6. Layer Responsibilities

## Servlet Layer

Responsible for

- Receiving HTTP Requests
- Input validation
- Session handling
- Calling Service Layer
- Returning responses

No business logic should exist here.

---

## Service Layer

Responsible for

- Business rules
- Validation
- Authorization
- Transaction logic

Services should never communicate directly with MySQL.

---

## Repository Layer

Responsible for

- SQL queries
- CRUD operations
- ResultSet mapping

Repositories should not contain business rules.

---

# 7. Authentication Flow

```
Login Form

↓

POST /login

↓

Validate Credentials

↓

Create Session

↓

Store Logged-in User

↓

Redirect to Dashboard
```

Session Attributes

```
user

employee

customer

role
```

---

# 8. Role-Based Access Control

## Customer

Permissions

- View Accounts
- Deposit
- Withdraw
- Transfer
- Transaction History

---

## Cashier

Permissions

- Customer Management
- Account Operations
- Deposit
- Withdrawal
- Transfer

---

## Relationship Manager

Permissions

- Customer Registration
- Account Creation

---

## Branch Manager

Permissions

- Bank Management
- Branch Management
- Employee Registration
- Customer Registration
- Account Creation

---

## Admin

Permissions

- Full Access

---

# 9. Backend Architecture

Repository Pattern

```
AccountRepository

↓

AccountRepositoryImpl
```

Service Pattern

```
AccountService

↓

AccountRepository
```

Servlet Pattern

```
AccountServlet

↓

AccountService
```

---

# 10. Frontend Architecture

```
pages/

components/

forms/

api/

layouts/
```

Pages

Responsible for routing.

Components

Reusable UI.

Forms

Validation.

API

Axios wrappers.

Layouts

Shared dashboard structure.

---

# 11. API Endpoints

## Authentication

POST

```
/login
```

POST

```
/logout
```

---

## Customer

POST

```
/register
```

GET

```
/customer
```

---

## Employee

POST

```
/register-employee
```

GET

```
/employee
```

---

## Accounts

POST

```
/account
```

GET

```
/account
```

PUT

```
/account
```

DELETE

```
/account
```

---

## Transactions

POST

```
/transaction
```

GET

```
/transaction
```

---

# 12. Database Schema

## Tables

```
bank

branch

employee

customer

user

account

transaction
```

---

Entity Relationships

```
Bank

└── Branch

      ├── Employee

      └── Customer

             ├── User

             └── Account

                     └── Transaction
```

---

# 13. Session Management

After successful authentication

```
HttpSession session

↓

session.setAttribute(...)
```

Protected resources verify

```
session != null

&&

user != null
```

Logout

```
invalidateSession()

↓

Redirect Login
```

---

# 14. Security Features

Implemented

✔ Session Authentication

✔ Role-Based Authorization

✔ Password Validation

✔ Login Rate Limiting

✔ Client Validation

✔ Server Validation

✔ Protected Dashboard

Recommended

- BCrypt Password Hashing
- CSRF Protection
- JWT
- Refresh Tokens
- HTTPS

---

# 15. Error Handling

Frontend

- Validation Messages
- Toast Notifications
- Loading Indicators

Backend

- Try/Catch
- SQL Exception Handling
- Validation Errors
- HTTP Status Codes

---

# 16. Coding Standards

## Naming

Classes

```
PascalCase
```

Variables

```
camelCase
```

Constants

```
UPPER_CASE
```

---

## Repository

One responsibility

Only SQL

---

## Services

Business logic only

---

## Servlets

HTTP handling only

---

# 17. Design Principles

Applied

- SOLID
- DRY
- Separation of Concerns
- MVC
- Repository Pattern
- Layered Architecture

---

# 18. Future Enhancements

Authentication

- OTP Login
- Email Verification
- Password Reset

Banking

- Loan Module
- Fixed Deposit
- Recurring Deposit
- EMI Calculator

Technical

- Spring Boot
- Spring Security
- JWT
- Hibernate
- Docker
- Kubernetes
- CI/CD

---

# 19. Deployment

Backend

Apache Tomcat

Frontend

React Production Build

Database

MySQL

---

# 20. Development Workflow

```
Create Feature Branch

↓

Implement Backend

↓

Test API

↓

Connect React

↓

Test UI

↓

Git Commit

↓

Pull Request

↓

Merge
```

---

# 21. Sequence Diagram

Customer Deposit

```
Customer

↓

React Form

↓

Axios

↓

TransactionServlet

↓

TransactionService

↓

AccountRepository

↓

MySQL

↓

Updated Balance

↓

React Dashboard
```

---

# 22. Lessons Learned

This project demonstrates practical implementation of

- Enterprise Java
- Layered Architecture
- Repository Pattern
- MVC
- React Integration
- REST Communication
- JDBC
- Session Management
- Authentication
- Role-Based Access Control
- Database Normalization
- Frontend Component Architecture