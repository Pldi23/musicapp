package by.platonov.music.repository.mapper.preparedStatement;

import by.platonov.music.entity.Playlist;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author dzmitryplatonov on 2019-06-15.
 * @version 0.0.1
 */
public class SetPlaylistUpdateMapper implements PreparedStatementMapper<Playlist> {
    @Override
    public void map(PreparedStatement preparedStatement, Playlist playlist) throws SQLException {
        preparedStatement.setString(1, playlist.getName());
        preparedStatement.setLong(2, playlist.getId());
    }
}
