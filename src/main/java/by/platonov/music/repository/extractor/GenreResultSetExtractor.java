package by.platonov.music.repository.extractor;

import by.platonov.music.entity.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * to disassemble result set to list of {@link Genre}
 *
 * @author dzmitryplatonov on 2019-06-13.
 * @version 0.0.1
 */
public class GenreResultSetExtractor implements AbstractResultSetExtractor<Genre> {
    @Override
    public List<Genre> extract(ResultSet resultSet) throws SQLException {
        List<Genre> result = new ArrayList<>();
            while (resultSet.next()) {
                Genre genre = Genre.builder()
                        .id(resultSet.getLong(ExtractConstant.GENRE_ID))
                        .title(resultSet.getString(ExtractConstant.GENRE_NAME))
                        .build();
                result.add(genre);
            }
            return result;
    }
}
