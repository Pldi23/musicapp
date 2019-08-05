package by.platonov.music.repository.specification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * to select user's playlists which contains track
 *
 * @author Dzmitry Platonov on 2019-07-17.
 * @version 0.0.1
 */
public class PlaylistByTrackAndUserSpecification implements SqlSpecification {

    private static final String SPECIFICATION = "join user_playlist up on playlist.id = up.playlist_id " +
            "where up.user_login = ? and pt.track_id = ?";

    private long trackId;
    private String login;

    public PlaylistByTrackAndUserSpecification(long trackId, String login) {
        this.trackId = trackId;
        this.login = login;
    }

    @Override
    public PreparedStatement toPreparedStatement(Connection connection, String parentSql) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(parentSql + SPECIFICATION);
        statement.setString(1, login);
        statement.setLong(2, trackId);
        return statement;
    }
}
