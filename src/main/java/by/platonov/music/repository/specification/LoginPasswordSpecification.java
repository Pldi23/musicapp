package by.platonov.music.repository.specification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author dzmitryplatonov on 2019-06-16.
 * @version 0.0.1
 */
public class LoginPasswordSpecification implements SqlSpecification {

    private String login;
    private String password;

    public LoginPasswordSpecification(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @Override
    public String toSqlClauses() {
        return String.format("where login = '%s' and password = '%s'", login, password);
    }

    @Override
    public PreparedStatement toPreparedStatement(Connection connection, String parentSql) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(parentSql + " where login = ? and password = ?");
        preparedStatement.setString(1, login);
        preparedStatement.setString(2, password);
        return preparedStatement;

    }
}
