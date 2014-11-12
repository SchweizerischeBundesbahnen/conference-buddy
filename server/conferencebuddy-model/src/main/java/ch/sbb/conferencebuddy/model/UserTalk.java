package ch.sbb.conferencebuddy.model;

import static ch.sbb.esta.util.model.EstaMappingType.JODA_PERSISTENCE_LOCAL_TIME;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.hibernate.annotations.Type;
import org.joda.time.LocalTime;

import ch.sbb.esta.core.model.LongIdEntity;
import ch.sbb.esta.core.webservice.xmladapter.LocalTimeAdapter;

/**
 * As exported from e-tutor.
 *
 * @author Gilles Zimmermann
 *
 * @since 0.0.1, 2014
 */
@Entity
public class UserTalk extends LongIdEntity {

    /**
     * vortrag-Id.
     */
    @NotNull
    private String pid;
    /**
     * u-Nummer.
     */
    @NotNull
    private String userId;

    @Type(type = JODA_PERSISTENCE_LOCAL_TIME)
    @XmlJavaTypeAdapter(LocalTimeAdapter.class)
    @NotNull
    private LocalTime startTime;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }
}
