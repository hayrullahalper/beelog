package com.beehive.lib.Injector;

import com.beehive.lib.Repository.Repository;
import com.beehive.lib.Service.Service;
import com.beehive.lib.application.BeehiveApplicationFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.validation.ConstraintViolation;
import java.util.Set;

public abstract class Injector {
  protected static final Logger logger = LogManager.getLogger();

  public final <T extends Service> T service(Class<T> service) {
    return BeehiveApplicationFactory.getInstance().getServices().stream()
      .filter(s -> s.getClass().equals(service))
      .map(service::cast)
      .findFirst()
      .orElse(null);
  }

  public <T extends Repository<?>> T repository(Class<T> clazz) {
    return Repository.create(clazz);
  }

  public final <T> Set<ConstraintViolation<T>> validate(T object) {
    return BeehiveApplicationFactory.getInstance().getValidator().validate(object);
  }
}
