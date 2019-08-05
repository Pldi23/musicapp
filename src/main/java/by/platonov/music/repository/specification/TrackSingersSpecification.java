package by.platonov.music.repository.specification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * to select singers who sing a track with the specified id
 * @author dzmitryplatonov on 2019-06-14.
 * @version 0.0.1
 */
public class TrackSingersSpecification implements SqlSpecification {

    private static final String SPECIFICATION =
            "join singer_track st on musician.id = st.singer_id where track_id = ?";

    private long trackId;

    public TrackSingersSpecification(long trackId) {
        this.trackId = trackId;
    }

    @Override
    public PreparedStatement toPreparedStatement(Connection connection, String parentSql) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(parentSql + SPECIFICATION);
        statement.setLong(1, trackId);
        return statement;
    }
}
