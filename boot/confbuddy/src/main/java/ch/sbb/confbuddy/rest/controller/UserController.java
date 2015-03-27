package ch.sbb.confbuddy.rest.controller;

import java.time.LocalTime;
import java.util.List;

import ch.sbb.confbuddy.model.User;
import ch.sbb.confbuddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * User handling.
 *
 * @author Gilles Zimmermann
 * @since 0.0.1, 2014
 */
@RestController
@RequestMapping("service")
public class UserController {

  @Autowired
  private UserService userService;

  /**
   * Registriert einen Benutzer und generiert einen eindeutigen Token, am besten eine UUID.
   *
   * @return unique ID
   */
  @RequestMapping(value = "user", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
  public
  @ResponseBody
  String register(@RequestBody final User user) {
    return userService.register(user);
  }

  /**
   * Gibt den {@link User} zurück, der zu der übergebenen {@code userId} passt.
   *
   * @return {@code null} oder ein {@code User} Objekt
   */
  @RequestMapping("user")
  public
  @ResponseBody
  User validateUser(@RequestHeader("X-Access-Token") String userId) {
    return userService.validateAndLoadUser(userId);
  }


  /**
   * Liefert alle Vorträge des Users {userId}.
   *
   * @return Array von Vortrag-Ids (pid). Die Einträge sind chronologisch nach Startzeiten der Vorträge
   * sortiert, von der Ersten zur Letzten. Falls keine Vorträge existieren, wird ein leeres Array
   * zurückgeliefert.
   */
  @RequestMapping("mytrack")
  public
  @ResponseBody
  String[] loadUserTracks(@RequestHeader("X-Access-Token") String userId) {
    final List<String> mytracks = userService.loadUserTracks(userId);
    String[] retVal = new String[mytracks.size()];
    retVal = mytracks.toArray(retVal);
    return retVal;
  }

  @RequestMapping(value = "admin/{pid}/{hour}/{minute}", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
  public void insertUserTalks(@RequestParam("pid") final String pid, @RequestParam("hour") final int hour, @RequestParam("minute") final int minute, @RequestBody final String csv) {
    userService.insertUserTalks(pid, LocalTime.of(hour, minute), csv);
  }
}
