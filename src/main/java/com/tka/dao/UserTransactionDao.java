package com.tka.dao;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.tka.user.TransactionSummary;
import com.tka.user.User;
import com.tka.utility.HibernateUtil;

import java.util.Date;
import java.util.List;

public class UserTransactionDao {
	

	public boolean addTransactionToUser(int userID, TransactionSummary transaction) {
	    Transaction tx = null;
	    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	        tx = session.beginTransaction();

	        // Fetch the user
	        User user = session.get(User.class, userID);

	        // Associate the transaction with the user
	        transaction.setUser(user);
	        user.getTransactions().add(transaction);

	        // Save the transaction
	        session.saveOrUpdate(user);

	        tx.commit();
	        return true;
	    } catch (Exception e) {
	        if (tx != null) {
	            tx.rollback();
	        }
	        e.printStackTrace();
	        return false;
	    }
	}


	    public List<TransactionSummary> allTransaction(int userID) {
	        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	            String hql = "FROM TransactionSummary WHERE user.userID = :userID";
	            Query<TransactionSummary> query = session.createQuery(hql, TransactionSummary.class);
	            query.setParameter("userID", userID);
	            return query.getResultList();
	        }
	    }

	    public boolean updateTransaction(TransactionSummary transaction, int userID) {
	        Transaction tx = null;
	        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	            tx = session.beginTransaction();

	            // Retrieve the transaction by ID
	            TransactionSummary existingTransaction = session.get(TransactionSummary.class, transaction.getTraID());

	            if (existingTransaction == null || existingTransaction.getUser().getUserID() != userID) {
	                // Transaction does not exist or does not belong to the user
	                return false;
	            }

	            // Update the fields of the existing transaction
	            existingTransaction.setAmount(transaction.getAmount());
	            existingTransaction.setType(transaction.getType());
	            existingTransaction.setCategory(transaction.getCategory());
	            existingTransaction.setDate(transaction.getDate());
	            existingTransaction.setDescription(transaction.getDescription());

	            session.update(existingTransaction);
	            tx.commit();
	            return true;
	        } catch (Exception e) {
	            if (tx != null) {
	                tx.rollback();
	            }
	            e.printStackTrace();
	            return false;
	        }
	    }


	    public boolean deleteTransaction(int transactionID, int userID) {
	        Transaction tx = null;
	        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	            tx = session.beginTransaction();

	            // Fetch the transaction by ID
	            TransactionSummary transaction = session.get(TransactionSummary.class, transactionID);

	            // Check if the transaction exists and belongs to the specified user
	            if (transaction == null || transaction.getUser().getUserID() != userID) {
	                return false;
	            }

	            // Remove the transaction
	            session.delete(transaction);

	            tx.commit();
	            return true;
	        } catch (Exception e) {
	            if (tx != null) {
	                tx.rollback();
	            }
	            e.printStackTrace();
	            return false;
	        }
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






