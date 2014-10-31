package ch.sbb.conferencebuddy;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 * @author Gilles Zimmermann
 *
 * @since 0.0.1, 2014
 */
@Path("/")
public class HelloWorldResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(HelloWorldResource.class);

    public HelloWorldResource() {
        LOGGER.info("in HelloWorldResource ");
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/helloworld")
    public String getHello() {
        return "Hello World!";
    }
}
