package com.beehive.lib.Application;

import com.beehive.lib.Injector.Injector;
import com.beehive.lib.Module.Module;
import com.beehive.lib.Service.Service;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public final class Application {
  private static final Logger logger = LogManager.getLogger();
  private final com.beehive.lib.Module.Module mainModule;
  private final List<Service> services = new ArrayList<>();
  private final ApplicationConfig config;
  private final EntityManagerFactory emf;
  private final Validator validator;

  public Application(Class<?> clazz, ApplicationConfig config) {
    this.config = config;
    this.mainModule = Module.create(clazz, null);

    this.emf = Persistence.createEntityManagerFactory(config.getPersistenceUnit());

    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    this.validator = factory.getValidator();
  }

  private HttpServer createServer(int port) {
    final ResourceConfig rc = new ResourceConfig().packages(config.getMainPackage());

    rc.property("jersey.config.server.wadl.disableWadl", true);

    java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);
    java.util.logging.Logger.getLogger("org.glassfish.grizzly").setLevel(Level.OFF);

    return GrizzlyHttpServerFactory.createHttpServer(config.getBaseURI(port), rc);
  }

  public void listen(int port) {
    loadServices();

    final HttpServer server = createServer(port);

    logger.info("BEEHIVE app started with endpoints available at {}",
                config.getBaseURI(port));
    logger.info("Press CTRL^C to exit...");

    try {
      System.in.read();
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }

    server.shutdown();
  }

  public EntityManagerFactory getEntityManagerFactory() {
    return emf;
  }

  private List<Class<?>> sortServiceClasses(List<Class<?>> serviceClasses) {
    return serviceClasses.stream()
      .sorted((a, b) -> {
        int aWeight = Service.getWeight(a);
        int bWeight = Service.getWeight(b);

        return aWeight - bWeight;
      })
      .toList();
  }

  private void loadServices() {
    List<Class<?>> serviceClasses = sortServiceClasses(mainModule.getAllServiceClasses());

    for (Class<?> serviceClass : serviceClasses) {
      try {
        Object service = serviceClass
          .getDeclaredConstructor(new Class<?>[] {})
          .newInstance();

        services.add(Service.class.isAssignableFrom(serviceClass) ? (Service) service : null);
        logger.info("Service {} loaded", serviceClass.getSimpleName());
      } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
               NoSuchMethodException e) {
        throw new ApplicatonServiceLoadError(serviceClass.getName());
      }
    }
  }

  private <T extends Service> boolean canInjectorAccessToService(Class<?> requester, Class<T> service) {
    if (!Injector.class.isAssignableFrom(requester)) {
      throw new ServiceRequesterInheritError();
    }

    return true;
  }

  public <T extends Service> T getService(Class<?> requester, Class<T> service) {
    if (!canInjectorAccessToService(requester, service)) {
      throw new ServiceAccessError(service.getName(), requester.getName());
    }

    for (Service s : services) {
      if (s.getClass().equals(service)) {
        return service.cast(s);
      }
    }

    throw new ServiceNotFoundError(service.getName());
  }

  public Validator getValidator() {
    return validator;
  }
}
