package by.platonov.music.repository.specification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author dzmitryplatonov on 2019-06-21.
 * @version 0.0.1
 */
public class UserEmailHashSpecification implements SqlSpecification {

    private static final String SPECIFICATION = "where e_mail = ? and verification_uuid = ?;";

    private String email;
    private String hash;

    public UserEmailHashSpecification(String email, String hash) {
        this.email = email;
        this.hash = hash;
    }

    @Override
    public PreparedStatement toPreparedStatement(Connection connection, String parentSql) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(parentSql + SPECIFICATION);
        statement.setString(1, email);
        statement.setString(2, hash);
        return statement;
    }
}
