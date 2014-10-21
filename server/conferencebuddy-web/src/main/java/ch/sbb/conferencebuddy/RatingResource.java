package ch.sbb.conferencebuddy;

import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * @author u215246 (Gilles Zimmermann)
 * @version $Id: $
 * @since 2014
 */
@Path("/rating")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RatingResource {
    /** logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(RatingResource.class);

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
    public Rating[] loadAll(@PathParam("pid") final long pid){
        final List<Rating> ratingList = ratingService.loadAll(pid);

        if (ratingList == null) {
            return new Rating[0];
        }
        return ratingList.toArray(new Rating[ratingList.size()]);
    }

    @PUT
    @Path("/")
    public Rating save(final Rating rating, @HeaderParam("X-Access-Token") String userId) {
        return ratingService.save(rating, userId);
    }

    @POST
    @Path("/{rid}/{rate}")
    public void update(@PathParam("rid") final long id, @PathParam("rate") final long rate,
            @HeaderParam("X-Access-Token") String userId) {
        ratingService.update(id, rate, userId);
    }
}
