package ch.sbb.conferencebuddy;

import java.util.Collections;
import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import org.joda.time.LocalTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import ch.sbb.conferencebuddy.model.User;
import ch.sbb.conferencebuddy.service.UserService;
import ch.sbb.esta.util.condition.Reject;

/**
 * User handling.
 *
 * @author Gilles Zimmermann
 *
 * @since 0.0.1, 2014
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
    public String[] loadUserTracks(@HeaderParam("X-Access-Token") String userId) {
        final List<String> mytracks = userService.loadUserTracks(userId);
        String[] retVal = new String[mytracks.size()];
        retVal = mytracks.toArray(retVal);
        return retVal;
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.TEXT_PLAIN)
    @Path("admin/{pid}/{hour}/{minute}")
    public void insertUserTalks(@PathParam("pid") final String pid, @PathParam("hour") final int hour, @PathParam("minute")final int minute, final String csv) {
        userService.insertUserTalks(pid, new LocalTime(hour, minute), csv);
    }
}
