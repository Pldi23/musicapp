package by.platonov.music.repository.specification;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * to select all tracks
 *
 * @author Dzmitry Platonov on 2019-07-27.
 * @version 0.0.1
 */
public class TrackIdIsNotNullSpecification implements SqlSpecification {

    private static final String SPECIFICATION = "where track.id IS NOT NULL;";

    @Override
    public PreparedStatement toPreparedStatement(Connection connection, String parentSql) throws SQLException {
        return connection.prepareStatement(parentSql + SPECIFICATION);
    }
}
