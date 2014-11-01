package ch.sbb.conferencebuddy.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import ch.sbb.conferencebuddy.model.Rating;
import ch.sbb.conferencebuddy.persistence.AbstractTalkRepository;
import ch.sbb.conferencebuddy.persistence.RatingRepository;
import ch.sbb.conferencebuddy.service.RatingService;
import ch.sbb.esta.core.stereotype.EstaService;
import ch.sbb.esta.util.condition.Reject;

import java.util.List;

/**
 * @author Gilles Zimmermann
 *
 * @since 0.0.1, 2014
 */
@EstaService
public class RatingServiceImpl extends AbstractTalkServiceImpl<Rating> implements RatingService {

    @Autowired
    private RatingRepository ratingRepository;


    @Override
    public Rating loadUserRating(Long pid, String userId) {
        Rating rating = ratingRepository.findByPidAndUserFk(pid, userId);
        if(rating == null) {
            rating = new Rating();
            rating.setPid(pid);
        }
        setAverageRating(rating);
        return rating;
    }

    @Override
    public Rating saveOrUpdate(final Long pid, final Long rate, final String userId) {
        // pre condition
        Reject.ifNull(rate);
        Reject.ifNull(pid);
        Reject.ifEmpty(userId);

        Rating rating = ratingRepository.findByPidAndUserFk(pid, userId);

        if(rating != null){
            rating = update(rating, rate, userId);
        }
        else{
            rating = createNewRating(pid, rate, userId);
            rating = save(rating, userId);
        }

        return rating;
    }

    /**
     * package private for testing only.
     */
    Rating createNewRating(final Long pid, final Long rate, final String userId) {
        final Rating rating = new Rating();
        rating.setRate(rate);
        rating.setPid(pid);
        rating.setUserFk(userId);
        return rating;
    }

    private Rating update(final Rating rating, final Long rate, String userId) {
        // post condition
        Reject.ifNull(rating);
        Reject.ifFalse(userId.equals(rating.getUserFk()));

        // set new value
        rating.setRate(rate);
        ratingRepository.save(rating);
        setAverageRating(rating);
        return rating;
    }

    @Override
    public Rating save(Rating value, String userId) {
        Rating rating = super.save(value, userId);
        setAverageRating(rating);
        return rating;
    }

    @Override
    public List<Rating> loadAll(Long pid) {
        List<Rating> ratings = super.loadAll(pid);
        // n+1 select problem, could be solved with a local cache, but since this method is used only for testing, it's not worth the time
        for(Rating r : ratings)  {
            setAverageRating(r);
        }
        return ratings;
    }

    private void setAverageRating(final Rating rating){
        rating.setAverageRate(ratingRepository.averageByPid(rating.getPid()));
    }

    @Override
    protected AbstractTalkRepository<Rating> getAbstractTalkRepository() {
        return ratingRepository;
    }
}
