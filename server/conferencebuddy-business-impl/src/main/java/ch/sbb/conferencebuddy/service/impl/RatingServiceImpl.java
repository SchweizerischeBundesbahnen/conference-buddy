package ch.sbb.conferencebuddy.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import ch.sbb.conferencebuddy.model.Rating;
import ch.sbb.conferencebuddy.persistence.AbstractTalkRepository;
import ch.sbb.conferencebuddy.persistence.RatingRepository;
import ch.sbb.conferencebuddy.service.RatingService;
import ch.sbb.esta.core.stereotype.EstaService;
import ch.sbb.esta.util.condition.Reject;

/**
 * @author u215246 (Gilles Zimmermann)
 * @version $Id: $
 * @since 2014
 */
@EstaService
public class RatingServiceImpl extends AbstractTalkServiceImpl<Rating> implements RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    @Override
    public void update(final Long id, final Long rate, final String userId) {
        // pre condition
        Reject.ifNull(rate);
        Reject.ifNull(id);
        Reject.ifEmpty(userId);

        final Rating rating = ratingRepository.findOne(id);

        // post condition
        Reject.ifNull(rating);
        Reject.ifFalse(userId.equals(rating.getUserFk()));

        // set new value
        rating.setRate(rate); // that should be enough, since the rating-obj is still attached and will be
                              // updated.
    }

    @Override
    protected AbstractTalkRepository<Rating> getAbstractTalkRepository() {
        return ratingRepository;
    }
}
