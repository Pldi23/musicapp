package by.platonov.music.repository.specification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * to select all entities from repository
 *
 * @author dzmitryplatonov on 2019-06-10.
 * @version 0.0.1
 */
public class IdIsNotNullSpecification implements SqlSpecification {

    private static final String SPECIFICATION = "where id IS NOT NULL;";

    @Override
    public PreparedStatement toPreparedStatement(Connection connection, String parentSql) throws SQLException {
        return connection.prepareStatement(parentSql + SPECIFICATION);
    }
}
