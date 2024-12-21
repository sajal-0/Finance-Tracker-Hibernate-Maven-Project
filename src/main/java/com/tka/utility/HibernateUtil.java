package com.tka.utility;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.tka.user.TransactionSummary;
import com.tka.user.User;

public class HibernateUtil {
    private static SessionFactory sessionFactory;


    static {
        try {
            sessionFactory = new Configuration().configure("hibernateSQL.cfg.xml").buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }
}
