package ch.sbb.confbuddy.service.impl;

import ch.sbb.confbuddy.model.Talk;
import ch.sbb.confbuddy.service.TalkService;
import ch.sbb.confbuddy.service.UserService;
import ch.sbb.confbuddy.service.repository.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public abstract class AbstractTalkServiceImplIT<T extends Talk> extends AbstractServiceIT {

    protected final static int EXPECTED_VALUES = 3;
    protected final static String TEST_PID = "1";
    protected final static String SAVE_TEST_PID = "9";
    protected String userUUID1;
    protected String userUUID2;
    protected String userUUID3;


    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;


    protected UserTestFixture userTestFixture;

    @Before
    public void createInitData(){
        userRepository.deleteAll();

        userTestFixture = new UserTestFixture(userService);
        userUUID1 = userTestFixture.registerNewUser(UserTestFixture.U_NUMMER_1);
        userUUID2 = userTestFixture.registerNewUser(UserTestFixture.U_NUMMER_2);
        userUUID3 = userTestFixture.registerNewUser(UserTestFixture.U_NUMMER_3);

        createTestdata(userUUID1, userUUID2, userUUID3);
    }

    @Test
    public void testSave() {
        T value = getTalkService().save(createTestValue(SAVE_TEST_PID, userUUID1), userUUID1);
        Assert.assertNotNull(value);
        Assert.assertNotNull(value.getId());
        Assert.assertEquals(SAVE_TEST_PID, value.getPid());
    }

    /**
     * create {@link #EXPECTED_VALUES} values with PID {@link #TEST_PID}.
     */
    protected void createTestdata(String userUUID1, String userUUID2, String userUUID3){
        getTalkService().save(createTestValue(TEST_PID, userUUID1), userUUID1);
        getTalkService().save(createTestValue(TEST_PID, userUUID2), userUUID2);
        getTalkService().save(createTestValue(TEST_PID, userUUID3), userUUID3);
        getTalkService().save(createTestValue("2", userUUID1), userUUID1);
        getTalkService().save(createTestValue("2", userUUID2), userUUID2);
    }

    protected abstract T createTestValue (final String pid, final String userUUID);

    protected abstract TalkService<T> getTalkService();
}
