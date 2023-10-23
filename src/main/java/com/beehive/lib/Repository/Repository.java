package com.beehive.lib.Repository;

import com.beehive.errors.repository.RepositoryInitializationError;
import com.beehive.lib.application.BeehiveApplicationFactory;
import com.beelog.user.entity.UserEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public abstract class Repository<T> {
  protected static final Logger logger = LogManager.getLogger();

  public static <T extends Repository<?>> T create(Class<T> clazz) {
    try {
      return clazz.getConstructor().newInstance();
    } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
      throw new RepositoryInitializationError(clazz.getName());
    }
  }

  public final EntityManager em() {
    return BeehiveApplicationFactory.getInstance()
      .getEmf()
      .createEntityManager();
  }

  public abstract void save(T entity);

  public abstract void update(T entity);

  public abstract void delete(T entity);

  public abstract T findOne(EntityFilter filter);

  public abstract List<T> findAll(EntityFilter filter);

  @FunctionalInterface
  public interface EntityFilter {
    Predicate filter(CriteriaBuilder cb, Root<UserEntity> root);
  }
}
