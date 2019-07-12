package by.platonov.music.repository.specification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-05.
 * @version 0.0.1
 */
public class TrackUuidIsNotNullSpecification implements SqlSpecification {

    private static final String SPECIFICATION = "where uuid is not null limit ? offset ?;";

    private long limit;
    private long offset;

    public TrackUuidIsNotNullSpecification(long limit, long offset) {
        this.limit = limit;
        this.offset = offset;
    }

    @Override
    public PreparedStatement toPreparedStatement(Connection connection, String parentSql) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(parentSql + SPECIFICATION);
        statement.setLong(1, limit);
        statement.setLong(2, offset);
        return statement;
    }
}
