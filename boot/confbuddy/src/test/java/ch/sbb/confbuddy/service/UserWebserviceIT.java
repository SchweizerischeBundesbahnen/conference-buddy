package ch.sbb.confbuddy.service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import ch.sbb.confbuddy.model.Comment;
import ch.sbb.confbuddy.model.Rating;
import ch.sbb.confbuddy.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * Testing the whole process, starting:
 * 1. import csv
 * 2. register test user
 * 3. load myTrack
 * 4. comment for a track
 * 5. change comment
 * 6. load all comments for a track
 * 7. delete comment
 * 8. vote for a track
 * 9. change vote
 * 10. load user rate with average vote for track
 * <p>
 * IMPORTANT: It's only a feel lucky test! boarder cases need to be tested separately.
 *
 * @author Gilles Zimmermann
 * @since 0.0.1, 2014
 */
public class UserWebserviceIT {

  private static final String TEST_CSV_1_AND_2 = "login;firstname;lastname;email;oe;comment\n" +
    "U123456;Daniel;Muster;DANIEL.MUSTER@SBB-bla.CH;IT-SWE-CD2-T29;\n" +
    "U123457;Stefan;Meier;STEFAN.MEIER@SBB-bla.CH;IT-SWE-CD5-T55;";

  private static final String TEST_CSV_99 = "login;firstname;lastname;email;oe;comment\n" +
    "U654321;Daniel;Muster;DANIEL.MUSTER@SBB-bla.CH;IT-SWE-CD2-T29;\n" +
    "U754321;Stefan;Meier;STEFAN.MEIER@SBB-bla.CH;IT-SWE-CD5-T55;";

  private static final String TEST_PID_1 = "1";
  private static final String TEST_PID_2 = "2";
  private static final String TEST_PID_99 = "99";

  private static final String U_NUMMER = "U123456";


  private static final String NEW_COMMENT = "newComment";

  private static final Logger LOGGER = LoggerFactory.getLogger(UserWebserviceIT.class);

  private RestTemplate restTemplate = new RestTemplate();

  /**
   * Die URL von der ersten Seite, kann mit dem System-Property demo.baseUrl (z.B.. via mvn) überschrieben werden
   * (Je nachdem wo die Applikation betrieben wird). Standard-Wert ist für Tomcat konfiguriert.
   */
  protected final String urlOfFirstPage = System.getProperty("demo.baseUrl", "http://localhost:8080/service/");


  @Test
  public void testInsertUserTalks() {
    LOGGER.info(urlOfFirstPage);

    // 1)
    importCSV(TEST_PID_1, 8, 10, TEST_CSV_1_AND_2);
    importCSV(TEST_PID_2, 14, 11, TEST_CSV_1_AND_2);
    importCSV(TEST_PID_99, 16, 53, TEST_CSV_99);

    // 2)
    final String userUUID = registerUser();
    // 3)
    loadMyTrack(userUUID);
    // 4)
    // Comment comment = createComment(userUUID);
    // 5)
    // updateComment(userUUID, comment);
    // 6)
    // loadComments();
    // 7)
    // deleteComment(userUUID, comment);
    // 8)
    Long ratingId = createRating(userUUID, TEST_PID_1);
    // 9)
    // updateRating(userUUID, TEST_PID_1, ratingId);
    // 10)
    // updateRatingWrongUserId(UUID.randomUUID().toString(), TEST_PID_1, ratingId);
    updateRatingWrongUserId(UUID.randomUUID().toString(), TEST_PID_1, 1L);

  }

  private void updateRatingWrongUserId(final String userUUID, final String pid, final Long vote) {
    final String url = urlOfFirstPage + "rating/" + pid;

    HttpHeaders headers = createHttpHeaders(null);
    HttpEntity<String> entity = new HttpEntity<>(vote.toString(), headers);

    ResponseEntity<Rating> response = restTemplate.exchange(url, HttpMethod.PUT, entity, Rating.class);

    Assert.assertNotNull(response);
    Assert.assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
  }

