package by.platonov.music.repository.specification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * to select user from user repository by login and password
 * @author dzmitryplatonov on 2019-06-16.
 * @version 0.0.1
 */
public class UserLoginPasswordSpecification implements SqlSpecification {

    private static final String SPECIFICATION = " where login = ? and password = ?;";

    private String login;
    private String password;

    public UserLoginPasswordSpecification(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @Override
    public PreparedStatement toPreparedStatement(Connection connection, String parentSql) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(parentSql + SPECIFICATION);
        preparedStatement.setString(1, login);
        preparedStatement.setString(2, password);
        return preparedStatement;

    }
}
