package ch.sbb.conferencebuddy.persistence;

import ch.sbb.conferencebuddy.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.UUID;

public class UserRepositoryTest extends AbstractRepositoryTest {


    @Override
    void createTestdata() {
        // create new user
        userRepository.save(createNewUser(UUID.randomUUID().toString(), "u654321", true));
        userRepository.save(createNewUser(UUID.randomUUID().toString(), "u123457", false));
    }

    @Test
    public void testFindByEmailSentTrueOrderByCreatedAt() {
        List<User> userPage = null;
        int count = 0;
        do {
            userPage = userRepository.findByEmailSentFalseOrderByCreatedAsc();

            if(userPage!=null && !userPage.isEmpty()) {
                count++;
                Assert.assertNotNull(userPage);

                User user = userPage.get(0);
                Assert.assertFalse(user.isEmailSent());

                user.setEmailSent(true);
                userRepository.save(user);
            }
        }while(userPage!=null && !userPage.isEmpty());

        Assert.assertNotNull(userPage);
        Assert.assertEquals(2, count);
    }
}