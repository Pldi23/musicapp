package by.platonov.music.repository.specification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * to select all public playlists
 * @author Dzmitry Platonov on 2019-07-19.
 * @version 0.0.1
 */
public class PlaylistPublicSpecification implements SqlSpecification {

    private static final String SPECIFICATION = "where id IS NOT NULL and playlist.private = false";

    @Override
    public PreparedStatement toPreparedStatement(Connection connection, String parentSql) throws SQLException {
        return connection.prepareStatement(parentSql + SPECIFICATION);
    }
}
