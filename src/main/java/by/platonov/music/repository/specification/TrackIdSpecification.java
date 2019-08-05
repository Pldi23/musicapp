package by.platonov.music.repository.specification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * to select track by his id
 *
 * @author dzmitryplatonov on 2019-06-13.
 * @version 0.0.1
 */
public class TrackIdSpecification implements SqlSpecification {

    private static final String SPECIFICATION = "where track.id = ?;";

    private long id;

    public TrackIdSpecification(long id) {
        this.id = id;
    }

    @Override
    public PreparedStatement toPreparedStatement(Connection connection, String parentSql) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(parentSql + SPECIFICATION);
        statement.setLong(1, id);
        return statement;
    }
}
