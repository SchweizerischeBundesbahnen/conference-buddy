package ch.sbb.conferencebuddy.service.impl;

import ch.sbb.conferencebuddy.model.User;
import ch.sbb.conferencebuddy.model.UserTalk;
import ch.sbb.conferencebuddy.persistence.UserRepository;
import ch.sbb.conferencebuddy.persistence.UserTalkRepository;
import ch.sbb.conferencebuddy.service.EmailService;
import ch.sbb.conferencebuddy.service.UserService;
import ch.sbb.conferencebuddy.service.util.EtutorCVSReader;
import ch.sbb.esta.core.exception.EstaRuntimeException;
import ch.sbb.esta.core.stereotype.EstaService;
import ch.sbb.esta.util.condition.Reject;
import org.joda.time.LocalTime;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.UUID;

/**
 * @author Gilles Zimmermann
 *
 * @since 0.0.1, 2014
 */
@EstaService
public class UserServiceImpl extends AbstractServiceImpl implements UserService {

    @Autowired
    private UserTalkRepository userTalkRepository;

    @Override
    public String register(final User user) {
        Reject.ifNull(user);

        // set new UUID
        String id = UUID.randomUUID().toString();
        user.setId(id);

        // save
        userRepository.save(user);

        return id;
    }

    /**
     * @param userId UUID out of {@link ch.sbb.conferencebuddy.model.User#id}
     */
    @Override
    public List<String> loadUserTracks(final String userId) {
        // pre condition
        validateUser(userId);

        // load user
        final User user = userRepository.findOne(userId);

        // post condition
        Reject.ifNull(user);

        // load all talks by u-nummer
        return userTalkRepository.findByUserId(user.getUserId());
    }

    @Override
    public void insertUserTalks(final String pid, final LocalTime startTime, final String csv) {
        Reject.ifNull(pid);
        Reject.ifEmpty(csv);

        // clear DB for pid
        userTalkRepository.removeByPid(pid);

        List<String> uNummers;
        try(BufferedReader bufferedReader = new BufferedReader(new StringReader(csv))) {
            uNummers = EtutorCVSReader.readUNummer(bufferedReader);
        } catch (IOException e) {
            throw new EstaRuntimeException(null, e);
        }

        Reject.ifNull(uNummers);
        Reject.ifTrue(uNummers.isEmpty());

        for(String uNummer : uNummers){
            UserTalk userTalk = new UserTalk();
            userTalk.setPid(pid);
            userTalk.setUserId(uNummer);
            userTalk.setStartTime(startTime);
            userTalkRepository.save(userTalk);
        }
    }
}
