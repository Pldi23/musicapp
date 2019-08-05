package by.platonov.music.repository.specification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * to select entity by name ignore case
 *
 * @author Dzmitry Platonov on 2019-06-28.
 * @version 0.0.1
 */
public class EntityNameSpecification implements SqlSpecification {

    private static final String SPECIFICATION = "where LOWER(name) = ?;";

    private String name;

    public EntityNameSpecification(String name) {
        this.name = name;
    }

    @Override
    public PreparedStatement toPreparedStatement(Connection connection, String parentSql) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(parentSql + SPECIFICATION);
        statement.setString(1, name);
        return statement;
    }
}
