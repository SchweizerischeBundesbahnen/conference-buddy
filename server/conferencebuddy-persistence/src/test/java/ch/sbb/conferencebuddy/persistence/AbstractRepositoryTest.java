package ch.sbb.conferencebuddy.persistence;

import ch.sbb.conferencebuddy.model.User;
import ch.sbb.esta.util.persistence.test.JpaTest;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.UUID;

/**
 * @author u215246 (Gilles Zimmermann)
 * @version $Id: $
 * @since 2014
 */
@ContextConfiguration("classpath:persistence-context.xml")
public abstract class AbstractRepositoryTest extends JpaTest {

    static final String USER_ID = UUID.randomUUID().toString();

    @Autowired
    private UserRepository userRepository;

    @Before
    public void createInitData(){
        // make sure the tables are empty
        userRepository.deleteAll();
        // create new user
        userRepository.save(createNewUser());

        // create user-load test data
        createTestdata();
    }

    abstract void createTestdata();

    private User createNewUser(){
        final User user = new User();
        user.setId(USER_ID);
        user.setUserId("u123456");
        return user;
    }
}
