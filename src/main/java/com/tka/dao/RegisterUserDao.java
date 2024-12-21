package com.tka.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;


import com.tka.user.User;
import com.tka.utility.HibernateUtil;

public class RegisterUserDao {
	List<User> list = null;

   
    Scanner sc = new Scanner(System.in);
    

    public List<User> regUser(User user) {
    	 Transaction tx = null;
    	 list = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Begin transaction
        	  tx = session.beginTransaction();

            // Save the user object
            session.save(user);
            list.add(user);
            // Commit the transaction
            tx.commit();
            
            list = session.createQuery("FROM User", User.class).list();
            
        } catch (Exception e) {
            if (tx != null) {
            	tx.rollback();
            }
            e.printStackTrace();
           
        }
        
        return list;
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

    public List<User> updateUser(User user) {
        Transaction transaction = null;
        list = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Begin transaction
            transaction = session.beginTransaction();

            // Update the user object
            session.update(user);
            list.add(user);

            // Commit the transaction
            transaction.commit();
         
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
           
        }
        
        return list;
    }
    public List<User> deleteUser(int userID) {
        Transaction transaction = null;
        list = new ArrayList<>();
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
            list = session.createQuery("from User", User.class).getResultList();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
           
        }
        return list;
    }
    
    public boolean isUserExists(String username) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM User WHERE username = :username";
            User existingUser = session.createQuery(hql, User.class)
                                       .setParameter("username", username)
                                       .uniqueResult();
            return existingUser != null; // Return true if user exists
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public User findByUsername(String username) {
        User user = null;
        
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // HQL to fetch user by username
            String hql = "FROM User WHERE username = :username";
            Query<User> query = session.createQuery(hql, User.class);
            query.setParameter("username", username);

            // Fetch the result (unique result expected since username is typically unique)
            user = query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }

    
   



    
    

}
