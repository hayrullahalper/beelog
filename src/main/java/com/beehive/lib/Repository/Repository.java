package com.beehive.lib.Repository;

import com.beehive.lib.Factory.Factory;
import jakarta.persistence.EntityManager;

public abstract class Repository<T> {

    public final EntityManager em() {
        return Factory.getInstance()
            .getEntityManagerFactory()
            .createEntityManager();
    }

    public abstract void save(T entity);

    public abstract void update(T entity);

    public abstract void delete(T entity);
}
