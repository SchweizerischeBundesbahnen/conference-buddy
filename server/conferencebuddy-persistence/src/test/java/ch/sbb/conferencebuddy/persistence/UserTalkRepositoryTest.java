package ch.sbb.conferencebuddy.persistence;

import ch.sbb.conferencebuddy.model.UserTalk;
import org.joda.time.LocalTime;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


/**
 * @author Gilles Zimmermann
 *
 * @since 0.0.1, 2014
 */
public class UserTalkRepositoryTest extends AbstractRepositoryTest {

    public static final String TEST_U_NUMMER = "u123456";
    public static final String PID = "1";
    public static final String PID_2 = "2";
    @Autowired
    private UserTalkRepository userTalkRepository;



    @Override
    void createTestdata() {
          // TODO implement
    }


    @Test
    public void testFindByUserId() {

        createNewUserTalk(PID, new LocalTime(17, 2));
        createNewUserTalk(PID_2, new LocalTime(15, 2));

        final List<String> userTalks = userTalkRepository.findByUserId(TEST_U_NUMMER);

        Assert.assertNotNull(userTalks);
        Assert.assertEquals(2, userTalks.size());
        Assert.assertEquals(PID_2, userTalks.get(0));
        Assert.assertEquals(PID, userTalks.get(1));
    }


    private void createNewUserTalk(final String pid, final LocalTime startZeit){
        final UserTalk userTalk = new UserTalk();
        userTalk.setPid(pid);
        userTalk.setUserId(TEST_U_NUMMER);
        userTalk.setStartTime(startZeit);

        userTalkRepository.save(userTalk);
        userTalkRepository.flush();
    }
}