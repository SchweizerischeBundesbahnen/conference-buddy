package ch.sbb.conferencebuddy.persistence;

import java.util.List;
import java.util.UUID;

import ch.sbb.conferencebuddy.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ch.sbb.conferencebuddy.model.Rating;

/**
 * @author Gilles Zimmermann
 *
 * @since 0.0.1, 2014
 */
public class RatingRepositoryTest extends AbstractRepositoryTest {

    private static final Double EXPECTED_AVG_RATING = 8d/3; //(5+1+2)/3
    private static final String TEST_PID = "1";
    private static final Long TEST_RATE = Long.valueOf(1);

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    void createTestdata() {
        // make sure the tables are empty
        ratingRepository.deleteAll();

        final String testUser2UUID = UUID.randomUUID().toString();
        final String testUser3UUID = UUID.randomUUID().toString();
        final User testUser2 = createNewUser(testUser2UUID, "u654321", false);
        userRepository.save(testUser2);
        final User testUser3 = createNewUser(testUser3UUID, "u123457", false);
        userRepository.save(testUser3);

        // create ratings
        ratingRepository.save(createNewRaring(TEST_PID, TEST_RATE, USER_ID));
        ratingRepository.save(createNewRaring(TEST_PID, Long.valueOf(5), testUser2UUID));
        ratingRepository.save(createNewRaring(TEST_PID, Long.valueOf(2), testUser3UUID));
        ratingRepository.save(createNewRaring("2", Long.valueOf(1), USER_ID));
        ratingRepository.save(createNewRaring("3", Long.valueOf(1), USER_ID));
        ratingRepository.flush(); // just to make sure
    }

    @Test
    public void testFindByPid() {
        List<Rating> byPid = ratingRepository.findByPid(TEST_PID);
        Assert.assertNotNull(byPid);
        Assert.assertEquals(3, byPid.size()); // since 3 ratings exists for pid 1

        for(Rating r : byPid){
            Assert.assertEquals(TEST_PID, r.getPid());
        }
    }

    @Test
    public void testAverageByPid(){
        final Double avgRating = ratingRepository.averageByPid("1");
        Assert.assertEquals(EXPECTED_AVG_RATING, avgRating);
    }

    public void testFindByPidAndUserFk(){
        Rating rating = ratingRepository.findByPidAndUserFk(TEST_PID, USER_ID);
        Assert.assertNotNull(rating);
        Assert.assertEquals(TEST_RATE, rating.getRate());
    }

    private Rating createNewRaring(final String pid, final Long rate, final String userUUID) {
        final Rating rating = new Rating();
        rating.setRate(rate);
        rating.setPid(pid);
        rating.setUserFk(userUUID); // FK constraint
        return rating;
    }
}
