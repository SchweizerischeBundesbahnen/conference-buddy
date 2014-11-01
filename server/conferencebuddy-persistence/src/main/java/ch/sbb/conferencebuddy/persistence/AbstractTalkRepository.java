package ch.sbb.conferencebuddy.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ch.sbb.conferencebuddy.model.Talk;

/**
 * @author Gilles Zimmermann
 *
 * @since 0.0.1, 2014
 */
public interface AbstractTalkRepository<T extends Talk> extends JpaRepository<T, Long> {

    List<T> findByPid(final Long pid);
}
