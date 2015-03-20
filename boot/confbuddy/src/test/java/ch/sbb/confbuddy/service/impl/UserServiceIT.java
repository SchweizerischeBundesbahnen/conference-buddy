package ch.sbb.confbuddy.service.impl;

import ch.sbb.confbuddy.service.UserService;
import ch.sbb.confbuddy.service.repository.UserTalkRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalTime;
import java.util.List;


public class UserServiceIT extends AbstractServiceIT {

    public static final String PID = "1";

    @Autowired
    private UserService userService;

    @Autowired
    private UserTalkRepository userTalkRepository;

    private UserTestFixture userTestFixture;


    @Before
    public void init(){
        userTestFixture = new UserTestFixture(userService);
    }

    @Test
    public void testRegister() {
        final String registerdUser = userTestFixture.registerNewUser(UserTestFixture.U_NUMMER_1);
        Assert.assertNotNull(registerdUser);
    }

    @Test
    public void testLoadUserTracks() {
        initTestTracks();
        final String registerdUser = userTestFixture.registerNewUser(UserTestFixture.U_NUMMER_1);
        final List<String> pids = userService.loadUserTracks(registerdUser);

        Assert.assertNotNull(pids);
        Assert.assertEquals(1, pids.size());
        Assert.assertEquals(PID, pids.get(0));
    }

    private void initTestTracks() {
        final String testCSV = "login;firstname;lastname;email;oe;comment\n" +
                "U123456;Daniel;Muster;DANIEL.MUSTER@SBB-bla.CH;IT-SWE-CD2-T29;\n" +
                "U123457;Stefan;Meier;STEFAN.MEIER@SBB-bla.CH;IT-SWE-CD5-T55;";

        userService.insertUserTalks(PID, LocalTime.of(15, 10), testCSV);
        userTalkRepository.flush();
    }
}
