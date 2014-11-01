package ch.sbb.conferencebuddy.model;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.hibernate.annotations.Type;

import ch.sbb.esta.core.model.StringIdEntity;
import ch.sbb.esta.core.type.Email;
import ch.sbb.esta.core.type.webservice.xmladapter.EmailAdapter;
import ch.sbb.esta.util.model.EstaMappingType;

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
    private String email;

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
}
