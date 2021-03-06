package by.platonov.music.repository.specification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * to select users who did not confirm registration during one day period
 * @author dzmitryplatonov on 2019-06-22.
 * @version 0.0.1
 */
public class UserNotConfirmedRegistrationSpecification implements SqlSpecification {

    private static final String SPECIFICATION =
            "where active_status = false and verification_uuid is not null and created_at <= now() - INTERVAL '1 DAY';";

    @Override
    public PreparedStatement toPreparedStatement(Connection connection, String parentSql) throws SQLException {
        return connection.prepareStatement(parentSql + SPECIFICATION);
    }
}
