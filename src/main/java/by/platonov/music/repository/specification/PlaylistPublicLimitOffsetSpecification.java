package by.platonov.music.repository.specification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * to select all public playlists from playlist repository by limit and offset
 * @author Dzmitry Platonov on 2019-07-19.
 * @version 0.0.1
 */
public class PlaylistPublicLimitOffsetSpecification implements SqlSpecification {

    private static final String USER_SPECIFICATION = "where playlist.private = false and playlist.id is not null limit ? offset ?;";
    private static final String ADMIN_SPECIFICATION = "where playlist.id is not null limit ? offset ?;";

    private int limit;
    private long offset;
    private boolean userAdmin;

    public PlaylistPublicLimitOffsetSpecification(int limit, long offset, boolean userAdmin) {
        this.limit = limit;
        this.offset = offset;
        this.userAdmin = userAdmin;
    }

    @Override
    public PreparedStatement toPreparedStatement(Connection connection, String parentSql) throws SQLException {
        String specification = userAdmin ? parentSql + ADMIN_SPECIFICATION : parentSql + USER_SPECIFICATION;
        PreparedStatement statement = connection.prepareStatement(specification);
        statement.setInt(1, limit);
        statement.setLong(2, offset);
        return statement;
    }
}
