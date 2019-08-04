package by.platonov.music.repository.specification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author dzmitryplatonov on 2019-06-13.
 * @version 0.0.1
 */
public class PlaylistIdSpecification implements SqlSpecification {

    private static final String SPECIFICATION = "where playlist.id = ? order by pt.index;";

    private long id;

    public PlaylistIdSpecification(long id) {
        this.id = id;
    }

    @Override
    public PreparedStatement toPreparedStatement(Connection connection, String parentSql) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(parentSql + SPECIFICATION);
        statement.setLong(1, id);
        return statement;
    }
}
