package ch.sbb.conferencebuddy.service.impl;

import ch.sbb.conferencebuddy.model.User;
import org.junit.Test;

import java.util.UUID;

/**
 * @author Gilles Zimmermann
 * @version 0.0.1, 2014
 */
public class GmailServiceImplTest{

    @Test
    public void testSendMail() {

        final User testUser = new User();
        testUser.setUserId(UUID.randomUUID().toString());
        testUser.setName("tester");
        testUser.setEmail("mein.name@mein.domain.go");

        new GmailServiceImpl().sendMail(testUser);
    }
}