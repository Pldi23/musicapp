package by.platonov.music.repository.specification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * to select payments by user login
 *
 * @author Dzmitry Platonov on 2019-08-07.
 * @version 0.0.1
 */
public class PaymentByLoginSpecification implements SqlSpecification {

    private static final String SPECIFICATION = " where user_login = ? order by created_at;";

    private String userLogin;

    public PaymentByLoginSpecification(String userLogin) {
        this.userLogin = userLogin;
    }

    @Override
    public PreparedStatement toPreparedStatement(Connection connection, String parentSql) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(parentSql + SPECIFICATION);
        statement.setString(1, userLogin);
        return statement;
    }
}
