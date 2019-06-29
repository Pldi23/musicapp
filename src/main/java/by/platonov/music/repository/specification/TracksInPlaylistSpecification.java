package by.platonov.music.repository.specification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author dzmitryplatonov on 2019-06-13.
 * @version 0.0.1
 */
public class TracksInPlaylistSpecification implements SqlSpecification{

    private static final String SPECIFICATION =
            "join playlist_track on track.id = playlist_track.track_id where playlist_track.playlist_id = ?";

    private long playlistId;

    public TracksInPlaylistSpecification(long playlistId) {
        this.playlistId = playlistId;
    }

    @Override
    public PreparedStatement toPreparedStatement(Connection connection, String parentSql) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(parentSql + SPECIFICATION);
        statement.setLong(1, playlistId);
        return statement;
    }
}
