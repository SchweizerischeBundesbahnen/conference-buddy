package ch.sbb.conferencebuddy.model;

import static ch.sbb.esta.util.model.EstaMappingType.JODA_PERSISTENCE_DATE_TIME;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import ch.sbb.conferencebuddy.model.xml.adapter.StringDateTimeAdapter;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import ch.sbb.esta.core.model.StringIdEntity;
import ch.sbb.esta.core.webservice.xmladapter.DateTimeAdapter;

/**
 * @author Gilles Zimmermann
 *
 * @since 0.0.1, 2014
 */
@XmlRootElement(name="user")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class User extends StringIdEntity {

    private String name;
    @Size(max = 10)
    private String userId;
    @NotNull
    private String email;

    @XmlTransient
    private boolean emailSent = false;

    @Type(type = JODA_PERSISTENCE_DATE_TIME)
    @XmlJavaTypeAdapter(StringDateTimeAdapter.class)
    @NotNull
    private DateTime created = new DateTime();

    @XmlTransient
    private int retryCount = 0;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEmailSent() {
        return emailSent;
    }

    public void setEmailSent(boolean emailSent) {
        this.emailSent = emailSent;
    }

    public DateTime getCreated() {
        return created;
    }

    public void setCreated(DateTime created) {
        this.created = created;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }
}
