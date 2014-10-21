package ch.sbb.conferencebuddy.service.impl;

import ch.sbb.conferencebuddy.model.User;
import ch.sbb.conferencebuddy.model.UserTalk;
import ch.sbb.conferencebuddy.persistence.UserRepository;
import ch.sbb.conferencebuddy.persistence.UserTalkRepository;
import ch.sbb.conferencebuddy.service.UserService;
import ch.sbb.conferencebuddy.service.util.EtutorCVSReader;
import ch.sbb.esta.core.exception.EstaRuntimeException;
import ch.sbb.esta.core.stereotype.EstaService;
import ch.sbb.esta.util.condition.Reject;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.UUID;

/**
 * @author u215246 (Gilles Zimmermann)
 * @version $Id: $
 * @since 2014
 */
@EstaService
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

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

        // TODO: send mail!!!

        return id;
    }

    /**
     * @param userId UUID out of {@link ch.sbb.conferencebuddy.model.User#id}
     */
    @Override
    public List<Long> loadUserTracks(final String userId) {
        // pre condition
        Reject.ifEmpty(userId);

        // load user
        final User user = userRepository.findOne(userId);

        // post condition
        Reject.ifNull(user);

        // load all talks by u-nummer
        return userTalkRepository.findByUserId(user.getUserId());
    }

    @Override
    public void insertUserTalks(Long pid, String csv) {
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
            userTalkRepository.save(userTalk);
        }
    }
}
