package ch.sbb.conferencebuddy.model;

import ch.sbb.esta.core.model.LongIdEntity;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

/**
 * As exported from e-tutor.
 *
 * @author u215246 (Gilles Zimmermann)
 * @version $Id: $
 * @since 2014
 */
@Entity
public class UserTalk extends LongIdEntity {

    /**
     * vortrag-Id.
     */
    @NotNull
    /**
     * u-Nummer.
     */
    private Long pid;
    @NotNull
    private String userId;

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
