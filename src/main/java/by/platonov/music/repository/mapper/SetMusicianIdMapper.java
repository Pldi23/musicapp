package by.platonov.music.repository.mapper;

import by.platonov.music.entity.Musician;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * sets musician's id to prepared statement which delete {@link Musician} from db
 *
 * @author dzmitryplatonov on 2019-06-15.
 * @version 0.0.1
 */
public class SetMusicianIdMapper implements PreparedStatementMapper<Musician> {
    @Override
    public void map(PreparedStatement preparedStatement, Musician musician) throws SQLException {
        preparedStatement.setLong(1, musician.getId());
    }
}
