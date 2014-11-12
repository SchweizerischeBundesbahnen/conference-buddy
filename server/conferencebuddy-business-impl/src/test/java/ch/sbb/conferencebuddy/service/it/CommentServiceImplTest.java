package ch.sbb.conferencebuddy.service.it;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ch.sbb.conferencebuddy.model.Comment;
import ch.sbb.conferencebuddy.model.Talk;
import ch.sbb.conferencebuddy.persistence.CommentRepository;
import ch.sbb.conferencebuddy.service.CommentService;
import ch.sbb.conferencebuddy.service.TalkService;

public class CommentServiceImplTest extends AbstractTalkServiceImplIT {


    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentRepository commentRepository; // only for testing

    @Test
    public void testUpdate() {
        final Comment comment = commentService.loadAll(TEST_PID).get(0); // ugly
        commentRepository.flush();
        final String newComment = "new-comment";
        commentService.update(comment.getId(), newComment, userUUID1);

        final List<Comment> comments = commentService.loadAll(TEST_PID);
        boolean found = false;
        for(Comment c : comments){
            if(c.getId().equals(comment.getId())){
                Assert.assertEquals(newComment, c.getValue());
                found = true;
                break;
            }
        }

        Assert.assertTrue(found);
    }

    @Test
    public void testDelete() {
        final Comment comment = commentService.loadAll(TEST_PID).get(0); // ugly
        commentRepository.flush();
        commentService.delete(comment.getId(), userUUID1);

        final List<Comment> comments = commentService.loadAll(TEST_PID);
        boolean found = false;
        for(Comment c : comments){
            if(c.getId().equals(comment.getId())){
                found = true;
                break;
            }
        }

        Assert.assertFalse(found);
    }

    @Override
    protected Talk createTestValue(final String pid, final String userUUID) {
        final Comment comment = new Comment();
        comment.setUserFk(userUUID);
        comment.setPid(pid);
        comment.setValue("test"); // test comment
        return comment;
    }

    @Override
    protected TalkService getTalkService() {
        return commentService;
    }
}
