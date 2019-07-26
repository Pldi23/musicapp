package by.platonov.music.repository.extractor;

import by.platonov.music.entity.Musician;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dzmitryplatonov on 2019-06-13.
 * @version 0.0.1
 */
public class MusicianResultSetExtractor implements AbstractResultSetExtractor<Musician> {
    @Override
    public List<Musician> extract(ResultSet resultSet) throws SQLException {
        List<Musician> result = new ArrayList<>();
        while (resultSet.next()) {
            Musician musician = Musician.builder()
                    .id(resultSet.getLong(ExtractConstant.ID))
                    .name(resultSet.getString(ExtractConstant.NAME))
                    .build();
            result.add(musician);
        }
        return result;
    }

}
