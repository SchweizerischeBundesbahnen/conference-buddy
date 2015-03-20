package ch.sbb.confbuddy.service.repository;

import ch.sbb.confbuddy.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


/**
 * @author Gilles Zimmermann
 *
 * @since 0.0.1, 2014
 */
public interface UserRepository  extends JpaRepository<User, String> {

    @Query("select u from User u where u.emailSent = false and u.retryCount < 10 order by u.created asc")
    List<User> findByEmailSentFalseOrderByCreatedAsc();
}
