package ch.sbb.conferencebuddy.service.impl;

import java.util.List;

import ch.sbb.conferencebuddy.model.Talk;
import ch.sbb.conferencebuddy.persistence.AbstractTalkRepository;
import ch.sbb.conferencebuddy.service.TalkService;
import ch.sbb.esta.util.condition.Reject;

/**
 * @author Gilles Zimmermann
 *
 * @since 0.0.1, 2014
 */
public abstract class AbstractTalkServiceImpl<T extends Talk> implements TalkService<T> {

    @Override
    public List<T> loadAll(final Long pid) {
        // pre condition
        Reject.ifNull(pid);
        return getAbstractTalkRepository().findByPid(pid);
    }


    @Override
    public T save(final T value, final String userId) {
        // pre condition
        Reject.ifNull(value);
        Reject.ifEmpty(userId);

        // check userId
        if (value.getUserFk() == null) {
            value.setUserFk(userId);
        }
        // post condition
        Reject.ifFalse(userId.equals(value.getUserFk()));

        return getAbstractTalkRepository().save(value);
    }

    protected abstract AbstractTalkRepository<T> getAbstractTalkRepository();
}
