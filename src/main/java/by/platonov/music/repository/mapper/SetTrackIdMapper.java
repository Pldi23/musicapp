package by.platonov.music.repository.mapper;

import by.platonov.music.entity.Track;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author dzmitryplatonov on 2019-06-15.
 * @version 0.0.1
 */
public class SetTrackIdMapper implements PreparedStatementMapper<Track> {
    @Override
    public void map(PreparedStatement preparedStatement, Track track) throws SQLException {
        preparedStatement.setLong(1, track.getId());
    }
}
