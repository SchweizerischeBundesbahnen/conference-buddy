package ch.sbb.confbuddy.service.repository;

import ch.sbb.confbuddy.Application;
import ch.sbb.confbuddy.model.User;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;

/**
 * @author Gilles Zimmermann
 *
 * @since 0.0.1, 2014
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public abstract class AbstractRepositoryTest {

    static final String USER_ID = UUID.randomUUID().toString();

    @Autowired
    protected UserRepository userRepository;

    @Before
    public void createInitData(){
        // make sure the tables are empty
        userRepository.deleteAll();
        // create new user
        userRepository.save(createNewUser(USER_ID, "u123456", false));

        // create user-load test data
        createTestdata();
    }

    abstract void createTestdata();

    protected User createNewUser(final String userUUID, final String uNummer, boolean emailSent){
        final User user = new User();
        user.setId(userUUID);
        user.setUserId(uNummer);
        user.setEmail("mein.name@main.domain.go");
        user.setEmailSent(emailSent);
        return user;
    }
}
