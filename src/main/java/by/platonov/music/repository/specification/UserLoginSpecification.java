package by.platonov.music.repository.specification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * to select user from user repository by his login
 * @author dzmitryplatonov on 2019-06-08.
 * @version 0.0.1
 */
public class UserLoginSpecification implements SqlSpecification {

    private static final String SPECIFICATION = "where login = ?;";

    private String login;

    public UserLoginSpecification(String login) {
        this.login = login;
    }

    @Override
    public PreparedStatement toPreparedStatement(Connection connection, String parentSql) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(parentSql + SPECIFICATION);
        statement.setString(1, login);
        return statement;
    }
}
