package ch.sbb.conferencebuddy;

import ch.sbb.conferencebuddy.model.Rating;
import ch.sbb.conferencebuddy.service.RatingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * 
 * <pre>
 *     {@code
 *     { 
 *          "id": "1234",             // wird bei PUT vom Backend generiert, identifiziert Rating
 *          "pid": "3456",            // Vortrag-Id
 *          "rate": "4"
 *     }
 *     }
 * </pre>
 * 
 * @author Gilles Zimmermann
 *
 * @since 0.0.1, 2014
 */
@Path("/rating")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RatingResource {
    /** logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(RatingResource.class);

    @Autowired
    private RatingService ratingService;

    /**
     * init {@code autowired} fields.
     */
    public RatingResource() {
        LOGGER.info("in RatingResource");
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    @GET
    @Path("/{pid}")
    public Rating load(@PathParam("pid") final long pid, @HeaderParam("X-Access-Token") String userId){
        return ratingService.loadUserRating(pid, userId);
    }

    @PUT
    @Path("/")
    public Rating update(@PathParam("pid") final long pid , final long rate, @HeaderParam("X-Access-Token") String userId) {
        return ratingService.saveOrUpdate(pid, rate, userId);
    }
}
