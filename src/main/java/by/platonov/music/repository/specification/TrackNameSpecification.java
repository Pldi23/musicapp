package by.platonov.music.repository.specification;

import by.platonov.music.repository.specification.SqlSpecification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-03.
 * @version 0.0.1
 */
public class TrackNameSpecification implements SqlSpecification {

    private static final String SPECIFICATION = "where LOWER(name) = ?";

    private String name;

    public TrackNameSpecification(String name) {
        this.name = name;
    }

    @Override
    public PreparedStatement toPreparedStatement(Connection connection, String parentSql) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(parentSql + SPECIFICATION);
        statement.setString(1, name);
        return statement;
    }
}
