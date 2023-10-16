package com.playground.user.repository;

import com.beehive.lib.Repository.Repository;
import com.playground.user.entity.UserEntity;
import jakarta.persistence.EntityManager;

public class UserRepository extends Repository<UserEntity> {
    public void save(UserEntity user) {
        EntityManager em = em();

        try (em) {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        }
    }

    public void delete(UserEntity user) {
        EntityManager em = em();

        try (em) {
            em.getTransaction().begin();
            em.remove(user);
            em.getTransaction().commit();
        }
    }

    public void update(UserEntity user) {
        EntityManager em = em();

        try (em) {
            em.getTransaction().begin();
            em.merge(user);
            em.getTransaction().commit();
        }

    }

    public UserEntity findByUsername(String username) {
        EntityManager em = em();

        try (em) {
            return em.createQuery("SELECT u FROM UserEntity u WHERE u.username = :username", UserEntity.class)
                .setParameter("username", username)
                .getSingleResult();
        }
    }
}
