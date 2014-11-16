package ch.sbb.conferencebuddy.service;

import java.util.List;

import ch.sbb.conferencebuddy.model.User;
import org.joda.time.LocalTime;

/**
 * @author Gilles Zimmermann
 *
 * @since 0.0.1, 2014
 */
public interface UserService {

    public String register(final User user);

    /**
     * @param userId UUID out of {@link ch.sbb.conferencebuddy.model.User#id}
     */
    public User validateAndLoadUser(final String userId);

    /**
     * @param userId UUID out of {@link ch.sbb.conferencebuddy.model.User#id}
     */
    public List<String> loadUserTracks(final String userId);

    /**
     * importing all users visiting a {@code pid}.
     */
    void insertUserTalks(final String pid, final LocalTime startTime, final String csv);
}
