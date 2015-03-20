package ch.sbb.confbuddy.service.impl;

import ch.sbb.confbuddy.model.User;
import ch.sbb.confbuddy.model.UserTalk;
import ch.sbb.confbuddy.service.UserService;
import ch.sbb.confbuddy.service.repository.UserTalkRepository;
import ch.sbb.confbuddy.util.EtutorCVSReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

/**
 * @author Gilles Zimmermann
 *
 * @since 0.0.1, 2014
 */
@Service
public class UserServiceImpl extends AbstractServiceImpl implements UserService {

    @Autowired
    private UserTalkRepository userTalkRepository;

    @Override
    @Transactional
    public String register(final User user) {
        if(user == null){
          throw new IllegalArgumentException("argument user is null");
        }
        // set new UUID
        String id = UUID.randomUUID().toString();
        user.setId(id);

        // save
        userRepository.save(user);

        return id;
    }

    @Override
    public User validateAndLoadUser(String userId) {
        validateUser(userId);
        return userRepository.findOne(userId);
    }

    /**
     * @param userId UUID out of {@link ch.sbb.confbuddy.model.User#id}
     */
    @Override
    public List<String> loadUserTracks(final String userId) {
        // pre condition
        validateUser(userId);

        // load user
        final User user = userRepository.findOne(userId);

        // post condition
        if(user == null){
          throw new IllegalStateException("user is null");
        }

        // load all talks by u-nummer
        if(!StringUtils.isEmpty(user.getUserId())){
            return userTalkRepository.findByUserId(user.getUserId().toUpperCase());
        }
        else{
            return new ArrayList();
        }
    }

    @Override
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void insertUserTalks(final String pid, final LocalTime startTime, final String csv) {
        if(StringUtils.isEmpty(pid)){
          throw new IllegalArgumentException("argument pid is null or empty");
        }
        if(StringUtils.isEmpty(csv)){
          throw new IllegalArgumentException("argument csv is null or empty");
        }

        // clear DB for pid
        userTalkRepository.removeByPid(pid);

        List<String> uNummers;
        try(BufferedReader bufferedReader = new BufferedReader(new StringReader(csv))) {
            uNummers = EtutorCVSReader.readUNummer(bufferedReader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if(uNummers == null || uNummers.isEmpty()){
          throw new IllegalStateException("argument uNummer is null or empty");
        }

        for(String uNummer : uNummers){
            UserTalk userTalk = new UserTalk();
            userTalk.setPid(pid);
            userTalk.setUserId(uNummer);
            userTalk.setStartTime(startTime);
            userTalkRepository.save(userTalk);
        }
    }
}
