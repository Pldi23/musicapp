package by.platonov.music.repository.mapper;

import by.platonov.music.entity.Genre;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * sets genre's title to prepared statement which insert {@link Genre} into db
 *
 * @author dzmitryplatonov on 2019-06-15.
 * @version 0.0.1
 */
public class SetGenreFieldsMapper implements PreparedStatementMapper<Genre> {
    @Override
    public void map(PreparedStatement preparedStatement, Genre genre) throws SQLException {
        preparedStatement.setString(1, genre.getTitle());
    }
}
