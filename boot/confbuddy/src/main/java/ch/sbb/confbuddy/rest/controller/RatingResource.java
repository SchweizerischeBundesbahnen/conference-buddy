package ch.sbb.confbuddy.rest.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ch.sbb.confbuddy.model.Rating;
import ch.sbb.confbuddy.service.RatingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
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
 * @since 0.0.1, 2014
 */
@Component
@Path("/rating")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RatingResource {
  private RatingService ratingService;

  /**
   * init {@code autowired} fields.
   */
  @Autowired
  public RatingResource(final RatingService ratingService) {
    this.ratingService = ratingService;
  }

  @GET
  @Path("/{pid}")
  public Rating load(@PathParam("pid") final String pid, @HeaderParam("X-Access-Token") String userId) {
    return ratingService.loadUserRating(pid, userId);
  }

  @PUT
  @Path("/{pid}")
  public Rating update(@PathParam("pid") final String pid, final long rate, @HeaderParam("X-Access-Token") String userId) {
    return ratingService.saveOrUpdate(pid, rate, userId);
  }
}