  private HttpHeaders createHttpHeaders(final String userId) {
    HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN));
    headers.add("X-Access-Token", userId);
    return headers;
  }


  private void updateRating(final String userUUID, final String pid, final Long expectedId) {
    Assert.assertEquals(expectedId, vote(userUUID, pid, 2L).getId());
  }

  private Long createRating(final String userUUID, final String pid) {
    return vote(userUUID, pid, 6L).getId();
  }


  private Rating vote(final String userUUID, final String pid, final Long vote) {
    final String url = urlOfFirstPage + "rating/" + pid;

    HttpEntity<Long> entity = new HttpEntity<>(vote, createHttpHeaders(userUUID));
    ResponseEntity<Rating> response = restTemplate.exchange(url, HttpMethod.PUT, entity, Rating.class);

    Assert.assertNotNull(response.getBody());
    Assert.assertEquals(userUUID, response.getBody().getUserFk());
    Assert.assertEquals(Long.valueOf(vote), response.getBody().getRate());

    return response.getBody();
  }
/*
    private void deleteComment(String userUUID, Comment comment) {
        final String url = baseURL + "comment/" + comment.getId();
        Response response = client.target(url).request(MediaType.APPLICATION_JSON).header("X-Access-Token", userUUID).delete();
        Assert.assertNotNull(response);
        Assert.assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());

        // check by loading all comments
        final String getURL = baseURL + "comment/" + comment.getPid();
        final Comment[] comments = client.target(getURL).request(MediaType.APPLICATION_JSON).get(Comment[].class);
        Assert.assertNotNull(comments);
        Assert.assertEquals(0, comments.length);
    }

    private void loadComments() {
        final String url = baseURL + "comment/" + TEST_PID_1;
        final Comment[] comments = client.target(url).request(MediaType.APPLICATION_JSON).get(Comment[].class);
        Assert.assertNotNull(comments);
        Assert.assertEquals(1, comments.length);
        Assert.assertEquals(NEW_COMMENT, comments[0].getValue());
    }

    private void updateComment(final String userUUID, final Comment comment) {

        final String url = baseURL + "comment/" + comment.getId();

        Response response = client.target(url).request(MediaType.APPLICATION_JSON).header("X-Access-Token", userUUID).post(Entity.json(NEW_COMMENT));
        Assert.assertNotNull(response);
        Assert.assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
    }
*/

  private Comment createComment(final String userUUID) {

    final Comment comment = new Comment();
    comment.setValue("test");
    comment.setPid(TEST_PID_1);

    final String url = urlOfFirstPage + "comment";

    HttpEntity<Comment> entity = new HttpEntity<>(comment, createHttpHeaders(userUUID));
    ResponseEntity<Comment> response = restTemplate.exchange(url, HttpMethod.PUT, entity, Comment.class);

    Assert.assertNotNull(response);
    Assert.assertNotNull(response.getBody());
    Assert.assertNotNull(response.getBody().getId());

    return response.getBody();
  }

  private void loadMyTrack(final String userUUID) {
    final String url = urlOfFirstPage + "mytrack";
    HttpEntity<Object> entity = new HttpEntity<>(null, createHttpHeaders(userUUID));

    ResponseEntity<List<String>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, new ParameterizedTypeReference<List<String>>() {
    });

    Assert.assertNotNull(responseEntity.getBody());
    Assert.assertEquals(2, responseEntity.getBody().size());

  }


  private String registerUser() {
    final String url = urlOfFirstPage + "user";

    final User user = new User();
    user.setUserId(U_NUMMER);
    user.setName("DANIEL MUSTER");
    user.setEmail("mein.name@mein.domain.go");

    HttpEntity<User> entity = new HttpEntity<>(user, createHttpHeaders(null));
    ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

    Assert.assertNotNull(response);
    Assert.assertNotNull(response.getBody());
    Assert.assertFalse(response.getBody().isEmpty());

    return response.getBody();
  }

  private void importCSV(final String pid, final int hour, final int minutes, final String csv) {
    final String url = urlOfFirstPage + "admin/" + pid + "/" + hour + "/" + minutes;

    HttpEntity<String> entity = new HttpEntity<>(csv, createHttpHeaders(null));
    ResponseEntity<Void> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, Void.class);

    Assert.assertNotNull(responseEntity);
    Assert.assertNull(responseEntity.getBody());
  }

}
