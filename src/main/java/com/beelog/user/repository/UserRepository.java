package com.beelog.user.repository;

import com.beehive.lib.Repository.Repository;
import com.beelog.user.entity.UserEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.List;

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

  public UserEntity findOne(EntityFilter filter) {
    EntityManager em = em();

    try (em) {
      CriteriaBuilder cb = em.getCriteriaBuilder();

      CriteriaQuery<UserEntity> cq = cb.createQuery(UserEntity.class);
      Root<UserEntity> root = cq.from(UserEntity.class);

      Predicate predicate = filter.filter(cb, root);
      cq.where(predicate);

      cq.select(root);

      return em.createQuery(cq).getSingleResult();
    }
  }

  public List<UserEntity> findAll(EntityFilter filter) {
    EntityManager em = em();

    try (em) {
      CriteriaBuilder cb = em.getCriteriaBuilder();

      CriteriaQuery<UserEntity> cq = cb.createQuery(UserEntity.class);
      Root<UserEntity> root = cq.from(UserEntity.class);

      Predicate predicate = filter.filter(cb, root);
      cq.where(predicate);

      cq.select(root);

      return em.createQuery(cq).getResultList();
    }
  }
}
