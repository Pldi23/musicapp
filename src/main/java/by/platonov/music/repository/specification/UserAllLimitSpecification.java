package by.platonov.music.repository.specification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * to select all users using limit and offset
 *
 * @author Dzmitry Platonov on 2019-07-22.
 * @version 0.0.1
 */
public class UserAllLimitSpecification implements SqlSpecification {

    private static final String SPECIFICATION = "where login is not null limit ? offset ?;";
    private int limit;
    private long offset;

    public UserAllLimitSpecification(int limit, long offset) {
        this.limit = limit;
        this.offset = offset;
    }

    @Override
    public PreparedStatement toPreparedStatement(Connection connection, String parentSql) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(parentSql + SPECIFICATION);
        statement.setInt(1, limit);
        statement.setLong(2, offset);
        return statement;
    }
}
