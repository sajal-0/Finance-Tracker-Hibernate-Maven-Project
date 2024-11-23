package com.tka.utility;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.tka.user.TransactionSummary;
import com.tka.user.User;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    static {
        try {
            sessionFactory = new Configuration().configure("hibernateSQL.cfg.xml").addAnnotatedClass(User.class).addAnnotatedClass(TransactionSummary.class).buildSessionFactory();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExceptionInInitializerError("Failed to create SessionFactory");
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
