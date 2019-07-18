package by.platonov.music.repository.specification;

import by.platonov.music.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-18.
 * @version 0.0.1
 */
public class SearchPlayilistNameSpecification implements SqlSpecification {
    private static final String ADMIN_SPECIFICATION = "WHERE LOWER(name) LIKE LOWER(?) limit ? offset ?;";
    private static final String USER_SPECIFICATION = "left join user_playlist up on playlist.id = up.playlist_id " +
            "WHERE LOWER(name) LIKE LOWER(?) and (private = false or user_login = ?) limit ? offset ?;";
    private static final String GUEST_SPECIFICATION =
            "WHERE LOWER(name) LIKE LOWER(?) and private = false limit ? offset ?;";

    private String searchRequest;
    private int limit;
    private long offset;
    private User user;

    public SearchPlayilistNameSpecification(String searchRequest, int limit, long offset, User user) {
        this.searchRequest = searchRequest;
        this.limit = limit;
        this.offset = offset;
        this.user = user;
    }

    @Override
    public PreparedStatement toPreparedStatement(Connection connection, String parentSql) throws SQLException {
        PreparedStatement statement;
        if (user == null) {
            statement = connection.prepareStatement(parentSql + GUEST_SPECIFICATION);
            statement.setString(1, "%" + searchRequest + "%");
            statement.setInt(2, limit);
            statement.setLong(3, offset);
        } else if (user.isAdmin()) {
            statement = connection.prepareStatement(parentSql + ADMIN_SPECIFICATION);
            statement.setString(1, "%" + searchRequest + "%");
            statement.setInt(2, limit);
            statement.setLong(3, offset);
        } else {
            statement = connection.prepareStatement(parentSql + USER_SPECIFICATION);
            statement.setString(1, "%" + searchRequest + "%");
            statement.setString(2, user.getLogin());
            statement.setInt(3, limit);
            statement.setLong(4, offset);
        }
        return statement;
    }
}
