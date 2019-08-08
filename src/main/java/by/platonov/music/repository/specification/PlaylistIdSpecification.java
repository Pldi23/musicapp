package by.platonov.music.repository.specification;

import lombok.extern.log4j.Log4j2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * to select playlist from playlist repository by id
 *
 * @author dzmitryplatonov on 2019-06-13.
 * @version 0.0.1
 */
@Log4j2
public class PlaylistIdSpecification implements SqlSpecification {

    private static final String SPECIFICATION = "where playlist.id = ? ";

    private long id;

    public PlaylistIdSpecification(long id) {
        this.id = id;
    }

    @Override
    public PreparedStatement toPreparedStatement(Connection connection, String parentSql) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(parentSql + SPECIFICATION);
        statement.setLong(1, id);
        return statement;
    }
}
