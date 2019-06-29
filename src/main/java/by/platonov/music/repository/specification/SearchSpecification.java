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
public class SearchSpecification implements SqlSpecification {

    private static final String SPECIFICATION = "WHERE LOWER(name) LIKE LOWER(?);";
    
    private String searchRequest;

    public SearchSpecification(String searchRequest) {
        this.searchRequest = searchRequest;
    }

    @Override
    public PreparedStatement toPreparedStatement(Connection connection, String parentSql) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(parentSql + SPECIFICATION);
        statement.setString(1, "%" + searchRequest + "%");
        return statement;
    }
}
