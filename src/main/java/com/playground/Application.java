package com.playground;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Application {
  public static void main(String[] args) {
    SessionFactory factory = new Configuration()
        .configure("hibernate.cfg.xml")
        .addAnnotatedClass(UserEntity.class)
        .buildSessionFactory();

    try(factory) {
      Session session = factory.getCurrentSession();

      UserRepository userRepository = new UserRepository(session);

    }
  }
}
