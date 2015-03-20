package ch.sbb.confbuddy.service.impl;

import ch.sbb.confbuddy.model.Comment;
import ch.sbb.confbuddy.model.User;
import ch.sbb.confbuddy.service.CommentService;
import ch.sbb.confbuddy.service.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

/**
 * @author Gilles Zimmermann
 *
 * @since 0.0.1, 2014
 */
@Service
@Transactional
public class CommentServiceImpl extends AbstractServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public List<Comment> loadAll(final String pid) {
        // pre condition
        if(StringUtils.isEmpty(pid)){
          throw new IllegalArgumentException("argument pid is null or empty");
        }
        return commentRepository.findByPidOrderByTimestampDesc(pid);
    }

    @Override
    public Comment save(Comment value, String userId) {
        validateUser(userId);
        if(value.getTimestamp() == null){
            value.setTimestamp(LocalDateTime.now());
        }
        if(StringUtils.isEmpty(value.getAuthor())) {
            User user = userRepository.findOne(userId);
            value.setAuthor(user.getName());
        }

        // check userId
        if (value.getUserFk() == null) {
          value.setUserFk(userId);
        }

        // post condition
        validateUser(value.getUserFk(), userId);

        return commentRepository.save(value);
    }

    @Override
    public void update(final Long id, final String value, final String userId) {
        // pre condition
        if(StringUtils.isEmpty(value)){
          throw new IllegalArgumentException("argument value is null or empty");
        }

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
        if(id== null){
          throw new IllegalArgumentException("argument id is null");
        }
        validateUser(userId);

        final Comment comment = commentRepository.findOne(id);

        // post condition
        if(comment == null){
          throw new IllegalStateException("comment for  is null");
        }
        validateUser(comment.getUserFk(), userId);
        return comment;
    }
}
