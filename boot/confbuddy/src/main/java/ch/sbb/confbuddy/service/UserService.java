package ch.sbb.confbuddy.service;


import ch.sbb.confbuddy.model.User;

import java.time.LocalTime;
import java.util.List;

/**
 * @author Gilles Zimmermann
 *
 * @since 0.0.1, 2014
 */
public interface UserService {

    public String register(final User user);

    /**
     * @param userId UUID out of {@link ch.sbb.confbuddy.model.User#id}
     */
    public User validateAndLoadUser(final String userId);

    /**
     * @param userId UUID out of {@link ch.sbb.confbuddy.model.User#id}
     */
    public List<String> loadUserTracks(final String userId);

    /**
     * importing all users visiting a {@code pid}.
     */
    void insertUserTalks(final String pid, final LocalTime startTime, final String csv);
}
