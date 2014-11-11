package ch.sbb.conferencebuddy.service.impl;

import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    @Override
    @Scheduled(fixedRate= 30*1000) // every 30sec
    public void sendMail() {
        final Page<User> userPage = userRepository.findByEmailSentFalseOrderByCreatedAsc(new PageRequest(1, 1));
        if(userPage.hasContent()) {
            final User user = userPage.getContent().get(0);
            sendMail(user);
            user.setEmailSent(true);
            userRepository.save(user);
        }
    }

    void sendMail(final User user) {
        // ugli, ugli, ugli but quick and dirty

        LOGGER.info("send email for user {}", user.getEmail());


        final String username = "sender@mein.domain.go";
        final String password = "conf$buddy_2014";

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

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user.getEmail()));
            message.setSubject("conference-buddy registration");
            message.setText("Dear " + user.getName() + ","
                + "\n\n Your Registration-URL: http://sbb-conferencebuddy.elasticbeanstalk.com/t=" + user.getUserId()
                + "\n\n Your conference-buddy team"
            );

            Transport.send(message);
        } catch (MessagingException e) {
            LOGGER.error("messaging-exception: {}", e);
        }

    }
}
