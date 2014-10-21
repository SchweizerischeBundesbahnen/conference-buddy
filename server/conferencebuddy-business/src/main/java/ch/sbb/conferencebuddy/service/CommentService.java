package ch.sbb.conferencebuddy.service;

import ch.sbb.conferencebuddy.model.Comment;

/**
 * @author u215246 (Gilles Zimmermann)
 * @version $Id: $
 * @since 2014
 */
public interface CommentService extends TalkService<Comment> {

    /**
     * updated the value eines Kommentars.
     * @param id PK vom Kommentar
     * @param value neuer Wert im Kommentar
     * @param userId {@code ch.sbb.conferencebuddy.model.User#id}
     */
    void update(final Long id, final String value, final String userId);

    /**
     * löscht einen Kommentar, wenn dieser von dem {@code userId} erstellt wurde.
     *
     * @param id PK vom Kommentar
     * @param userId {@code ch.sbb.conferencebuddy.model.User#id}
     */
    void delete(final Long id, final String userId);
}
