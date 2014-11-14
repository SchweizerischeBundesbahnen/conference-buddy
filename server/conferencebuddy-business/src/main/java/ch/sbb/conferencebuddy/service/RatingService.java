package ch.sbb.conferencebuddy.service;

import ch.sbb.conferencebuddy.model.Rating;

/**
 * @author Gilles Zimmermann
 *
 * @since 0.0.1, 2014
 */
public interface RatingService extends TalkService<Rating> {

    Rating loadUserRating(final String pid, final String userId);

    Rating saveOrUpdate(final String pid, final Long rate, final String userId);
}
