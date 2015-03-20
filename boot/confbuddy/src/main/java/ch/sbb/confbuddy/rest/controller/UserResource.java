package ch.sbb.confbuddy.rest.controller;

import java.time.LocalTime;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ch.sbb.confbuddy.model.User;
import ch.sbb.confbuddy.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 * User handling.
 *
 * @author Gilles Zimmermann
 * @since 0.0.1, 2014
 */
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

  private UserService userService;

  /**
   * init {@code Autowired} fields.
   */
  @Autowired
  public UserResource(final UserService userService) {
    this.userService = userService;
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
    return userService.register(user);
  }

  /**
   * Gibt den {@link User} zurück, der zu der übergebenen {@code userId} passt.
   *
   * @return {@code null} oder ein {@code User} Objekt
   */
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/user")
  public User validateUser(@HeaderParam("X-Access-Token") String userId) {
    return userService.validateAndLoadUser(userId);
  }


  /**
   * Liefert alle Vorträge des Users {userId}.
   *
   * @return Array von Vortrag-Ids (pid). Die Einträge sind chronologisch nach Startzeiten der Vorträge
   * sortiert, von der Ersten zur Letzten. Falls keine Vorträge existieren, wird ein leeres Array
   * zurückgeliefert.
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
  public void insertUserTalks(@PathParam("pid") final String pid, @PathParam("hour") final int hour, @PathParam("minute") final int minute, final String csv) {
    userService.insertUserTalks(pid, LocalTime.of(hour, minute), csv);
  }
}
