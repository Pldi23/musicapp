package by.platonov.music.repository.specification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-19.
 * @version 0.0.1
 */
public class PlaylistSortedNameSpecification implements SqlSpecification {

    private static final String ASCENDING_SPECIFICATION = "order by LOWER(name) limit ? offset ?;";
    private static final String DESCENDING_SPECIFICATION = "order by LOWER(name) desc limit ? offset ?;";
    private static final String USER_SPECIFICATION = "where private = false ";

    private boolean isAscending;
    private int limit;
    private long offset;
    private boolean isAdmin;

    public PlaylistSortedNameSpecification(boolean isAscending, int limit, long offset, boolean isAdmin) {
        this.isAscending = isAscending;
        this.limit = limit;
        this.offset = offset;
        this.isAdmin = isAdmin;
    }

    @Override
    public PreparedStatement toPreparedStatement(Connection connection, String parentSql) throws SQLException {
        String specification = isAdmin ? parentSql : parentSql + USER_SPECIFICATION;
        specification = isAscending ? specification + ASCENDING_SPECIFICATION : specification + DESCENDING_SPECIFICATION;
        PreparedStatement statement = connection.prepareStatement(specification);
        statement.setInt(1, limit);
        statement.setLong(2, offset);
        return statement;
    }
}
