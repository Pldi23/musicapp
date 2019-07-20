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
            "left join playlist_track pt on playlist.id = pt.playlist_id " +
            "left join track t on pt.track_id = t.id ";

    private static final String USER_SPECIFICATION = "where playlist.private = false ";
    private static final String ORDER_SPECIFICATION = "group by playlist.id, playlist.name order by sum ";
    private static final String LIMIT_OFFSET = "limit ? offset ?;";
    private static final String DESCENDING = "desc ";

    private boolean isAscending;
    private int limit;
    private long offset;
    private boolean isAdmin;

    public PlaylistSortedLengthSpecification(boolean isAscending, int limit, long offset, boolean isAdmin) {
        this.isAscending = isAscending;
        this.limit = limit;
        this.offset = offset;
        this.isAdmin = isAdmin;
    }

    @Override
    public PreparedStatement toPreparedStatement(Connection connection, String parentSql) throws SQLException {
        String specification = isAdmin ? SPECIFICATION + ORDER_SPECIFICATION
                : SPECIFICATION + USER_SPECIFICATION + ORDER_SPECIFICATION;
        specification = isAscending ? specification + LIMIT_OFFSET : specification + DESCENDING + LIMIT_OFFSET;
        PreparedStatement statement = connection.prepareStatement(specification);
        statement.setInt(1, limit);
        statement.setLong(2, offset);
        return statement;
    }
}
