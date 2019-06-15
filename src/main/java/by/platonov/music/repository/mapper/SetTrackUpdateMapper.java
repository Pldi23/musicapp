package by.platonov.music.repository.mapper;

import by.platonov.music.entity.Track;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author dzmitryplatonov on 2019-06-15.
 * @version 0.0.1
 */
public class SetTrackUpdateMapper implements PreparedStatementMapper<Track> {
    @Override
    public void map(PreparedStatement preparedStatement, Track track) throws SQLException {
        preparedStatement.setString(1, track.getName());
        preparedStatement.setLong(2, track.getGenre().getId());
        preparedStatement.setLong(3, track.getLength());
        preparedStatement.setDate(4, Date.valueOf(track.getReleaseDate()));
        preparedStatement.setLong(5, track.getId());
    }
}
