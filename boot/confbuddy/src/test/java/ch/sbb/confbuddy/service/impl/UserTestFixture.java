package ch.sbb.confbuddy.service.impl;


import ch.sbb.confbuddy.model.User;
import ch.sbb.confbuddy.service.UserService;
import ch.sbb.confbuddy.service.repository.UserRepository;

import java.util.List;

/**
 * @author Gilles Zimmermann
 *
 * @since 0.0.1, 2014
 */
public final class UserTestFixture {

    public static final String U_NUMMER_1 = "U123456";
    public static final String U_NUMMER_2 = "U654321";
    public static final String U_NUMMER_3 = "U123457";

    private final UserService userService;


    public UserTestFixture(UserService userService) {
      this.userService = userService;
    }

    public String registerNewUser(String uNummer) {
      final User user = new User();
        user.setName(uNummer);
        user.setUserId(uNummer);
        user.setEmail("uNummer@tester.ch");
        return userService.register(user);
    }
}
