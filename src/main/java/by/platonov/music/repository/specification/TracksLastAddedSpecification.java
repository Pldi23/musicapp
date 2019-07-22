package by.platonov.music.repository.specification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-19.
 * @version 0.0.1
 */
public class TracksLastAddedSpecification implements SqlSpecification {

    private static final String  SPECIFICATION = "order by created_at desc limit 10";
    @Override
    public PreparedStatement toPreparedStatement(Connection connection, String parentSql) throws SQLException {
        return connection.prepareStatement(parentSql + SPECIFICATION);
    }
}