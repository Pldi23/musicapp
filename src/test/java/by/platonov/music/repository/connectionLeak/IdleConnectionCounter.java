package by.platonov.music.repository.connectionLeak;

import java.sql.Connection;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-06-26.
 * @version 0.0.1
 */
public interface IdleConnectionCounter {

    /**
     * Specifies which Dialect the counter applies to.
     *
     * @param dialect dialect
     *
     * @return applicability.
     */
//    boolean appliesTo(Class<? extends Dialect> dialect);

    /**
     * Count the number of idle connections.
     *
     * @param connection current JDBC connection to be used for querying the number of idle connections.
     *
     * @return idle connection count.
     */
    int count(Connection connection);
}
