package com.tka.dao;

import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.tka.user.User;
import com.tka.utility.HibernateUtil;

public class RegisterUserDao {
	List<User> list = null;

   
    Scanner sc = new Scanner(System.in);
    

    public boolean regUser(User user) {
    	 Transaction tx = null;
    	 
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Begin transaction
        	  tx = session.beginTransaction();

            // Save the user object
            session.save(user);

            // Commit the transaction
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
    
    public boolean loginUser(String username, String password) {
        boolean isAuthenticated = false;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM User WHERE username = :username";
            User user = session.createQuery(hql, User.class)
                               .setParameter("username", username)
                               .uniqueResult();

            if (user != null && user.getPassword().equals(password)) {
                isAuthenticated = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isAuthenticated;
    }

    public boolean updateUser(User user) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Begin transaction
            transaction = session.beginTransaction();

            // Update the user object
            session.update(user);

            // Commit the transaction
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }
    public boolean deleteUser(int userID) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Begin transaction
            transaction = session.beginTransaction();

            // Retrieve the user object
            User user = session.get(User.class, userID);
            if (user != null) {
                // Delete the user
                session.delete(user);
            }

            // Commit the transaction
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }


    
    

}
