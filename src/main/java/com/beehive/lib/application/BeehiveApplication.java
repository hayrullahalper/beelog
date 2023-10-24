package com.beehive.lib.application;

import com.beehive.errors.application.BeehiveApplicationConfigurationError;
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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public final class BeehiveApplication {
  private static final Logger logger = LogManager.getLogger();

  private final List<Service> services = new ArrayList<>();
  private final BeehiveConfig config;
  private final Module module;
  private EntityManagerFactory emf;
  private Validator validator;

  public BeehiveApplication(Class<?> clazz, BeehiveConfig config) {
    if (config.getMain().isEmpty() || config.getUnit().isEmpty()) {
      throw new BeehiveApplicationConfigurationError();
    }

    this.config = config;
    this.module = Module.create(clazz, null);

    initHibernate();
    initValidator();
  }

  private void initValidator() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    this.validator = factory.getValidator();
  }

  private void initHibernate() {
    this.emf = Persistence.createEntityManagerFactory(config.getUnit());
    logger.info("Hibernate persistence unit [{}] loaded", config.getUnit());
  }

  private void initServices() {
    Service.sort(Module.getAllServices(module)).forEach(service -> {
      Service.check(service);

      Service s = Service.create(service);

      this.services.add(s);
      logger.info("Service [{}] loaded", service.getName());
    });
  }

  private void initHttpServer(int port) {
    final HttpServer server = createHttpServer(port);

    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
      logger.info("BEEHIVE app shutting down");
      server.shutdownNow();
      emf.close();
    }));

    logger.info("BEEHIVE app started with endpoints available at {}",
                config.getBaseURI(port));

    try {
      server.start();
      Thread.currentThread().join();
    } catch (IOException | InterruptedException e) {
      logger.error(e.getMessage());
    }
  }

  private HttpServer createHttpServer(int port) {
    final ResourceConfig rc = new ResourceConfig().packages(config.getMain());

    rc.property("jersey.config.server.wadl.disableWadl", true);

    java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);
    java.util.logging.Logger.getLogger("org.glassfish.grizzly").setLevel(Level.OFF);

    logger.info("Grizzly HTTP Server [{}] loaded", config.getMain());
    return GrizzlyHttpServerFactory.createHttpServer(config.getBaseURI(port), rc);
  }

  public void listen(int port) {
    initServices();
    initHttpServer(port);
  }

  public Module getModule() {
    return module;
  }

  public Validator getValidator() {
    return validator;
  }

  public List<Service> getServices() {
    return services;
  }

  public EntityManagerFactory getEmf() {
    return emf;
  }
}
