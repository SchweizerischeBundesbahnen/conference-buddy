package ch.sbb.confbuddy.service;

import java.util.List;

/**
 * @author Gilles Zimmermann
 *
 * @since 0.0.1, 2014
 */
public interface TalkService<T> {

    /**
     * Gibt alle Kommentare zu einem Talk {@code pid} zurück.
     */
    List<T> loadAll(final String pid);

    /**
     * speicher einen neuen Kommentar.
     */
    T save(final T value, final String userId);
}
