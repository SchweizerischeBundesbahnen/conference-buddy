package ch.sbb.conferencebuddy;

import ch.sbb.conferencebuddy.model.User;
import ch.sbb.esta.core.type.Email;
import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Testing the whole process, starting:
 * 1. import csv
 * 2. register test user
 * 3. load myTrack
 * 4. comment for a track
 * 5. change comment
 * 6. vote for a track
 * 7. change vote
 *
 * IMPORTANT: It's only a feel lucky test! boarder cases need to be tested separately.
 *
 * @author u215246 (Gilles Zimmermann)
 * @version $Id: $
 * @since 2014
 */
public class UserWebserviceIT extends AbstractRestIT {

    private static final String TEST_CSV = "login;firstname;lastname;email;oe;comment\n" +
            "U123456;Daniel;Muster;DANIEL.MUSTER@SBB-bla.CH;IT-SWE-CD2-T29;\n" +
            "U123457;Stefan;Meier;STEFAN.MEIER@SBB-bla.CH;IT-SWE-CD5-T55;";

    private static final long TEST_PID = 1L;

    private static final String U_NUMMER = "U123456";


    @Test
    public void testInsertUserTalks() {
        importCSV();
        registerUser();
    }

    private String registerUser() {
        final String url = urlOfFirstPage + "/user";

        final User user = new User();
        user.setUserId(U_NUMMER);
        user.setName("DANIEL MUSTER");
        user.setEmail("DANIEL.MUSTER@SBB-bla.CH");


        final String userUUID = client.target(url).request(MediaType.TEXT_PLAIN).post(Entity.json(user), String.class);
        Assert.assertNotNull(userUUID);

        return userUUID;
    }

    private void importCSV(){
        final String url = urlOfFirstPage + "/admin/" + TEST_PID;

        final Response response = client.target(url).request(MediaType.TEXT_PLAIN).post(Entity.text(TEST_CSV));
        Assert.assertNotNull(response);
        Assert.assertEquals(204, response.getStatus());
    }

}
