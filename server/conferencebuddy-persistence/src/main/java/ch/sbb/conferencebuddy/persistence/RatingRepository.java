package ch.sbb.conferencebuddy.persistence;

import org.springframework.data.jpa.repository.Query;

import ch.sbb.conferencebuddy.model.Rating;

/**
 * @author Gilles Zimmermann
 *
 * @since 0.0.1, 2014
 */
public interface RatingRepository  extends AbstractTalkRepository<Rating> {

    @Query("select round(avg(r.rate),1) from Rating r where r.pid = ?1")
    Double averageByPid(final String pid);

    Rating findByPidAndUserFk(final String pid, final String userFk);
}
