package ch.sbb.conferencebuddy.persistence;

import ch.sbb.conferencebuddy.model.UserTalk;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


/**
 * @author u215246 (Gilles Zimmermann)
 * @version $Id: $
 * @since 2014
 */
public class UserTalkRepositoryTest extends AbstractRepositoryTest {

    public static final String TEST_U_NUMMER = "u123456";
    public static final Long PID = Long.valueOf(1);
    @Autowired
    private UserTalkRepository userTalkRepository;



    @Override
    void createTestdata() {
          // TODO implement
    }


    @Test
    public void testFindByUserId() {

        final UserTalk userTalk = new UserTalk();
        userTalk.setPid(PID);
        userTalk.setUserId(TEST_U_NUMMER);

        userTalkRepository.save(userTalk);
        userTalkRepository.flush();

        final List<Long> userTalks = userTalkRepository.findByUserId(TEST_U_NUMMER);

        Assert.assertNotNull(userTalks);
        Assert.assertFalse(userTalks.isEmpty());
        Assert.assertEquals(PID, userTalks.get(0));
    }
}