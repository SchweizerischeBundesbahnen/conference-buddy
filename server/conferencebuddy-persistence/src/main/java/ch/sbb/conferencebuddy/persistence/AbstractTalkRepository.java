package ch.sbb.conferencebuddy.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ch.sbb.conferencebuddy.model.Talk;

/**
 * @author u215246 (Gilles Zimmermann)
 * @version $Id: $
 * @since 2014
 */
public interface AbstractTalkRepository<T extends Talk> extends JpaRepository<T, Long> {

    List<T> findByPid(final Long pid);
}
