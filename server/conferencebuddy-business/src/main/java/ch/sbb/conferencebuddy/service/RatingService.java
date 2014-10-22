package ch.sbb.conferencebuddy.service;

import ch.sbb.conferencebuddy.model.Rating;

/**
 * @author u215246 (Gilles Zimmermann)
 * @version $Id: $
 * @since 2014
 */
public interface RatingService extends TalkService<Rating> {
    void update(final Long id, final Long rate, final String userId);
}
