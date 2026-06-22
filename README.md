# 🏦 BankingApp

A full-stack banking management system built using **Java Servlets, JSP, MySQL, and React**. The application provides secure banking operations for customers and employees through a role-based authentication system with a modern React frontend.

---

# ✨ Features

## 🔐 Authentication

- Customer Login
- Employee Login
- Session-based Authentication
- Role-Based Access Control
- Password Validation
- Rate Limiting for Login Attempts
- Secure Logout

---

## 👤 Customer Features

- Register New Customer
- View Profile
- Create Bank Account
- Deposit Money
- Withdraw Money
- Transfer Funds
- View Transaction History

---

## 👨‍💼 Employee Features

### Branch Manager

- Create Bank
- Create Branch
- Register Employees
- Register Customers
- Create Accounts
- View All Banking Operations

### Relationship Manager

- Register Customers
- Create Customer Accounts

### Cashier

- Customer Management
- Account Operations
- Deposits
- Withdrawals
- Money Transfers

### Admin

- Full Administrative Access

---

## 💳 Account Management

- Savings Account
- Current Account
- Balance Tracking
- Account Creation
- Account Lookup
- Deposit
- Withdrawal
- Transfer

---

## 💰 Transaction Management

- Deposit
- Withdrawal
- Fund Transfer
- Transaction History
- Automatic Balance Updates

---

## 🏦 Branch Management

- Create Bank
- Create Branch
- View Branches
- Employee Assignment

---

# 🛠 Tech Stack

## Backend

- Java 24
- Java Servlets
- JSP
- JDBC
- Apache Tomcat 9

## Frontend

- React
- React Router
- Axios
- Tailwind CSS

## Database

- MySQL

## Tools

- Git
- GitHub
- VS Code
- MySQL Workbench
- Figma

---

# 📂 Project Structure

```
BankingApp
│
├── backend
│   ├── servlet
│   ├── service
│   ├── repository
│   ├── repositoryimpl
│   ├── model
│   ├── database
│   └── util
│
├── frontend
│   ├── src
│   │   ├── components
│   │   ├── forms
│   │   ├── pages
│   │   ├── api
│   │   ├── layouts
│   │   └── styles
│
├── database
│
└── README.md
```

---

# 🏗 Architecture

```
React Frontend
        │
        ▼
React Router
        │
        ▼
Axios API Calls
        │
        ▼
Java Servlets
        │
        ▼
Service Layer
        │
        ▼
Repository Layer
        │
        ▼
MySQL Database
```

---

# 🔒 Security Features

- Session-Based Authentication
- Role-Based Authorization
- Password Validation
- Login Rate Limiting
- Protected Dashboard Routes
- Secure Logout
- Server-side Validation
- Client-side Validation

---

# 👥 User Roles

| Role | Permissions |
|------|-------------|
| Customer | Banking operations |
| Cashier | Customer & Account Management |
| Relationship Manager | Customer Registration & Account Creation |
| Branch Manager | Branch, Employee & Customer Management |
| Admin | Full System Access |

---

# 📊 Database Design

The application follows a normalized relational database structure.

Main entities include:

- Bank
- Branch
- Employee
- Customer
- User
- Account
- Transaction

Relationships:

```
Bank
 │
 └── Branch
        │
        ├── Employees
        └── Customers
                 │
                 ├── User
                 └── Accounts
                           │
                           └── Transactions
```

---

# 🚀 Getting Started

## Clone Repository

```bash
git clone https://github.com/<your-username>/BankingApp.git
```

---

## Configure MySQL

Create the database:

```sql
CREATE DATABASE banking_app;
```

Update your database credentials inside:

```
DatabaseInitializer.java
```

or your database configuration file.

---

## Run Backend

1. Import into Eclipse / IntelliJ.
2. Configure Apache Tomcat.
3. Deploy the project.
4. Start Tomcat.

---

## Run Frontend

```bash
cd frontend

npm install

npm run dev
```

---

# 🔧 Environment

Backend

```
Java 24
Tomcat 9
MySQL 8+
```

Frontend

```
Node.js
React
Axios
Tailwind CSS
```

---

# 📖 Concepts Used

### Java

- OOP
- Interfaces
- Inheritance
- Encapsulation
- Polymorphism

### Backend

- MVC Architecture
- Layered Architecture
- Repository Pattern
- Service Layer
- DAO Pattern
- Session Management

### Frontend

- React Components
- React Router
- Hooks
- Axios
- State Management
- Form Validation

### Database

- SQL
- Normalization
- Foreign Keys
- Transactions
- JDBC

---

# 🎨 UI

The application features a modern banking interface inspired by contemporary fintech platforms.

- Professional banking dashboard
- Responsive design
- Modern authentication screens
- Role-specific dashboards
- Clean navigation
- Consistent design system

---

# 📈 Future Improvements

- OTP Authentication
- Email Notifications
- Loan Management
- Credit Card Module
- Fixed Deposits
- Interest Calculation
- Audit Logs
- PDF Statements
- Spring Boot Migration
- Docker Deployment
- CI/CD Pipeline
- JWT Authentication
- REST API
- Microservices Architecture

---

# 📚 What I Learned

This project helped me gain practical experience in:

- Full Stack Development
- Java Servlets
- React Integration
- JDBC
- SQL Database Design
- Session Management
- Authentication & Authorization
- Role-Based Access Control
- RESTful Communication
- UI/UX Design
- Git Version Control

---

# 🤝 Contributing

Contributions, suggestions, and improvements are welcome.

Feel free to fork the repository and submit a pull request.

---

# 📄 License

This project is created for educational purposes.<img width="1440" height="771" alt="Screenshot 2026-06-22 at 12 09 40 PM" src="https://github.com/user-attachments/assets/f8be140e-0e73-4616-b348-56877bc498e2" />
<img width="1440" height="771" alt="Screenshot 2026-06-22 at 12 09 40 PM" src="https://github.com/user-attachments/assets/55a190f0-61ba-4f8f-bbd3-592d74de9637" />
