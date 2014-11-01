package ch.sbb.conferencebuddy.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import ch.sbb.conferencebuddy.model.User;

/**
 * @author Gilles Zimmermann
 *
 * @since 0.0.1, 2014
 */
public interface UserRepository  extends JpaRepository<User, String> {
}
