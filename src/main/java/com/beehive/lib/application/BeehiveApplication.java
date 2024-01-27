package com.beehive.lib.application;

import com.beehive.errors.application.BeehiveApplicationConfigurationError;
import com.beehive.lib.module.Module;
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
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;
import java.io.IOException;
import java.util.logging.Level;

class CORSFilter implements ContainerResponseFilter {
  public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) {

    MultivaluedMap<String, Object> headers = responseContext.getHeaders();

    headers.add("Access-Control-Allow-Origin", "*");
    headers.add("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
    headers.add("Access-Control-Allow-Headers", "Content-Type");
  }
}

public final class BeehiveApplication {
  private static final Logger logger = LogManager.getLogger();

  private final BeehiveServiceLoader serviceLoader;
  private final BeehiveConfig config;

  private EntityManagerFactory emf;
  private Validator validator;

  BeehiveApplication(Class<?> clazz, BeehiveConfig config) {
    if (config.getMain().isEmpty() || config.getUnit().isEmpty()) {
      throw new BeehiveApplicationConfigurationError();
    }

    this.config = config;

    initHibernate();
    initValidator();

    this.serviceLoader = new BeehiveServiceLoader(Module.create(clazz, null));
  }

  private void initValidator() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    this.validator = factory.getValidator();
  }

  private void initHibernate() {
    this.emf = Persistence.createEntityManagerFactory(config.getUnit());
    logger.info("Hibernate persistence unit [{}] loaded", config.getUnit());
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

    rc.register(new CORSFilter());
    rc.register(CORSFilter.class);

    java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);
    java.util.logging.Logger.getLogger("org.glassfish.grizzly").setLevel(Level.OFF);

    logger.info("Grizzly HTTP Server [{}] loaded", config.getMain());
    return GrizzlyHttpServerFactory.createHttpServer(config.getBaseURI(port), rc);
  }

  public void listen(int port) {
    serviceLoader.load();

    initHttpServer(port);
  }

  public Validator getValidator() {
    return validator;
  }

  public BeehiveServiceLoader getServiceLoader() {
    return serviceLoader;
  }

  public EntityManagerFactory getEmf() {
    return emf;
  }
}
