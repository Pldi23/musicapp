package by.platonov.music.repository.specification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * specify playlists that belong to user
 * to be used with playlist repository
 * @author dzmitryplatonov on 2019-06-14.
 * @version 0.0.1
 */
public class PlaylistOwnedByUserSpecification implements SqlSpecification {

    private static final String SPECIFICATION =
            "join user_playlist on user_playlist.playlist_id = playlist.id where user_playlist.user_login = ?;";

    private String login;

    public PlaylistOwnedByUserSpecification(String login) {
        this.login = login;
    }

    @Override
    public PreparedStatement toPreparedStatement(Connection connection, String parentSql) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(parentSql + SPECIFICATION);
        statement.setString(1, login);
        return statement;
    }
}
