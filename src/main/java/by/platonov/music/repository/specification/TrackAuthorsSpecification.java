package by.platonov.music.repository.specification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * to select all authors of the track with specified id
 *
 * @author dzmitryplatonov on 2019-06-14.
 * @version 0.0.1
 */
public class TrackAuthorsSpecification implements SqlSpecification {

    private static final String SPECIFICATION =
            "join author_track a on musician.id = a.author_id where track_id = ?";

    private long trackId;

    public TrackAuthorsSpecification(long trackId) {
        this.trackId = trackId;
    }

    @Override
    public PreparedStatement toPreparedStatement(Connection connection, String parentSql) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(parentSql + SPECIFICATION);
        statement.setLong(1, trackId);
        return statement;
    }
}
