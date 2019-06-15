package by.platonov.music.repository.mapper.preparedStatement;

import by.platonov.music.entity.Musician;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author dzmitryplatonov on 2019-06-15.
 * @version 0.0.1
 */
public class SetMusicianNameMapper implements PreparedStatementMapper<Musician> {
    @Override
    public void map(PreparedStatement preparedStatement, Musician musician) throws SQLException {
        preparedStatement.setString(1, musician.getName());
    }
}
