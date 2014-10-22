package ch.sbb.conferencebuddy.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import ch.sbb.conferencebuddy.model.User;

/**
 * @author u215246 (Gilles Zimmermann)
 * @version $Id: $
 * @since 2014
 */
public interface UserRepository  extends JpaRepository<User, String> {
}
