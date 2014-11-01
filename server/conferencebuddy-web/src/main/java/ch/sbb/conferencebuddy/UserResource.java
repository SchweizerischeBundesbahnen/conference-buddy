package ch.sbb.conferencebuddy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

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
    public long[] loadUserTracks(@HeaderParam("X-Access-Token") String userId) {
        List<Long> mytracks = userService.loadUserTracks(userId);
        long[] retVal = new long[mytracks.size()];
        for(int i=0 ; i<mytracks.size() ; i++){
            retVal[i] = mytracks.get(i);

        }
        return retVal;
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.TEXT_PLAIN)
    @Path("admin/{pid}")
    public void insertUserTalks(@PathParam("pid") final long pid, final String csv) {
        userService.insertUserTalks(Long.valueOf(pid), csv);
    }
}
