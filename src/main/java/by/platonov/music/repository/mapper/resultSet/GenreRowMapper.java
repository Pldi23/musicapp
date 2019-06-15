package by.platonov.music.repository.mapper.resultSet;

import by.platonov.music.entity.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author dzmitryplatonov on 2019-06-13.
 * @version 0.0.1
 */
public class GenreRowMapper implements AbstractRowMapper<Genre> {
    @Override
    public Genre map(ResultSet resultSet) throws SQLException {
        return Genre.builder()
                .id(resultSet.getLong("genreid"))
                .title(resultSet.getString("genre"))
                .build();
    }
}
