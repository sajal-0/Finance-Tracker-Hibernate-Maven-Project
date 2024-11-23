package com.tka.dao;
import org.hibernate.Session;

import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.tka.user.TransactionSummary;
import com.tka.user.User;
import com.tka.utility.HibernateUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserTransactionDao {
	List<TransactionSummary> list = null;

	
	public List<TransactionSummary> addTransactionToUser(int userID, TransactionSummary transaction) {
	    Transaction tx = null;
	    list = new ArrayList<>();
	    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	        tx = session.beginTransaction();

	        // Fetch the user
	        User user = session.get(User.class, userID);
	        
	        if (user == null) {
	            throw new IllegalArgumentException("User with ID " + userID + " not found.");
	        }

	        // Associate the transaction with the user
	        transaction.setUser(user);
	        
	        session.save(transaction);
	        
	     // Add transaction to User's list
	        user.getTransactions().add(transaction);

	        // Save the transaction
	        session.saveOrUpdate(user);
	        
	        // Fetch the latest transactions (eagerly)
	        list = session.createQuery(
	                "FROM TransactionSummary t WHERE t.user.id = :userId", TransactionSummary.class)
	                .setParameter("userId", userID)
	                .getResultList();
//	        list.add(transaction);

	        tx.commit();
	        
//	        list = user.getTransactions();
	       
	    } catch (Exception e) {
	        if (tx != null) {
//	            tx.rollback();
	        }
	        e.printStackTrace();
	        
	    }
	    return list;
	}


	    public List<TransactionSummary> allTransaction(int userID) {
	        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	            String hql = "FROM TransactionSummary WHERE user.userID = :userID";
	            Query<TransactionSummary> query = session.createQuery(hql, TransactionSummary.class);
	            query.setParameter("userID", userID);
	            return query.getResultList();
	        }
	    }

	    public List<TransactionSummary> updateTransaction(TransactionSummary transaction, int userID) {
	        Transaction tx = null;
	        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	            tx = session.beginTransaction();

	            // Retrieve the transaction by ID
	            TransactionSummary existingTransaction = session.get(TransactionSummary.class, transaction.getTraID());

	            if (existingTransaction == null || existingTransaction.getUser().getUserID() != userID) {
	                // Transaction does not exist or does not belong to the user
	                System.out.println("Transaction does not exist for tthis User ");
	            }

	            // Update the fields of the existing transaction
	            existingTransaction.setAmount(transaction.getAmount());
	            existingTransaction.setType(transaction.getType());
	            existingTransaction.setCategory(transaction.getCategory());
	            existingTransaction.setDate(transaction.getDate());
	            existingTransaction.setDescription(transaction.getDescription());

	            session.update(existingTransaction);
	            tx.commit();
	            System.out.println("Transaction Updated SuccessFully!!");
	          
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
	}






