package ch.sbb.conferencebuddy.service;

import java.util.List;

import ch.sbb.conferencebuddy.model.User;

/**
 * @author u215246 (Gilles Zimmermann)
 * @version $Id: $
 * @since 2014
 */
public interface UserService {

    public String register(final User user);

    /**
     * @param userId UUID out of {@link ch.sbb.conferencebuddy.model.User#id}
     */
    public List<Long> loadUserTracks(final String userId);

    /**
     * importing all users visiting a {@code pid}.
     */
    void insertUserTalks(final Long pid, final String csv);
}
