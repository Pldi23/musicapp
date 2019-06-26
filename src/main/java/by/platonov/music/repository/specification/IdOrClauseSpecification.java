package by.platonov.music.repository.specification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-06-26.
 * @version 0.0.1
 */
public class IdOrClauseSpecification implements SqlSpecification {

    private static final String SPECIFICATION = "where id = ? or id = ?;";

    private long id;
    private long anotherId;

    public IdOrClauseSpecification(long id, long anotherId) {
        this.id = id;
        this.anotherId = anotherId;
    }

    @Override
    public String toSqlClauses() {
        return String.format("where id = %d or id = %d", id, anotherId);
    }

    @Override
    public PreparedStatement toPreparedStatement(Connection connection, String parentSql) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(parentSql + SPECIFICATION);
        statement.setLong(1, id);
        statement.setLong(2, anotherId);
        return statement;
    }
}
