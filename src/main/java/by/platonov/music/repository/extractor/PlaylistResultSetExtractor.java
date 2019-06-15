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
    public List<Playlist> map(ResultSet resultSet) throws SQLException {
        List<Playlist> playlists = new ArrayList<>();
        Map<Long, Playlist> table = new HashMap<>();
        while (resultSet.next()) {
            if (!table.containsKey(resultSet.getLong("id"))) {
                Playlist playlist = Playlist.builder()
                        .id(resultSet.getLong("id"))
                        .name(resultSet.getString("name"))
                        .tracks(new HashSet<>())
                        .build();
                table.put(playlist.getId(), playlist);
            }
//            else {
//                Musician singer = mapper.mapSinger(resultSet);
//                Musician author = mapper.mapAuthor(resultSet);
//                table.get(resultSet.getLong("id")).getSingers().add(singer);
//                table.get(resultSet.getLong("id")).getAuthors().add(author);
//            }
        }
        table.forEach((id, entity) -> playlists.add(entity));
        return playlists;
    }
}
