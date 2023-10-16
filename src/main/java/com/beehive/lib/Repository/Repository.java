package com.beehive.lib.Repository;

import com.beehive.lib.Factory.Factory;
import com.playground.user.entity.UserEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.List;

public abstract class Repository<T> {

    @FunctionalInterface
    public interface EntityFilter {
        Predicate filter(CriteriaBuilder cb, Root<UserEntity> root);
    }

    public final EntityManager em() {
        return Factory.getInstance()
            .getEntityManagerFactory()
            .createEntityManager();
    }

    public abstract void save(T entity);

    public abstract void update(T entity);

    public abstract void delete(T entity);

    public abstract T findOne(EntityFilter filter);

    public abstract List<T> findAll(EntityFilter filter);
}
