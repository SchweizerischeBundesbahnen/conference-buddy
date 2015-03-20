package ch.sbb.confbuddy.service.impl;

import ch.sbb.confbuddy.model.User;
import ch.sbb.confbuddy.service.EmailService;
import ch.sbb.confbuddy.service.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;

/**
 * At the moment fix send via gmail.
 *
 * @author Gilles Zimmermann
 * @since 0.0.1, 2014
 */
@Component
public class SmtpMailServiceImpl implements EmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SmtpMailServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    private JavaMailSender javaMailSender;

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
            } catch (MessagingException e) { // ensure each user has got an email before the flag is set to true
                LOGGER.error("messaging-exception: {}", e);
                // possible infinit loop...  performance??? TODO introduce max retry foreach email.
                user.setRetryCount(user.getRetryCount()+1);
            }
            userRepository.save(user);
        }
    }

    @Value("${spring.mail.username}")
    private String username;


    void sendMail(final User user) throws MessagingException {
        // ugli, ugli, ugli but quick and dirty
        LOGGER.info("send email for user {}", user.getEmail());

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        mimeMessage.addFrom(new Address[]{new InternetAddress(username)});
        mimeMessage.addRecipients(Message.RecipientType.TO, InternetAddress.parse(user.getEmail()));
        mimeMessage.setSubject("conference-buddy registration");
        mimeMessage.setText("Dear " + user.getName() + ","
            + "\n\nYour Registration-URL: http://www.devday.ch/#/register?" + user.getId()
            + "\nSBB UserId: " + user.getUserId()
            + "\n\nYour conference-buddy team"
        );

        javaMailSender.send(mimeMessage);
    }
}
