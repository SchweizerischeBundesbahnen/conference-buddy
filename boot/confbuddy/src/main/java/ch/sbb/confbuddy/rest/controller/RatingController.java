package ch.sbb.confbuddy.rest.controller;

import ch.sbb.confbuddy.model.Rating;
import ch.sbb.confbuddy.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
@RestController
@RequestMapping("service")
public class RatingController {

  @Autowired
  private RatingService ratingService;

  @RequestMapping("rating/{pid}")
  public
  @ResponseBody
  Rating load(@PathVariable("pid") final String pid, @RequestHeader(value = "X-Access-Token", required = false) String userId) {
    return ratingService.loadUserRating(pid, userId);
  }

  @RequestMapping(value = "rating/{pid}", method = RequestMethod.PUT)
  public
  @ResponseBody
  Rating update(@PathVariable("pid") final String pid, @RequestBody final long rate, @RequestHeader("X-Access-Token") String userId) {
    return ratingService.saveOrUpdate(pid, rate, userId);
  }

}
