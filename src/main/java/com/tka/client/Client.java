package com.tka.client;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.tka.controller.ControllerTracker;
import com.tka.dao.UserTransactionDao;
import com.tka.user.TransactionSummary;
import com.tka.user.User;

public class Client {
    public static void main(String[] args) {
        ControllerTracker userController = new ControllerTracker();
        UserTransactionDao transactionController = new UserTransactionDao();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. Register User");
            System.out.println("2. Login User");
            System.out.println("3. Add Transaction for User");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1: // Register User
                    System.out.println("Enter User ID: ");
                    int userID = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    System.out.println("Enter Username: ");
                    String username = scanner.nextLine();

                    System.out.println("Enter Password: ");
                    String password = scanner.nextLine();

                    System.out.println("Enter Email: ");
                    String email = scanner.nextLine();

                    User newUser = new User(userID, username, password, email);
                    List<User> registeredUsers = userController.regUser(newUser);

                    if (!registeredUsers.isEmpty()) {
                        System.out.println("User registered successfully!");
                        System.out.println(registeredUsers);
                    } else {
                        System.out.println("User registration failed. Username may already exist.");
                    }
                    break;

                case 2: // Login User
                    System.out.println("Enter Username: ");
                    String loginUsername = scanner.nextLine();

                    System.out.println("Enter Password: ");
                    String loginPassword = scanner.nextLine();

                    boolean isAuthenticated = userController.loginUser(loginUsername, loginPassword);

                    if (isAuthenticated) {
                        System.out.println("Login successful!");
                    } else {
                        System.out.println("Invalid username or password.");
                    }
                    break;

                case 3: // Add Transaction for User
                    System.out.println("Enter User ID: ");
                    int transactionUserID = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    System.out.println("Enter Transaction ID: ");
                    int transactionID = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    System.out.println("Enter Amount: ");
                    double amount = scanner.nextDouble();
                    scanner.nextLine(); // Consume newline

                    System.out.println("Enter Type (income/expense): ");
                    String type = scanner.nextLine();

                    System.out.println("Enter Category: ");
                    String category = scanner.nextLine();

                    System.out.println("Enter Description: ");
                    String description = scanner.nextLine();

                    TransactionSummary transaction = new TransactionSummary(
                            transactionID,
                            amount,
                            type,
                            category,
                            new Date(System.currentTimeMillis()), // Use current date
                            description,
                            null // User will be set in the DAO method
                    );

                    // Use the transaction DAO method to add the transaction
                    List<TransactionSummary> transactions = transactionController.addTransactionToUser(transactionUserID, transaction);

                    if (transactions != null && !transactions.isEmpty()) {
                        System.out.println("Transaction added successfully!");
                        transactions.forEach(System.out::println);
                    } else {
                        System.out.println("Failed to add transaction. Check user ID or other details.");
                    }
                    break;

                case 4: // Exit
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
