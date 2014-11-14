package ch.sbb.conferencebuddy.service.impl;

import ch.sbb.conferencebuddy.model.User;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

import ch.sbb.conferencebuddy.model.Comment;
import ch.sbb.conferencebuddy.persistence.AbstractTalkRepository;
import ch.sbb.conferencebuddy.persistence.CommentRepository;
import ch.sbb.conferencebuddy.service.CommentService;
import ch.sbb.esta.core.stereotype.EstaService;
import ch.sbb.esta.util.condition.Reject;
import org.springframework.util.StringUtils;

/**
 * @author Gilles Zimmermann
 *
 * @since 0.0.1, 2014
 */
@EstaService
public class CommentServiceImpl extends AbstractTalkServiceImpl<Comment> implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public Comment save(Comment value, String userId) {
        validateUser(userId);
        if(value.getTimestamp() == null){
            value.setTimestamp(new DateTime());
        }
        if(StringUtils.isEmpty(value.getAuthor())) {
            User user = userRepository.findOne(userId);
            value.setAuthor(user.getName());
        }

        return super.save(value, userId);
    }

    @Override
    public void update(final Long id, final String value, final String userId) {
        // pre condition
        Reject.ifEmpty(value);

        final Comment comment = findOneComment(id, userId);

        // set new value
        comment.setValue(value);
        commentRepository.save(comment);
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
        validateUser(userId);

        final Comment comment = commentRepository.findOne(id);

        // post condition
        Reject.ifNull(comment);
        validateUser(comment.getUserFk(), userId);
        return comment;
    }

    @Override
    protected AbstractTalkRepository<Comment> getAbstractTalkRepository() {
        return commentRepository;
    }
}
