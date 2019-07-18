package by.platonov.music.repository.specification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-18.
 * @version 0.0.1
 */
public class MusicianMostPlayedGenreSpecification implements SqlSpecification {

    @Override
    public PreparedStatement toPreparedStatement(Connection connection, String parentSql) throws SQLException {
        return null;
    }
}
