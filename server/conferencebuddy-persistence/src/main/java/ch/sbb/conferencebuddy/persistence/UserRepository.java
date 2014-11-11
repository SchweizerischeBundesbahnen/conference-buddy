package ch.sbb.conferencebuddy.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import ch.sbb.conferencebuddy.model.User;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


/**
 * @author Gilles Zimmermann
 *
 * @since 0.0.1, 2014
 */
public interface UserRepository  extends JpaRepository<User, String> {

    @Query("select u from User u where u.emailSent = false order by u.created asc")
    List<User> findByEmailSentFalseOrderByCreatedAsc();
}
