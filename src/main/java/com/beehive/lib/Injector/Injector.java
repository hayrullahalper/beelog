package com.beehive.lib.Injector;

import com.beehive.lib.Factory.Factory;
import com.beehive.lib.Service.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.validation.ConstraintViolation;
import java.util.Set;

public abstract class Injector {

  protected static final Logger logger = LogManager.getLogger();

  public final <T extends Service> T service(Class<T> service) {
    return Factory.getInstance().getService(this.getClass(), service);
  }

  public <T> Set<ConstraintViolation<T>> validate(T object) {
    return Factory.getInstance().getValidator().validate(object);
  }
}
