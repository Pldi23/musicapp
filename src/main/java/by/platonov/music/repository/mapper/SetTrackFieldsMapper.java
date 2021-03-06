package by.platonov.music.repository.mapper;

import by.platonov.music.entity.Track;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * sets track fields to prepared statement which insert {@link Track} to db
 *
 * @author dzmitryplatonov on 2019-06-15.
 * @version 0.0.1
 */
public class SetTrackFieldsMapper implements PreparedStatementMapper<Track> {
    @Override
    public void map(PreparedStatement preparedStatement, Track track) throws SQLException {
        preparedStatement.setString(1, track.getName());
        preparedStatement.setLong(2, track.getGenre().getId());
        preparedStatement.setLong(3, track.getLength());
        preparedStatement.setDate(4, Date.valueOf(track.getReleaseDate()));
        preparedStatement.setString(5, track.getUuid());
    }
}
