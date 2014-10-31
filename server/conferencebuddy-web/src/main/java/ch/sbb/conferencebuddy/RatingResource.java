package ch.sbb.conferencebuddy;

import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import ch.sbb.conferencebuddy.model.Rating;
import ch.sbb.conferencebuddy.service.RatingService;

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
     * init {@code Autowired} fields.
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
    @Path("/{pid}")
    public Rating update(@PathParam("pid ") final long pid , final long rate, @HeaderParam("X-Access-Token") String userId) {
        return ratingService.saveOrUpdate(pid, rate, userId);
    }
}
