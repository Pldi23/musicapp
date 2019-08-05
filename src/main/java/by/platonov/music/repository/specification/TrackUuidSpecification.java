package by.platonov.music.repository.specification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * to select tracks from track repository by uuid
 *
 * @author Dzmitry Platonov on 2019-06-29.
 * @version 0.0.1
 */
public class TrackUuidSpecification implements SqlSpecification {

    private static final String SPECIFICATION = "where uuid = ?;";

    private String uuid;

    public TrackUuidSpecification(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public PreparedStatement toPreparedStatement(Connection connection, String parentSql) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(parentSql + SPECIFICATION);
        statement.setString(1, uuid);
        return statement;
    }
}
