package ch.sbb.conferencebuddy;

import ch.sbb.esta.core.webservice.rest.handler.EstaCheckedExceptionMapper;
import ch.sbb.esta.core.webservice.rest.handler.EstaRuntimeExceptionMapper;
import ch.sbb.esta.core.webservice.rest.handler.WebApplicationExceptionMapper;
import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Gilles Zimmermann
 *
 * @since 0.0.1, 2014
 */
@ApplicationPath("/")
public class RestApplication extends Application {
    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

    public RestApplication() {
        LOGGER.debug("in MyApplication");
    }


    /**
     * {@inheritDoc}.
     *
     * <ul>
     * <li>{@link HelloWorldResource}</li>
     * <li>{@link org.glassfish.jersey.filter.LoggingFilter}</li>
     * </ul>
     */
    @Override
    public Set<Class<?>> getClasses() {
        LOGGER.debug("in MyApplication#getClasses");
        final Set<Class<?>> classes = new HashSet<>();
        classes.add(HelloWorldResource.class);
        classes.add(CommentResource.class);
        classes.add(LoggingFilter.class);
        return classes;
    }

    /**
     * {@inheritDoc}.
     *
     * Gibt alle Singletons zurück, welche als Beispiel in dieser Applikation erstellt wurden. Es sind dies u.A. (List muss nicht vollständig sein):
     * <ul>
     * <li>{@link org.glassfish.jersey.jackson.JacksonFeature}</li>
     * <li>{@link ch.sbb.esta.core.webservice.rest.handler.EstaCheckedExceptionMapper}</li>
     * <li>{@link ch.sbb.esta.core.webservice.rest.handler.EstaRuntimeExceptionMapper}</li>
     * <li>{@link ch.sbb.esta.core.webservice.rest.handler.WebApplicationExceptionMapper}</li>
     * </ul>
     *
     */
    @Override
    public Set<Object> getSingletons() {
        Set<Object> singletons = new HashSet<>();
        singletons.add(new JacksonFeature());
        singletons.add(new EstaCheckedExceptionMapper());
        singletons.add(new EstaRuntimeExceptionMapper());
        singletons.add(new WebApplicationExceptionMapper());
        return singletons;

    }
}
