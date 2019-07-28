package by.platonov.music.repository.specification;

import by.platonov.music.entity.filter.EntityFilter;
import by.platonov.music.entity.filter.TrackFilter;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-11.
 * @version 0.0.1
 */
public class TrackFilterSpecification implements SqlSpecification {

    private static final String SPECIFICATION =
                    "join singer_track st on track.id = st.track_id " +
                    "join musician m on st.singer_id = m.id " +
                    "where LOWER(track.name) LIKE LOWER(?) " +
                    "and LOWER(genre_name) like (LOWER(?)) " +
                    "and release_date between ? and ? " +
                    "and LOWER(m.name) LIKE LOWER(?) " +
                    "group by track.id, track.name, genre.id, genre.genre_name, length, track.release_date, track.created_at, track.uuid " +
                    "limit ? offset ?";

    private TrackFilter filter;
    private int limit;
    private long offset;

    public TrackFilterSpecification(EntityFilter filter, int limit, long offset) {
        this.filter = (TrackFilter) filter;
        this.limit = limit;
        this.offset = offset;
    }

    @Override
    public PreparedStatement toPreparedStatement(Connection connection, String parentSql) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(parentSql + SPECIFICATION);
        statement.setString(1, "%" + filter.getTrackName() + "%");
        statement.setString(2, "%" + filter.getGenreName() + "%");
        statement.setDate(3, Date.valueOf(filter.getFromDate()));
        statement.setDate(4, Date.valueOf(filter.getToDate()));
        statement.setString(5, "%" + filter.getSingerName() + "%");
        statement.setInt(6, limit);
        statement.setLong(7, offset);
        return statement;
    }
}
