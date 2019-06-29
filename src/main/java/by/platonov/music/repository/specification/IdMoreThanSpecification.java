package by.platonov.music.repository.specification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-06-26.
 * @version 0.0.1
 */
public class IdMoreThanSpecification implements SqlSpecification {

    private static final String SPECIFICATION = "where id > ?;";

    private long id;

    public IdMoreThanSpecification(long id) {
        this.id = id;
    }

    @Override
    public PreparedStatement toPreparedStatement(Connection connection, String parentSql) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(parentSql + SPECIFICATION);
        statement.setLong(1, id);
        return statement;
    }
}
