package ch.sbb.conferencebuddy.service.it;

import ch.sbb.conferencebuddy.model.Rating;
import ch.sbb.conferencebuddy.model.Talk;
import ch.sbb.conferencebuddy.persistence.RatingRepository;
import ch.sbb.conferencebuddy.service.RatingService;
import ch.sbb.conferencebuddy.service.TalkService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class RatingServiceImplTest extends AbstractTalkServiceImplIT {

    @Autowired
    private RatingService ratingService;

    @Autowired
    private RatingRepository ratingRepository; // only for testing

    @Test
    public void testSaveOrUpdate() {
        // update
        testUpdate();
    }


    private void testUpdate() {
        final Rating rating = ratingService.loadAll(TEST_PID).get(0); // ugly
        ratingRepository.flush();
        final Long newRate = Long.valueOf(1);
        final Rating updatedRating = ratingService.saveOrUpdate(rating.getPid(), newRate, rating.getUserFk());
        Assert.assertEquals(rating.getId(), updatedRating.getId());
        Assert.assertEquals(newRate, updatedRating.getRate());

        final List<Rating> ratings = ratingService.loadAll(TEST_PID);
        boolean found = false;
        for(Rating r : ratings){
            if(r.getId().equals(rating.getId())){
                Assert.assertEquals(newRate, r.getRate());
                found = true;
                break;
            }
        }

        Assert.assertTrue(found);
    }

    @Override
    protected Talk createTestValue(final String pid, final String userUUID) {
        final Rating rating = new Rating();
        rating.setUserFk(userUUID);
        rating.setPid(pid);
        rating.setRate(Long.valueOf(6)); // test rating is always 6
        return rating;
    }

    @Override
    protected TalkService getTalkService() {
        return ratingService;
    }
}