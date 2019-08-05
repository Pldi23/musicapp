package by.platonov.music.repository.specification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * to select all tracks specified by musician id
 *
 * @author Dzmitry Platonov on 2019-07-13.
 * @version 0.0.1
 */
public class TracksByMusicianSpecification implements SqlSpecification {

    private static final String SPECIFICATION =
            "join singer_track st on track.id = st.track_id join musician m on st.singer_id = m.id where m.id = ?";

    private long musicianId;

    public TracksByMusicianSpecification(long musicianId) {
        this.musicianId = musicianId;
    }

    @Override
    public PreparedStatement toPreparedStatement(Connection connection, String parentSql) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(parentSql + SPECIFICATION);
        statement.setLong(1, musicianId);
        return statement;
    }
}
