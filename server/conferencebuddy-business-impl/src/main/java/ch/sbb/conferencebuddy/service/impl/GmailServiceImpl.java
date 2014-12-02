package ch.sbb.conferencebuddy.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;

import ch.sbb.conferencebuddy.model.User;
import ch.sbb.conferencebuddy.persistence.UserRepository;
import ch.sbb.conferencebuddy.service.EmailService;
import ch.sbb.esta.core.stereotype.EstaService;

/**
 * At the moment fix send via gmail.
 *
 * @author Gilles Zimmermann
 * @since 0.0.1, 2014
 */
@EstaService
public class GmailServiceImpl implements EmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GmailServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Value("${email.username}")
    private String username;

    @Value("${email.password}")
    private String password;

    @Override
    @Scheduled(fixedRate= 3*1000) // every 3sec, performance???
    public synchronized void sendMail() {
        final List<User> userPage = userRepository.findByEmailSentFalseOrderByCreatedAsc(); // performance???
        if (userPage != null && !userPage.isEmpty()) {
            LOGGER.info("still {} emails to send", userPage.size());
            final User user = userPage.get(0);
            try {
                sendMail(user);
                user.setEmailSent(true);
            } catch (MessagingException e) { // ensure each user has got an email before the flag is set to true
                LOGGER.error("messaging-exception: {}", e);
                // possible infinit loop...  performance??? TODO introduce max retry foreach email.
                user.setRetryCount(user.getRetryCount()+1);
            }
            userRepository.save(user);
        }
    }

    void sendMail(final User user) throws MessagingException {
        // ugli, ugli, ugli but quick and dirty
        LOGGER.info("send email for user {}", user.getEmail());

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        final Session session = Session.getInstance(props,
            new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user.getEmail()));
        message.setSubject("conference-buddy registration");
        message.setText("Dear " + user.getName() + ","
            + "\n\nYour Registration-URL: http://www.devday.ch/#/register?" + user.getId()
            + "\n\nYour conference-buddy team"
        );

        Transport.send(message);
    }
}
