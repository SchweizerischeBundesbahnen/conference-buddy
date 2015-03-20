package ch.sbb.confbuddy.rest.controller;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import ch.sbb.confbuddy.rest.controller.provider.UserUnauthorizedExceptionMapper;
import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author Gilles Zimmermann
 * @since 0.0.1, 2014
 */
@ApplicationPath("/service")
@Component
public class JerseyConfig extends ResourceConfig {
  private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

  public JerseyConfig() {
    LOGGER.debug("in JerseyConfig");

    // rest services
    register(HelloWorldResource.class);
    register(CommentResource.class);
    register(RatingResource.class);
    register(UserResource.class);
    register(LoggingFilter.class);

    // singletons
    registerInstances(
      new JacksonFeature(),
      new UserUnauthorizedExceptionMapper());
  }
}
