package ch.sbb.conferencebuddy.persistence;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ch.sbb.conferencebuddy.model.Comment;

import java.util.List;

/**
 * @author Gilles Zimmermann
 *
 * @since 0.0.1, 2014
 */
public class CommentRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    void createTestdata() {
        // TODO implement
    }

    /**
     * bad habit...
     */
    @Test
    public void testCRUDComment(){
        final Comment comment = new Comment();
        comment.setPid("1");
        comment.setAuthor("author");
        comment.setValue("comment");
        comment.setUserFk(USER_ID);
        comment.setTimestamp(new DateTime());

        // create
        final Comment savedComment = commentRepository.save(comment);

        Assert.assertNotNull(savedComment);
        Assert.assertNotNull(savedComment.getId());

        // update
        savedComment.setValue("comment2");
        Comment savedComment2 = commentRepository.save(savedComment);
        commentRepository.flush();

        // read
        savedComment2 = commentRepository.findOne(savedComment2.getId());
        commentRepository.flush(); // just in case
        Assert.assertNotNull(savedComment2);
        Assert.assertEquals("comment2", savedComment2.getValue());

        // delete
        commentRepository.delete(savedComment2);
        savedComment2 = commentRepository.findOne(savedComment2.getId());
        Assert.assertNull(savedComment2);
    }
}
