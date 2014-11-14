package ch.sbb.conferencebuddy;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ch.sbb.conferencebuddy.model.Comment;
import ch.sbb.conferencebuddy.model.Rating;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;

import ch.sbb.conferencebuddy.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

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
 *
 * IMPORTANT: It's only a feel lucky test! boarder cases need to be tested separately.
 *
 * @author Gilles Zimmermann
 *
 * @since 0.0.1, 2014
 */
public class UserWebserviceIT extends AbstractRestIT {

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


    @Test
    public void testInsertUserTalks() {
        LOGGER.info(urlOfFirstPage);

        // 1)
        importCSV(TEST_PID_1, 8, 10, TEST_CSV_1_AND_2);
        importCSV(TEST_PID_2, 14, 11, TEST_CSV_1_AND_2);
        importCSV(TEST_PID_99, 16, 53, TEST_CSV_99);

        // 2)
        String userUUID = registerUser();
        // 3)
        loadMyTrack(userUUID);
        // 4)
        Comment comment = createComment(userUUID);
        // 5)
        updateComment(userUUID, comment);
        // 6)
        // loadComments();
        // 7)
        // deleteComment(userUUID, comment);
        // 8)
        Long ratingId = createRating(userUUID, TEST_PID_1);
        // 9)
        updateRating(userUUID, TEST_PID_1, ratingId);
        // 10)
        updateRatingWrongUserId(UUID.randomUUID().toString(), TEST_PID_1, ratingId);

    }

    private void updateRatingWrongUserId(final String userUUID, final String pid, final long vote) {
        final String url = urlOfFirstPage + "rating/" + pid;
        Response response = client.target(url).request(MediaType.APPLICATION_JSON).header("X-Access-Token", null).put(Entity.json(vote));

        Assert.assertNotNull(response);
        Assert.assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), response.getStatus());
    }

    private void updateRating(final String userUUID, final String pid, final Long expectedId) {
        Assert.assertEquals(expectedId, vote(userUUID, pid, 2).getId());
    }

    private Long createRating(final String userUUID, final String pid) {
        return vote(userUUID, pid, 6).getId();
    }

    /**
     * internal helper class.
     */
    private Rating vote(final String userUUID, final String pid, final long vote) {
        final String url = urlOfFirstPage + "rating/" + pid;
        Rating rating = client.target(url).request(MediaType.APPLICATION_JSON).header("X-Access-Token", userUUID).put(Entity.json(vote), Rating.class);

        Assert.assertNotNull(rating);
        Assert.assertEquals(userUUID, rating.getUserFk());
        Assert.assertEquals(Long.valueOf(vote), rating.getRate());

        return rating;
    }

    private void deleteComment(String userUUID, Comment comment) {
        final String url = urlOfFirstPage + "comment/" + comment.getId();
        Response response = client.target(url).request(MediaType.APPLICATION_JSON).header("X-Access-Token", userUUID).delete();
        Assert.assertNotNull(response);
        Assert.assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());

        // check by loading all comments
        final String getURL = urlOfFirstPage + "comment/" + comment.getPid();
        final Comment[] comments = client.target(getURL).request(MediaType.APPLICATION_JSON).get(Comment[].class);
        Assert.assertNotNull(comments);
        Assert.assertEquals(0, comments.length);
    }

    private void loadComments() {
        final String url = urlOfFirstPage + "comment/" + TEST_PID_1;
        final Comment[] comments = client.target(url).request(MediaType.APPLICATION_JSON).get(Comment[].class);
        Assert.assertNotNull(comments);
        Assert.assertEquals(1, comments.length);
        Assert.assertEquals(NEW_COMMENT, comments[0].getValue());
    }

    private void updateComment(final String userUUID, final Comment comment) {

        final String url = urlOfFirstPage + "comment/" + comment.getId();

        Response response = client.target(url).request(MediaType.APPLICATION_JSON).header("X-Access-Token", userUUID).post(Entity.json(NEW_COMMENT));
        Assert.assertNotNull(response);
        Assert.assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
    }

    private Comment createComment(final String userUUID) {

        final Comment comment = new Comment();
        comment.setValue("test");
        comment.setPid(TEST_PID_1);

        final String url = urlOfFirstPage + "comment";

        Comment savedComment = client.target(url).request(MediaType.APPLICATION_JSON).header("X-Access-Token", userUUID).put(Entity.json(comment), Comment.class);
        Assert.assertNotNull(savedComment);
        Assert.assertNotNull(savedComment.getId());

        return savedComment;
    }

    private void loadMyTrack(final String userUUID) {
        final String url = urlOfFirstPage + "mytrack";

        final String[] mytracks = client.target(url).request(MediaType.APPLICATION_JSON).header("X-Access-Token", userUUID).get(String[].class);
        Assert.assertNotNull(mytracks);
        Assert.assertEquals(2, mytracks.length);

    }

    private String registerUser() {
        final String url = urlOfFirstPage + "user";

        final User user = new User();
        user.setUserId(U_NUMMER);
        user.setName("DANIEL MUSTER");
        user.setEmail("mein.name@mein.domain.go");


        final String userUUID = client.target(url).request(MediaType.TEXT_PLAIN).post(Entity.json(user), String.class);
        Assert.assertNotNull(userUUID);

        return userUUID;
    }

    private void importCSV(final String pid, final int hour, final int minutes, final String csv){
        final String url = urlOfFirstPage + "admin/" + pid + "/" + hour + "/" + minutes;

        final Response response = client.target(url).request(MediaType.TEXT_PLAIN).post(Entity.text(csv));
        Assert.assertNotNull(response);
        Assert.assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
    }

}
