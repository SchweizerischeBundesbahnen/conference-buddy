package ch.sbb.conferencebuddy;

import ch.sbb.conferencebuddy.model.User;
import ch.sbb.conferencebuddy.service.UserService;
import ch.sbb.esta.util.condition.Reject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * User handling.
 * 
 * @author u215246 (Gilles Zimmermann)
 * @version $Id: $
 * @since 2014
 */
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    /** logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(UserResource.class);

    @Autowired
    private UserService userService;

    /**
     * init {@code Autowired} fields.
     */
    public UserResource() {
        LOGGER.info("in UserResource");
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    /**
     * Registriert einen Benutzer und generiert einen eindeutigen Token, am besten eine UUID.
     * 
     * @return unique ID
     */
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/user")
    public String register(final User user) {
        Reject.ifNull(user);

        return userService.register(user);
    }


    /**
     * Liefert alle Vorträge des Users {userId}.
     * 
     * @return Array von Vortrag-Ids (pid). Die Einträge sind chronologisch nach Startzeiten der Vorträge
     *         sortiert, von der Ersten zur Letzten. Falls keine Vorträge existieren, wird ein leeres Array
     *         zurückgeliefert.
     */
    @GET
    @Path("/mytrack")
    public List<Long> loadUserTracks(@HeaderParam("X-Access-Token") String userId) {
        if (!isValidUserId(userId)) {
            throw new WebApplicationException(Response.status(Response.Status.UNAUTHORIZED)
                    .entity("YOU SHALL NOT PASS!").type(MediaType.APPLICATION_JSON).build());
        }
        return userService.loadUserTracks(userId);
    }

    private boolean isValidUserId(String userId) {
        return userId != null /* && validateToken(userId) */;
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.TEXT_PLAIN)
    @Path("admin/{pid}")
    public void insertUserTalks(@PathParam("pid") final long pid, final String csv) {
        userService.insertUserTalks(Long.valueOf(pid), csv);
    }
}
