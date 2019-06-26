package by.platonov.music.repository.specification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author dzmitryplatonov on 2019-06-13.
 * @version 0.0.1
 */
public class MusicianIdSpecification implements SqlSpecification {

    private static final String SPECIFICATION = "where musician.id = ?;";

    private long id;

    public MusicianIdSpecification(long id) {
        this.id = id;
    }

    @Override
    public String toSqlClauses() {
        return String.format("where musician.id = %d", id);
    }

    @Override
    public PreparedStatement toPreparedStatement(Connection connection, String parentSql) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(parentSql + SPECIFICATION);
        statement.setLong(1, id);
        return statement;
    }
}
