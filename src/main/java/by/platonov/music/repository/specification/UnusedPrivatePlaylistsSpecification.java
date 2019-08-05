package by.platonov.music.repository.specification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * to select all private playlists without user
 * @author Dzmitry Platonov on 2019-08-05.
 * @version 0.0.1
 */
public class UnusedPrivatePlaylistsSpecification implements SqlSpecification {

    private static final String SPECIFICATION =
            " left join user_playlist up on playlist.id = up.playlist_id\n" +
            " where private = true and user_login is null;";

    @Override
    public PreparedStatement toPreparedStatement(Connection connection, String parentSql) throws SQLException {
        return connection.prepareStatement(parentSql + SPECIFICATION);
    }
}
