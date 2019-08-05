package by.platonov.music.repository.specification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * to select genre by title
 *
 * @author Dzmitry Platonov on 2019-06-28.
 * @version 0.0.1
 */
public class GenreTitleSpecification implements SqlSpecification {

    private static final String SPECIFICATION = "where genre_name = ?;";

    private String title;

    public GenreTitleSpecification(String title) {
        this.title = title;
    }

    @Override
    public PreparedStatement toPreparedStatement(Connection connection, String parentSql) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(parentSql + SPECIFICATION);
        statement.setString(1, title);
        return statement;
    }
}
