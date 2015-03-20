package ch.sbb.confbuddy.model;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author u215246 (Gilles Zimmermann)
 * @version $Id: $
 * @since 2014
 */
@Entity
public class Comment extends Talk {

    private String author;

    @NotNull
    private String value;

    private LocalDateTime timestamp;

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

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
