package by.platonov.music.repository.specification;

import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-06-29.
 * @version 0.0.1
 */
public class TrackPathSpecification implements SqlSpecification {

    private static final String SPECIFICATION = "where media_path = ?;";

    private Path path;

    public TrackPathSpecification(Path path) {
        this.path = path;
    }

    @Override
    public PreparedStatement toPreparedStatement(Connection connection, String parentSql) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(parentSql + SPECIFICATION);
        statement.setString(1, path.toString());
        return statement;
    }
}
