package ch.sbb.conferencebuddy.service;

import java.util.List;

/**
 * @author u215246 (Gilles Zimmermann)
 * @version $Id: $
 * @since 2014
 */
public interface TalkService<T> {

    /**
     * Gibt alle Kommentare zu einem Talk {@code pid} zurück.
     */
    List<T> loadAll(final Long pid);

    /**
     * speicher einen neuen Kommentar.
     */
    T save(final T value, final String userId);
}
