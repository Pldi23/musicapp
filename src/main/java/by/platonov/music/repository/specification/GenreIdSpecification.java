package by.platonov.music.repository.specification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * to select genre by id
 *
 * @author dzmitryplatonov on 2019-06-13.
 * @version 0.0.1
 */
public class GenreIdSpecification implements SqlSpecification {

    private static final String SPECIFICATION = "where genre.id = ?;";

    private long id;

    public GenreIdSpecification(long id) {
        this.id = id;
    }

    @Override
    public PreparedStatement toPreparedStatement(Connection connection, String parentSql) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(parentSql + SPECIFICATION);
        statement.setLong(1, id);
        return statement;
    }
}
