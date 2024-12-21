package com.tka.dao;
import org.hibernate.Session;


import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.tka.user.TransactionSummary;
import com.tka.user.User;
import com.tka.utility.HibernateUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class UserTransactionDao {
	List<TransactionSummary> list = null;

	
	public List<TransactionSummary> addTransactionToUser(int userID, TransactionSummary transaction) {
	    Transaction tx = null;
	    List<TransactionSummary> transactions = new ArrayList<>();

	    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	        tx = session.beginTransaction();

	        // Fetch the user
	        User user = session.get(User.class, userID);

	        if (user == null) {
	            throw new IllegalArgumentException("User with ID " + userID + " not found.");
	        }

	        // Associate the transaction with the user
	        transaction.setUser(user);

	        // Save the transaction
	        session.save(transaction);


	        // Fetch all transactions for the user
	        transactions = session.createQuery("FROM TransactionSummary WHERE user.id = :userID", TransactionSummary.class)
	                              .setParameter("userID", userID)
	                              .getResultList();
	        tx.commit();
	    } catch (Exception e) {
	        if (tx != null) {
	            tx.rollback();
	        }
	        e.printStackTrace();
	    }
	    transactions.add(transaction);
	    
	    return transactions;
	}


	public List<TransactionSummary> allTransaction(int userID) {
	    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	        return session.createQuery(
	                "FROM TransactionSummary WHERE user.userID = :userID", TransactionSummary.class)
	                .setParameter("userID", userID)
	                .getResultList();
	    }
	}


	public List<TransactionSummary> updateTransaction(TransactionSummary transaction, int userID) {
	    Transaction tx = null;
	    List<TransactionSummary> list = null; // Initialize list to avoid potential NullPointerException
	    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	        tx = session.beginTransaction();

	        // Retrieve the transaction by ID
	        TransactionSummary existingTransaction = session.get(TransactionSummary.class, transaction.getTraID());

	        // Check if the transaction exists and belongs to the user
	        if (existingTransaction == null || existingTransaction.getUser().getUserID() != userID) {
	            System.out.println("Transaction does not exist for this User.");
	            tx.rollback(); // Rollback and return early
	            return Collections.emptyList();
	        }

	        // Update the fields of the existing transaction
	        existingTransaction.setAmount(transaction.getAmount());
	        existingTransaction.setType(transaction.getType());
	        existingTransaction.setCategory(transaction.getCategory());
	        existingTransaction.setTransactionDate(transaction.getTransactionDate());
	        existingTransaction.setDescription(transaction.getDescription());

	        session.update(existingTransaction); // Persist changes
	        tx.commit();
	        System.out.println("Transaction updated successfully!");

	        // Fetch all updated transactions list
	        list = session.createQuery("from TransactionSummary", TransactionSummary.class).getResultList();
	    } catch (Exception e) {
	        if (tx != null) {
	            tx.rollback(); // Rollback the transaction in case of an error
	        }
	        e.printStackTrace();
	        System.out.println("Failed to update the transaction.");
	    }
	    return list == null ? Collections.emptyList() : list; // Return empty list if no data
	}



	    public List<TransactionSummary> deleteTransaction(int transactionID, int userID) {
	        Transaction tx = null;
	        list = new ArrayList<>();
	        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	            tx = session.beginTransaction();

	            // Fetch the transaction by ID
	            TransactionSummary transaction = session.get(TransactionSummary.class, transactionID);

	            // Check if the transaction exists and belongs to the specified user
	            if (transaction == null || transaction.getUser().getUserID() != userID) {
	               System.out.println("No transaction exist for this user ");
	            }

	            // Remove the transaction
	            session.delete(transaction);

	            tx.commit();
	            //fetch all the updated transactions list
	            list = session.createQuery("from TransactionSummary", TransactionSummary.class).getResultList();
	            
	        } catch (Exception e) {
	            if (tx != null) {
	                tx.rollback();
	            }
	            e.printStackTrace();
	         
	        }
	        return list;
	    }


	    public double getTotalIncome(int userID) {
	        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	            String hql = "SELECT SUM(amount) FROM TransactionSummary WHERE userID = :userID AND type = 'income'";
	            Query<Double> query = session.createQuery(hql, Double.class);
	            query.setParameter("userID", userID);
	            Double totalIncome = query.uniqueResult();
	            return totalIncome != null ? totalIncome : 0.0;
	        }
	    }

	    public double getTotalExpense(int userID) {
	        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	            String hql = "SELECT SUM(amount) FROM TransactionSummary WHERE userID = :userID AND type = 'expense'";
	            Query<Double> query = session.createQuery(hql, Double.class);
	            query.setParameter("userID", userID);
	            Double totalExpense = query.uniqueResult();
	            return totalExpense != null ? totalExpense : 0.0;
	        }
	    }

	    public List<TransactionSummary> getTransactionSummary(int userID, Date startDate, Date endDate) {
	        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	            String hql = "FROM TransactionSummary WHERE userID = :userID AND date BETWEEN :startDate AND :endDate";
	            Query<TransactionSummary> query = session.createQuery(hql, TransactionSummary.class);
	            query.setParameter("userID", userID);
	            query.setParameter("startDate", startDate);
	            query.setParameter("endDate", endDate);
	            return query.getResultList();
	        }
	    }

	    public List<TransactionSummary> getTransactionsByCategory(int userID, String category) {
	        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	            String hql = "FROM TransactionSummary WHERE userID = :userID AND category = :category";
	            Query<TransactionSummary> query = session.createQuery(hql, TransactionSummary.class);
	            query.setParameter("userID", userID);
	            query.setParameter("category", category);
	            return query.getResultList();
	        }
	    }
	    
	    
	    
	    public TransactionSummary getTransactionById(int userID, int transactionID) {
	        TransactionSummary transaction = null;
	        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	            // Fetch the transaction using its ID and ensure it belongs to the user
	            transaction = session.createQuery(
	                "from TransactionSummary ts where ts.traID = :transactionID and ts.user.userID = :userID",
	                TransactionSummary.class
	            )
	            .setParameter("transactionID", transactionID)
	            .setParameter("userID", userID)
	            .uniqueResult();

	            if (transaction == null) {
	                System.out.println("Transaction not found or does not belong to the user.");
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            System.out.println("Failed to fetch the transaction.");
	        }
	        return transaction;
	    }

	}






