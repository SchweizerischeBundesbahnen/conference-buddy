package ch.sbb.conferencebuddy.persistence;

import ch.sbb.conferencebuddy.model.Comment;

import java.util.List;

/**
 * @author Gilles Zimmermann
 *
 * @since 0.0.1, 2014
 */
public interface CommentRepository extends AbstractTalkRepository<Comment> {
    List<Comment> findByPidOrderByTimestampDesc(final String pid);
}
