package by.platonov.music.repository.specification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * to select musician from musician repository by name
 *
 * @author Dzmitry Platonov on 2019-07-26.
 * @version 0.0.1
 */
public class MusicianNameSpecification implements SqlSpecification {

    private static final String SPECIFICATION = "where name = ?;";

    private String name;

    public MusicianNameSpecification(String name) {
        this.name = name;
    }

    @Override
    public PreparedStatement toPreparedStatement(Connection connection, String parentSql) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(parentSql + SPECIFICATION);
        statement.setString(1, name);
        return statement;
    }
}
