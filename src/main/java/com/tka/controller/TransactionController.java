package com.tka.controller;

import com.tka.service.ServiceTransaction;
import com.tka.user.TransactionSummary;

import java.util.Date;
import java.util.List;

public class TransactionController {

    private final ServiceTransaction transactionService;

    // Constructor to initialize the service
    public TransactionController() {
        transactionService = new ServiceTransaction();
    }

    // Add a transaction
    public void addTransaction(int userID, TransactionSummary transaction) {
        List<TransactionSummary> updatedTransactions = transactionService.addTransaction(userID, transaction);
        System.out.println("Transaction added successfully!");
        printTransactions(updatedTransactions);
    }

    private void printTransactions(List<TransactionSummary> transactions) {
        if (transactions == null || transactions.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            transactions.forEach(System.out::println);
        }
    }


    // Get all transactions
    public List<TransactionSummary> getAllTransactions(int userID) {
        List<TransactionSummary> transactions = transactionService.getAllTransactions(userID);
        System.out.println("All transactions for User ID " + userID + ":");
        printTransactions(transactions);
		return transactions;
    }

    // Update a transaction
    public List<TransactionSummary> updateTransaction(int userID, TransactionSummary transaction) {
        List<TransactionSummary> updatedTransactions = transactionService.updateTransaction(transaction, userID);
        System.out.println("Transaction updated successfully!");
        printTransactions(updatedTransactions);
        return updatedTransactions;
    }

    // Delete a transaction
    public void deleteTransaction(int transactionID, int userID) {
        List<TransactionSummary> updatedTransactions = transactionService.deleteTransaction(transactionID, userID);
        System.out.println("Transaction deleted successfully!");
        printTransactions(updatedTransactions);
    }

    // Get total income
    public void getTotalIncome(int userID) {
        double totalIncome = transactionService.getTotalIncome(userID);
        System.out.println("Total income for User ID " + userID + ": " + totalIncome);
    }

    // Get total expense
    public void getTotalExpense(int userID) {
        double totalExpense = transactionService.getTotalExpense(userID);
        System.out.println("Total expense for User ID " + userID + ": " + totalExpense);
    }

    // Get transactions within a date range
    public void getTransactionSummary(int userID, Date startDate, Date endDate) {
        List<TransactionSummary> transactions = transactionService.getTransactionSummary(userID, startDate, endDate);
        System.out.println("Transaction summary for User ID " + userID + " between " + startDate + " and " + endDate + ":");
        printTransactions(transactions);
    }

    // Get transactions by category
    public void getTransactionsByCategory(int userID, String category) {
        List<TransactionSummary> transactions = transactionService.getTransactionsByCategory(userID, category);
        System.out.println("Transactions for User ID " + userID + " in category: " + category);
        printTransactions(transactions);
    }
    
    
    public TransactionSummary getTransactionById(int userID, int transactionID) {
        if (transactionID <= 0 || userID <= 0) {
            System.out.println("Invalid transaction or user ID.");
            return null;
        }

        return transactionService.getTransactionById(userID, transactionID);
    }


    
    

}

