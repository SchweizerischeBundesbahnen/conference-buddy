package ch.sbb.confbuddy.service.repository;

import ch.sbb.confbuddy.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Gilles Zimmermann
 *
 * @since 0.0.1, 2014
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByPidOrderByTimestampDesc(final String pid);
}
