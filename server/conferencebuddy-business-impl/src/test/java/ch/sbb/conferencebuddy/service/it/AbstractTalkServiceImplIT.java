package ch.sbb.conferencebuddy.service.it;

import ch.sbb.conferencebuddy.model.Talk;
import ch.sbb.conferencebuddy.service.TalkService;
import ch.sbb.conferencebuddy.service.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public abstract class AbstractTalkServiceImplIT<T extends Talk> extends AbstractServiceIT {

    protected final static int EXPECTED_VALUES = 3;
    protected final static Long TEST_PID = Long.valueOf(1);
    protected String userUUID;


    @Autowired
    private UserService userService;


    protected UserTestFixture userTestFixture;

    @Before
    public void createInitData(){
        userTestFixture = new UserTestFixture(userService);
        userUUID = userTestFixture.registerNewUser();
        createTestdata(userUUID);
    }

    @Test
    public void testLoadAll() {
        final List<T> list = getTalkService().loadAll(TEST_PID);
        Assert.assertFalse(list.isEmpty());
        Assert.assertEquals(EXPECTED_VALUES, list.size());
    }

    @Test
    public void testSave() {
        T value = getTalkService().save(createTestValue(TEST_PID), userUUID);
        Assert.assertNotNull(value);
        Assert.assertNotNull(value.getId());
        Assert.assertEquals(TEST_PID, value.getPid());
    }

    /**
     * create {@link #EXPECTED_VALUES} values with PID {@link #TEST_PID}.
     */
    protected void createTestdata(String userUUID){
        getTalkService().save(createTestValue(TEST_PID), userUUID);
        getTalkService().save(createTestValue(TEST_PID), userUUID);
        getTalkService().save(createTestValue(TEST_PID), userUUID);
        getTalkService().save(createTestValue(Long.valueOf(2)), userUUID);
        getTalkService().save(createTestValue(Long.valueOf(2)), userUUID);
    }

    protected abstract T createTestValue (final Long pid);

    protected abstract TalkService<T> getTalkService();
}