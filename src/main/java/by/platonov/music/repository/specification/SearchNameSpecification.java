package by.platonov.music.repository.specification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-06-25.
 * @version 0.0.1
 */
public class SearchNameSpecification implements SqlSpecification {

    private static final String SPECIFICATION = "WHERE LOWER(name) LIKE LOWER(?) limit ? offset ?;";
    
    private String searchRequest;
    private int limit;
    private long offset;

    public SearchNameSpecification(String searchRequest, int limit, long offset) {
        this.searchRequest = searchRequest;
        this.limit = limit;
        this.offset = offset;
    }

    @Override
    public PreparedStatement toPreparedStatement(Connection connection, String parentSql) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(parentSql + SPECIFICATION);
        statement.setString(1, "%" + searchRequest + "%");
        statement.setInt(2, limit);
        statement.setLong(3, offset);
        return statement;
    }
}
