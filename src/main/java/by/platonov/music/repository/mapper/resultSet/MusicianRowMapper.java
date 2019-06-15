package by.platonov.music.repository.mapper.resultSet;

import by.platonov.music.entity.Musician;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author dzmitryplatonov on 2019-06-13.
 * @version 0.0.1
 */
public class MusicianRowMapper implements AbstractRowMapper<Musician> {
    @Override
    public Musician map(ResultSet resultSet) throws SQLException {
        return Musician.builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("name"))
                .build();
    }

    @Override
    public String getKey() {
        return "id";
    }
}
