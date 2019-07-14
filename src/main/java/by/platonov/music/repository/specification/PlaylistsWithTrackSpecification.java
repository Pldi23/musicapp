package by.platonov.music.repository.specification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-13.
 * @version 0.0.1
 */
public class PlaylistsWithTrackSpecification implements SqlSpecification {

    private static final String SPECIFICATION =
            "join playlist_track pt on playlist.id = pt.playlist_id where pt.track_id = ?";

    private long trackId;

    public PlaylistsWithTrackSpecification(long trackId) {
        this.trackId = trackId;
    }

    @Override
    public PreparedStatement toPreparedStatement(Connection connection, String parentSql) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(parentSql + SPECIFICATION);
        statement.setLong(1, trackId);
        return statement;
    }
}
