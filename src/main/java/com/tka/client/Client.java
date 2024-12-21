package com.tka.client;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

import com.tka.controller.TransactionController;
import com.tka.controller.UserController;
import com.tka.user.TransactionSummary;
import com.tka.user.User;

public class Client {

    public static void main(String[] args) {
        UserController userController = new UserController();
        TransactionController transactionController = new TransactionController();
        Scanner scanner = new Scanner(System.in);

        User loggedInUser = null; // To track logged-in user

        while (true) {
            if (loggedInUser == null) {
                System.out.println("\n1. Register User");
                System.out.println("2. Login User");
                System.out.println("3. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1: // Register User
                        System.out.print("Enter Username: ");
                        String username = scanner.nextLine();

                        System.out.print("Enter Password: ");
                        String password = scanner.nextLine();

                        System.out.print("Enter Email: ");
                        String email = scanner.nextLine();

                        User newUser = new User( username, password, email); // User ID will be auto-generated
                        List<User> registeredUsers = userController.regUser(newUser);

                        if (!registeredUsers.isEmpty()) {
                            System.out.println("User registered successfully!");
                        } else {
                            System.out.println("User registration failed. Username may already exist.");
                        }
                        break;

                    case 2: // Login User
                        System.out.print("Enter Username: ");
                        String loginUsername = scanner.nextLine();

                        System.out.print("Enter Password: ");
                        String loginPassword = scanner.nextLine();

                        boolean isAuthenticated = userController.loginUser(loginUsername, loginPassword);

                        if (isAuthenticated) {
                            loggedInUser = userController.getUserByUsername(loginUsername); // Fetch full user details
                            System.out.println("Login successful! Welcome, " + loginUsername + "!");
                        } else {
                            System.out.println("Invalid username or password.");
                        }
                        break;

                    case 3: // Exit
                        System.out.println("Exiting...");
                        scanner.close();
                        System.exit(0);
                        break;

                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } else { // After login
                System.out.println("\n1. View Transactions");
                System.out.println("2. Add Transaction");
                System.out.println("3. Update Transaction");
                System.out.println("4. Delete Transaction");
                System.out.println("5. Logout");
                System.out.println("6. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1: // View Transactions
                        List<TransactionSummary> transactions = transactionController.getAllTransactions(loggedInUser.getUserID());
                        if (transactions != null && !transactions.isEmpty()) {
                            System.out.println("Your Transactions:");
                            for (TransactionSummary transaction : transactions) {
                                System.out.println(transaction);
                            }
                        } else {
                            System.out.println("No transactions found.");
                        }
                        break;

                    case 2: // Add Transaction
                        System.out.print("Enter Amount: ");
                        double amount = scanner.nextDouble();
                        scanner.nextLine(); // Consume newline

                        System.out.print("Enter Type (income/expense): ");
                        String type = scanner.nextLine();

                        System.out.print("Enter Category: ");
                        String category = scanner.nextLine();

                        System.out.print("Enter Description: ");
                        String description = scanner.nextLine();

                        TransactionSummary transaction = new TransactionSummary(
                                 // Transaction ID will be auto-generated
                                amount,
                                type,
                                category,
                                new Date(System.currentTimeMillis()), // Use current date
                                description,
                                loggedInUser // Pass the full User object
                        );

                        transactionController.addTransaction(loggedInUser.getUserID(), transaction);
                        System.out.println("Transaction added successfully!");
                        break;

                    case 3: // Update Transaction
                        System.out.print("Enter Transaction ID to Update: ");
                        int transactionIDToUpdate = scanner.nextInt();
                        scanner.nextLine(); // Consume newline

                        System.out.print("Enter New Amount: ");
                        double newAmount = scanner.nextDouble();
                        scanner.nextLine(); // Consume newline

                        System.out.print("Enter New Description: ");
                        String newDescription = scanner.nextLine();

                        // Fetch the existing transaction to retain unchanged values
                        TransactionSummary existingTransaction = transactionController.getTransactionById(loggedInUser.getUserID(), transactionIDToUpdate);

                        if (existingTransaction == null) {
                            System.out.println("Transaction not found or does not belong to the current user.");
                        } else {
                            // Create an updated transaction object by retaining unchanged fields
                            TransactionSummary updatedTransaction = new TransactionSummary(
                                transactionIDToUpdate, // Ensure we have the correct ID
                                newAmount,
                                existingTransaction.getType(), // Retain the existing type
                                existingTransaction.getCategory(), // Retain the existing category
                                new Date(System.currentTimeMillis()), // Use the current date
                                newDescription,
                                loggedInUser // Ensure it is associated with the logged-in user
                            );

                            // Call update method
                            List<TransactionSummary>  updateSuccess = transactionController.updateTransaction(loggedInUser.getUserID(), updatedTransaction);

                            if (updateSuccess != null) {
                                System.out.println("Transaction updated successfully!");
                            } else {
                                System.out.println("Failed to update the transaction. Please try again.");
                            }
                        }
                        break;


                    case 4: // Delete Transaction
                        System.out.print("Enter Transaction ID to Delete: ");
                        int transactionIDToDelete = scanner.nextInt();
                        scanner.nextLine(); // Consume newline

                        transactionController.deleteTransaction(transactionIDToDelete, loggedInUser.getUserID());
                        System.out.println("Transaction deleted successfully!");
                        break;

                    case 5: // Logout
                        System.out.println("Logged out successfully.");
                        loggedInUser = null;
                        break;

                    case 6: // Exit
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
}
