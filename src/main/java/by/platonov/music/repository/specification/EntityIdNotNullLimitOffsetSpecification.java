package by.platonov.music.repository.specification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * to select all entities using limit and offset
 *
 * @author Dzmitry Platonov on 2019-07-07.
 * @version 0.0.1
 */
public class EntityIdNotNullLimitOffsetSpecification implements SqlSpecification {


    private static final String SPECIFICATION = "where id is not null limit ? offset ?;";

    private int limit;
    private long offset;

    public EntityIdNotNullLimitOffsetSpecification(int limit, long offset) {
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
