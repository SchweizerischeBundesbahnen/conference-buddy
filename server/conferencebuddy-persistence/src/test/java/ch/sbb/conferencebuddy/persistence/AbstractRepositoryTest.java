package ch.sbb.conferencebuddy.persistence;

import ch.sbb.conferencebuddy.model.User;
import ch.sbb.esta.util.persistence.test.JpaTest;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.UUID;

/**
 * @author Gilles Zimmermann
 *
 * @since 0.0.1, 2014
 */
@ContextConfiguration("classpath:persistence-context.xml")
public abstract class AbstractRepositoryTest extends JpaTest {

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
        return user;
    }
}
