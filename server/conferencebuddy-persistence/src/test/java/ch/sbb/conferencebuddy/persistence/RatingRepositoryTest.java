package ch.sbb.conferencebuddy.persistence;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ch.sbb.conferencebuddy.model.Rating;

/**
 * @author u215246 (Gilles Zimmermann)
 * @version $Id: $
 * @since 2014
 */
public class RatingRepositoryTest extends AbstractRepositoryTest {

    private static final Double EXPECTED_AVG_RATING = 8d/3; //(5+1+2)/3

    @Autowired
    private RatingRepository ratingRepository;

    @Override
    void createTestdata() {
        // make sure the tables are empty
        ratingRepository.deleteAll();

        // create ratings
        ratingRepository.save(createNewRaring(Long.valueOf(1), Long.valueOf(1)));
        ratingRepository.save(createNewRaring(Long.valueOf(1), Long.valueOf(5)));
        ratingRepository.save(createNewRaring(Long.valueOf(1), Long.valueOf(2)));
        ratingRepository.save(createNewRaring(Long.valueOf(2), Long.valueOf(1)));
        ratingRepository.save(createNewRaring(Long.valueOf(3), Long.valueOf(1)));
        ratingRepository.flush(); // just to make sure

    }

    @Test
    public void testFindByPid() {
        List<Rating> byPid = ratingRepository.findByPid(Long.valueOf(1));
        Assert.assertNotNull(byPid);
        Assert.assertEquals(3, byPid.size()); // since 3 ratings exists for pid 1

        for(Rating r : byPid){
            Assert.assertEquals(Long.valueOf(1), r.getPid());
            Assert.assertEquals(USER_ID, r.getUserFk());
        }
    }

    @Test
    public void testAverageByPid(){
        final Double avgRating = ratingRepository.averageByPid(Long.valueOf(1));
        Assert.assertEquals(EXPECTED_AVG_RATING, avgRating);
    }

    private Rating createNewRaring(final Long pid, final Long rate) {
        final Rating rating = new Rating();
        rating.setRate(rate);
        rating.setPid(pid);
        rating.setUserFk(USER_ID); // FK constraint
        return rating;
    }
}
