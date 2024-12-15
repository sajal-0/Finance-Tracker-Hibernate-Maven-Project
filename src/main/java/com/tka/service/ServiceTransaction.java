package com.tka.service;

import com.tka.dao.UserTransactionDao;
import com.tka.user.TransactionSummary;

import java.util.Date;
import java.util.List;

public class ServiceTransaction {

    private final UserTransactionDao transactionDao;

    // Constructor to initialize the DAO
    public ServiceTransaction() {
        transactionDao = new UserTransactionDao();
    }

    // Add a transaction for a user
    public List<TransactionSummary> addTransaction(int userID, TransactionSummary transaction) {
        return transactionDao.addTransactionToUser(userID, transaction);
    }

    // Get all transactions for a user
    public List<TransactionSummary> getAllTransactions(int userID) {
        return transactionDao.allTransaction(userID);
    }

    // Update a transaction
    public List<TransactionSummary> updateTransaction(TransactionSummary transaction, int userID) {
        return transactionDao.updateTransaction(transaction, userID);
    }

    // Delete a transaction
    public List<TransactionSummary> deleteTransaction(int transactionID, int userID) {
        return transactionDao.deleteTransaction(transactionID, userID);
    }

    // Get total income for a user
    public double getTotalIncome(int userID) {
        return transactionDao.getTotalIncome(userID);
    }

    // Get total expense for a user
    public double getTotalExpense(int userID) {
        return transactionDao.getTotalExpense(userID);
    }

    // Get transactions within a specific date range
    public List<TransactionSummary> getTransactionSummary(int userID, Date startDate, Date endDate) {
        return transactionDao.getTransactionSummary(userID, startDate, endDate);
    }

    // Get transactions by category
    public List<TransactionSummary> getTransactionsByCategory(int userID, String category) {
        return transactionDao.getTransactionsByCategory(userID, category);
    }
}

