package com.tka.controller;

import java.sql.Date;
import java.util.List;

import com.tka.dao.RegisterUserDao;
import com.tka.dao.UserTransactionDao;
import com.tka.user.TransactionSummary;
import com.tka.user.User;

public class ControllerTracker {

	
	
	
	 public static void main(String[] args) {
		 RegisterUserDao userDao = new RegisterUserDao();
		    UserTransactionDao transactionDao = new UserTransactionDao();

		    // Register a new user
		    User user = new User(1102, "User1", "Pass@123", "user@gmail.com");
		    boolean regUser = userDao.regUser(user);
		    System.out.println("User registered: " + regUser);

		    // Add a transaction for the user
		    TransactionSummary transaction1 = new TransactionSummary(2202,500.0, "income", "salary", new Date(0), "Monthly salary", user);
		    TransactionSummary transaction2 = new TransactionSummary(2202,500.0, "ssss", "salary", new Date(1), "Monthly salary", user);

		    boolean addTransaction = transactionDao.addTransactionToUser(1102, transaction1);
		    boolean newtra = transactionDao.addTransactionToUser(1102, transaction2);
		    System.out.println("Transaction added: " + addTransaction);
		    System.out.println(newtra);
		    

		    // Fetch all transactions for the user
//		    List<TransactionSummary> transactions = transactionDao.allTransaction(1102);
//		    transactions.forEach(System.out::println);
	}
	
}
