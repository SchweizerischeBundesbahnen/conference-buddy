package ch.sbb.conferencebuddy.service.impl;

import ch.sbb.conferencebuddy.model.User;
import ch.sbb.conferencebuddy.persistence.UserRepository;
import ch.sbb.conferencebuddy.service.EmailService;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;

/**
 * @author Gilles Zimmermann
 * @version 0.0.1, 2014
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:esta-core-web.xml",
        "classpath:esta-cache.xml",
        "classpath:persistence-context.xml",
        "classpath:root-context.xml",
        "classpath:email-config-context.xml"})
@Ignore("configuration for username and password is not commited into github, hence this test will faile and the config needs to be adjusted manually.")
public class EmailServiceTest {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSendMail() {

        User testUser = new User();
        testUser.setId(UUID.randomUUID().toString());
        testUser.setUserId("u123456");
        testUser.setName("tester");
        testUser.setEmail("mein.name@mein.domain.go");

        testUser = userRepository.saveAndFlush(testUser);

        emailService.sendMail();

        User user = userRepository.findOne(testUser.getId());
        Assert.assertNotNull(user);
        Assert.assertTrue(user.isEmailSent());
    }
}
