package by.platonov.music.repository.extractor;

import by.platonov.music.entity.Playlist;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * @author dzmitryplatonov on 2019-06-13.
 * @version 0.0.1
 */
public class PlaylistResultSetExtractor implements AbstractResultSetExtractor<Playlist> {
    @Override
    public List<Playlist> extract(ResultSet resultSet) throws SQLException {
        List<Playlist> playlists = new ArrayList<>();
        Map<Long, Playlist> table = new LinkedHashMap<>();
        while (resultSet.next()) {
            if (!table.containsKey(resultSet.getLong("id"))) {
                Playlist playlist = Playlist.builder()
                        .id(resultSet.getLong("id"))
                        .name(resultSet.getString("name"))
                        .tracks(new HashSet<>())
                        .build();
                table.put(playlist.getId(), playlist);
            }
        }
        table.forEach((id, entity) -> playlists.add(entity));
        return playlists;
    }
}
