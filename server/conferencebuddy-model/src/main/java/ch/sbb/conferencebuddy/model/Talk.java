package ch.sbb.conferencebuddy.model;

import ch.sbb.esta.core.model.LongIdEntity;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

/**
 * @author Gilles Zimmermann
 *
 * @since 0.0.1, 2014
 */
@MappedSuperclass
public abstract class Talk extends LongIdEntity {
    /**
     * vortrag-Id.
     */
    @NotNull
    private String pid;
    /**
     * uuid as FK to {@link ch.sbb.conferencebuddy.model.User#id}
     */
    @NotNull
    private String userFk;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getUserFk() {
        return userFk;
    }

    public void setUserFk(String userFk) {
        this.userFk = userFk;
    }
}
