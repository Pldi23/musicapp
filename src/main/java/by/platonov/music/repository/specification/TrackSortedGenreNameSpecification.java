package by.platonov.music.repository.specification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-05.
 * @version 0.0.1
 */
public class TrackSortedGenreNameSpecification implements SqlSpecification {

    private static final String ASCENDING_SPECIFICATION = "order by LOWER(genre_name) limit ? offset ?;";
    private static final String DESCENDING_SPECIFICATION = "order by LOWER(genre_name) desc limit ? offset ?;";

    private boolean isAscending;
    private int limit;
    private long offset;

    public TrackSortedGenreNameSpecification(boolean isAscending, int limit, long offset) {
        this.isAscending = isAscending;
        this.limit = limit;
        this.offset = offset;
    }

    @Override
    public PreparedStatement toPreparedStatement(Connection connection, String parentSql) throws SQLException {
        String request = isAscending ? parentSql + ASCENDING_SPECIFICATION : parentSql + DESCENDING_SPECIFICATION;
        PreparedStatement statement = connection.prepareStatement(request);
        statement.setInt(1, limit);
        statement.setLong(2, offset);
        return statement;
    }
}
