package by.platonov.music.repository.specification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-08.
 * @version 0.0.1
 */
public class PlaylistSortedLengthSpecification implements SqlSpecification {

    private static final String SPECIFICATION = "select playlist.id, playlist.name, playlist.private, sum(length) as sum " +
                    "from playlist " +
                    "inner join playlist_track pt on playlist.id = pt.playlist_id " +
                    "inner join track t on pt.track_id = t.id " +
                    "group by playlist.id, playlist.name " +
                    "order by sum ";
    private static final String LIMIT_OFFSET = "limit ? offset ?;";
    private static final String DESCENDING = "desc ";

    private boolean isAscending;
    private int limit;
    private long offset;

    public PlaylistSortedLengthSpecification(boolean isAscending, int limit, long offset) {
        this.isAscending = isAscending;
        this.limit = limit;
        this.offset = offset;
    }

    @Override
    public PreparedStatement toPreparedStatement(Connection connection, String parentSql) throws SQLException {
        String request = isAscending ? SPECIFICATION + LIMIT_OFFSET : SPECIFICATION + DESCENDING + LIMIT_OFFSET;
        PreparedStatement statement = connection.prepareStatement(request);
        statement.setInt(1, limit);
        statement.setLong(2, offset);
        return statement;
    }
}
