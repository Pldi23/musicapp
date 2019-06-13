package by.platonov.music.repository.mapper;

import by.platonov.music.entity.Playlist;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

/**
 * @author dzmitryplatonov on 2019-06-13.
 * @version 0.0.1
 */
public class PlaylistRowMapper implements AbstractRowMapper<Playlist> {
    @Override
    public Playlist map(ResultSet resultSet) throws SQLException {
        return Playlist.builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("name"))
                .tracks(new HashSet<>())
                .build();
    }
}
