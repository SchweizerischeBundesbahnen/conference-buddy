package ch.sbb.conferencebuddy.persistence;

import org.springframework.data.jpa.repository.Query;

import ch.sbb.conferencebuddy.model.Rating;

/**
 * @author u215246 (Gilles Zimmermann)
 * @version $Id: $
 * @since 2014
 */
public interface RatingRepository  extends AbstractTalkRepository<Rating> {

    @Query("select avg(r.rate) from Rating r where r.pid = ?1")
    Double averageByPid(final Long pid);
}
