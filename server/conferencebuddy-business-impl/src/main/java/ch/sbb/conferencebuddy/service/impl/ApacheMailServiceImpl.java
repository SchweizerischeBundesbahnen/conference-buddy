/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2015.
 */

package ch.sbb.conferencebuddy.service.impl;

import ch.sbb.conferencebuddy.model.User;
import ch.sbb.conferencebuddy.persistence.UserRepository;
import ch.sbb.conferencebuddy.service.EmailService;
import ch.sbb.esta.core.stereotype.EstaService;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

/**
 * apache commons-email implementation.
 */
@EstaService
public class ApacheMailServiceImpl implements EmailService {

  private static final Logger LOGGER = LoggerFactory.getLogger(ApacheMailServiceImpl.class);

  @Autowired
  private UserRepository userRepository;

  @Override
  @Scheduled(fixedRate= 9*1000) // every 9sec, performance???
  public synchronized void sendMail() {
    final List<User> userPage = userRepository.findByEmailSentFalseOrderByCreatedAsc(); // performance???
    if (userPage != null && !userPage.isEmpty()) {
      LOGGER.info("still {} emails to send", userPage.size());
      final User user = userPage.get(0);
      try {
        sendMail(user);
        user.setEmailSent(true);
      } catch (EmailException e) { // ensure each user has got an email before the flag is set to true
        LOGGER.error("messaging-exception: {}", e);
        // possible infinit loop...  performance??? TODO introduce max retry foreach email.
        user.setRetryCount(user.getRetryCount()+1);
      }

      userRepository.save(user);
    }
  }

  @Value("${email.username}")
  private String username;

  @Value("${email.password}")
  private String password;

  @Value("${email.smtp}")
  private String smtp;


  void sendMail(final User user) throws EmailException {
    Email email = new SimpleEmail();
    email.setHostName(smtp);
    email.setSmtpPort(465);
    email.setAuthenticator(new DefaultAuthenticator(username, password));
    email.setSSLOnConnect(true);
    email.setFrom(username);
    email.setSubject("conference-buddy registration");
    email.setMsg("Dear " + user.getName() + ","
      + "\n\nYour Registration-URL: http://www.tech4.ch/#/register?" + user.getId()
      + "\nSBB UserId: " + user.getUserId()
      + "\n\nYour conference-buddy team");
    email.addTo(user.getEmail());
    email.send();
  }
}
