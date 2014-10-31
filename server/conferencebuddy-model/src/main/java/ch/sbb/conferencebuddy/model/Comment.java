package ch.sbb.conferencebuddy.model;

import static ch.sbb.esta.util.model.EstaMappingType.JODA_PERSISTENCE_DATE_TIME;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import ch.sbb.esta.core.webservice.xmladapter.DateTimeAdapter;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

/**
 * @author Gilles Zimmermann
 *
 * @since 0.0.1, 2014
 */
@XmlRootElement(name="comment")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Comment extends Talk {

    private String author;
    @NotNull
    private String value;
    @Type(type = JODA_PERSISTENCE_DATE_TIME)
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    private DateTime timestamp;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public DateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(DateTime timestamp) {
        this.timestamp = timestamp;
    }
}
