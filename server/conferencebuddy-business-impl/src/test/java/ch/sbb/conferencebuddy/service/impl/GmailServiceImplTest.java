package ch.sbb.conferencebuddy.service.impl;

import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ch.sbb.conferencebuddy.model.User;
import ch.sbb.conferencebuddy.persistence.UserRepository;
import ch.sbb.conferencebuddy.service.EmailService;

/**
 * @author Gilles Zimmermann
 * @version 0.0.1, 2014
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:esta-core-web.xml",
        "classpath:esta-cache.xml",
        "classpath:persistence-context.xml",
        "classpath:root-context.xml"})

public class GmailServiceImplTest{

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