package com.playground;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Application {
  public static void main(String[] args) {
    SessionFactory factory = new Configuration()
        .configure("hibernate.cfg.xml")
        .addAnnotatedClass(User.class)
        .buildSessionFactory();

    try(factory) {
      Session session = factory.getCurrentSession();
      session.beginTransaction();

      User user = new User("username1", "email1@example.com", "name");

      session.persist(user);
      session.getTransaction().commit();
    }
  }
}
