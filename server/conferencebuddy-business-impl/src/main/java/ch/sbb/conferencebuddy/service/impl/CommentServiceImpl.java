package ch.sbb.conferencebuddy.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import ch.sbb.conferencebuddy.model.Comment;
import ch.sbb.conferencebuddy.persistence.AbstractTalkRepository;
import ch.sbb.conferencebuddy.persistence.CommentRepository;
import ch.sbb.conferencebuddy.service.CommentService;
import ch.sbb.esta.core.stereotype.EstaService;
import ch.sbb.esta.util.condition.Reject;

/**
 * @author u215246 (Gilles Zimmermann)
 * @version $Id: $
 * @since 2014
 */
@EstaService
public class CommentServiceImpl extends AbstractTalkServiceImpl<Comment> implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public void update(final Long id, final String value, final String userId) {
        // pre condition
        Reject.ifEmpty(value);

        final Comment comment = findOneComment(id, userId);

        // set new value
        comment.setValue(value); // that should be enough, since the comment-obj is still attached and will be
                                 // updated.
    }

    @Override
    public void delete(Long id, String userId) {
        final Comment comment = findOneComment(id, userId);

        commentRepository.delete(comment); // this could be replaced by just deleting by id, but we should
                                           // check if the comment belongs to the user which tries to delete
                                           // the comment
    }

    // helper method
    private Comment findOneComment(Long id, String userId) {
        // pre condition
        Reject.ifNull(id);
        Reject.ifEmpty(userId);

        final Comment comment = commentRepository.findOne(id);

        // post condition
        Reject.ifNull(comment);
        Reject.ifFalse(userId.equals(comment.getUserFk()));
        return comment;
    }

    @Override
    protected AbstractTalkRepository<Comment> getAbstractTalkRepository() {
        return commentRepository;
    }
}
