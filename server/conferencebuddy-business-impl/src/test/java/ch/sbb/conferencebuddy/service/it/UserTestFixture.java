package ch.sbb.conferencebuddy.service.it;

import ch.sbb.conferencebuddy.model.User;
import ch.sbb.conferencebuddy.service.UserService;
import ch.sbb.esta.core.type.Email;

/**
 * @author u215246 (Gilles Zimmermann)
 * @version $Id: $
 * @since 2014
 */
public final class UserTestFixture {

    public static final String U_NUMMER = "U123456";

    private final UserService userService;


    public UserTestFixture(UserService userService) {
        this.userService = userService;
    }

    public String registerNewUser() {
        final User user = new User();
        user.setName("tester");
        user.setUserId(U_NUMMER);
        user.setEmail("test@tester.ch");
        return userService.register(user);
    }
}
