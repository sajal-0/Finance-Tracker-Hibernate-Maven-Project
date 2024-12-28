# Finance Tracker

## Overview
The **Finance Tracker** is a Java-based application that allows users to manage their personal financial transactions. It provides functionality for users to add, update, delete, and view their transaction summaries while maintaining data integrity through a Hibernate-based ORM layer and a MySQL database backend.

---

## Features
- **User Authentication**
  - Manage users with unique IDs.
  
- **CRUD Operations**
  - Add new transactions.
  - Update existing transactions.
  - Delete transactions.
  - View all transactions for a specific user.

- **Expense Management**
  - Calculate total expenses for a specific month.

- **Data Management**
  - Leverages Hibernate ORM for interaction with the MySQL database.

---

## Technologies Used

### Programming and Frameworks
- **Java** (JDK 8 or above)
- **Hibernate ORM** for database interaction
- **Maven** for dependency management

### Database
- **MySQL**

### Libraries
- Hibernate Core
- Hibernate Validator

### Tools
- **IntelliJ IDEA** or **Eclipse IDE**
- **MySQL Workbench**

---

## Prerequisites
1. **Java JDK** (8 or above)
2. **Apache Maven** (Version 3 or above)
3. **MySQL Database**

---

## Getting Started

### Setting up MySQL Database

1. Create a database in MySQL:
   ```sql
   CREATE DATABASE transaction_management;
   ```

2. Run the following SQL commands to create tables:

   ```sql
   CREATE TABLE User (
       userID INT PRIMARY KEY AUTO_INCREMENT,
       username VARCHAR(50) NOT NULL,
       email VARCHAR(100) NOT NULL,
       password VARCHAR(100) NOT NULL
   );

   CREATE TABLE TransactionSummary (
       traID INT PRIMARY KEY AUTO_INCREMENT,
       amount DOUBLE NOT NULL,
       type ENUM('Income', 'Expense') NOT NULL,
       category VARCHAR(100),
       description VARCHAR(255),
       transaction_date DATE NOT NULL,
       userID INT,
       FOREIGN KEY (userID) REFERENCES User(userID)
   );
   ```

### Configuring the Project

1. Update the **`hibernate.cfg.xml`** file located in the resources folder with your MySQL credentials:

   ```xml
   <property name="hibernate.connection.url">jdbc:mysql://localhost:3306/transaction_management</property>
   <property name="hibernate.connection.username">YOUR_USERNAME</property>
   <property name="hibernate.connection.password">YOUR_PASSWORD</property>
   ```

2. Install dependencies using Maven:
   ```bash
   mvn clean install
   ```

---

## Running the Application

1. Compile the project:
   ```bash
   mvn compile
   ```

2. Run the application:
   ```bash
   mvn exec:java -Dexec.mainClass="com.tka.client.Client"
   ```

3. Interact with the menu-driven console application.

---

## Application Flow

### Main Functionalities
- **Add Transaction**
  - Provides a form to enter details such as amount, type, category, date, and description.

- **Update Transaction**
  - Updates specified fields of an existing transaction based on its ID.

- **Delete Transaction**
  - Removes a transaction by its ID after validation.

- **View Transactions**
  - Lists all transactions belonging to a logged-in user.

- **Calculate Monthly Expense**
  - Totals up the user's expenses for a given month and year.

### Code Structure

- `UserTransactionDao`
  - Handles all database-level operations (CRUD).

- `TransactionService`
  - Contains business logic and data validation.

- `TransactionController`
  - Acts as a bridge between the service layer and client interface.

- `Client`
  - Provides the menu-driven CLI for user interaction.

---

## Example Commands

1. Add a Transaction:
   - Input:
     ```plaintext
     Enter Amount: 1500.00
     Enter Type (Income/Expense): Expense
     Enter Category: Food
     Enter Description: Lunch at a restaurant
     ```

   - Output:
     ```plaintext
     Transaction added successfully!
     ```

2. View Transactions:
   - Input: Select option 2

   - Output:
     ```plaintext
     Transaction List:
     [ID: 1, Amount: 1500.00, Type: Expense, Category: Food, Date: 2024-12-21, Description: Lunch]
     ```

3. Calculate Monthly Expense:
   - Input:
     ```plaintext
     Enter Year: 2024
     Enter Month: 12
     ```

   - Output:
     ```plaintext
     Total Expense for 2024-12: 1500.00
     ```

---

## Future Enhancements
- **User Authentication:** Add session-based or token-based authentication.
- **Category Breakdown:** Visualize expenses by category (e.g., Pie Chart via a UI).
- **Report Generation:** Generate monthly and yearly expense reports in PDF or CSV format.
- **API Integration:** Expose CRUD operations through REST APIs using Spring Boot.

---

## License


---

## Author
**Sajal Bagde**  

