package ch.sbb.confbuddy.service.impl;


import ch.sbb.confbuddy.model.User;
import ch.sbb.confbuddy.service.exception.UserUnauthorizedException;
import ch.sbb.confbuddy.service.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author Gilles Zimmermann
 * @since 0.0.1, 2014
 */
@Service
public abstract class AbstractServiceImpl {

    @Autowired
    protected UserRepository userRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractServiceImpl.class);

    /**
     * validates the commited user.
     *
     * @throws UserUnauthorizedException if the {@code userId} is empty or not equals to {@code expectedUserId}.
     */
    protected void validateUser(final String expectedUserId, final String userId) throws UserUnauthorizedException {
        // check empty
        validateUser(userId);
        if(!userId.equals(expectedUserId)){
            LOGGER.debug("mismatch userId {}, expected userId {}", userId, expectedUserId);
            throw new UserUnauthorizedException(String.format("userId '%s' does not match the expected userId", userId));
        }
    }

    /**
     * validates the commited user.
     *
     * @throws UserUnauthorizedException if the {@code userId} is empty.
     */
    protected void validateUser(final String userId) throws UserUnauthorizedException {
        // check empty
        if(StringUtils.isEmpty(userId)){
            LOGGER.debug("null or empty user");
            throw new UserUnauthorizedException("null or empty userId");
        }
        isRegistered(userId);
    }


    private void isRegistered(final String userId) throws UserUnauthorizedException {
        final User user = userRepository.findOne(userId);
        if(user == null){
            LOGGER.debug("userId is unknown {}", userId);
            throw new UserUnauthorizedException(String.format("userId is unknown %s", userId));
        }
    }
}
