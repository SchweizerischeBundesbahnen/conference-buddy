package ch.sbb.confbuddy.service.repository;

import ch.sbb.confbuddy.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Gilles Zimmermann
 *
 * @since 0.0.1, 2014
 */
public interface RatingRepository  extends JpaRepository<Rating, Long> {

    List<Rating> findByPid(final String pid);

    @Query("select avg(r.rate) from Rating r where r.pid = ?1")
    Double averageByPid(final String pid);

    Rating findByPidAndUserFk(final String pid, final String userFk);
}
