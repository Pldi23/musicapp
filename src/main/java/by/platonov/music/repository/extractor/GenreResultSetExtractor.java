package by.platonov.music.repository.extractor;

import by.platonov.music.entity.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dzmitryplatonov on 2019-06-13.
 * @version 0.0.1
 */
public class GenreResultSetExtractor implements AbstractResultSetExtractor<Genre> {
    @Override
    public List<Genre> map(ResultSet resultSet) throws SQLException {
        List<Genre> result = new ArrayList<>();
            while (resultSet.next()) {
                Genre genre = Genre.builder()
                        .id(resultSet.getLong("genreid"))
                        .title(resultSet.getString("genre"))
                        .build();
                result.add(genre);
            }
            return result;
    }
}
