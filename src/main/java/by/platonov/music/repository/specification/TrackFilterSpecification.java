package by.platonov.music.repository.specification;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-11.
 * @version 0.0.1
 */
public class TrackFilterSpecification implements SqlSpecification {

    private static final String SPECIFICATION =
            "SELECT distinct track.id as id, track.name as name, genre.id as genreId, genre.genre_name, length," +
                    " track.release_date, track.uuid " +
                    "FROM track " +
                    "join genre on genre.id = track.genre_id " +
                    "join singer_track st on track.id = st.track_id " +
                    "join musician m on st.singer_id = m.id " +
                    "where LOWER(track.name) LIKE LOWER(?) " +
                    "and LOWER(genre_name) like (LOWER(?)) " +
                    "and release_date between ? and ? " +
                    "and LOWER(m.name) LIKE LOWER(?) limit ? offset ?";

    private String trackname;
    private String genreName;
    private LocalDate fromDate;
    private LocalDate toDate;
    private String singerName;
    private int limit;
    private long offset;

    public TrackFilterSpecification(String trackname, String genreName, LocalDate fromDate, LocalDate toDate,
                                    String singerName, int limit, long offset) {
        this.trackname = trackname;
        this.genreName = genreName;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.singerName = singerName;
        this.limit = limit;
        this.offset = offset;
    }

    @Override
    public PreparedStatement toPreparedStatement(Connection connection, String parentSql) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SPECIFICATION);
        statement.setString(1, "%" + trackname + "%");
        statement.setString(2, "%" + genreName + "%");
        statement.setDate(3, Date.valueOf(fromDate));
        statement.setDate(4, Date.valueOf(toDate));
        statement.setString(5, "%" + singerName + "%");
        statement.setInt(6, limit);
        statement.setLong(7, offset);
        return statement;
    }
}
