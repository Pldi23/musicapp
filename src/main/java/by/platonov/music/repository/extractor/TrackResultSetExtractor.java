package by.platonov.music.repository.extractor;

import by.platonov.music.entity.Genre;
import by.platonov.music.entity.Track;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static by.platonov.music.repository.extractor.ExtractConstant.*;

/**
 * to disassemble result set to list of {@link Track}
 *
 * @author dzmitryplatonov on 2019-06-13.
 * @version 0.0.1
 */
public class TrackResultSetExtractor implements AbstractResultSetExtractor<Track> {
    @Override
    public List<Track> extract(ResultSet resultSet) throws SQLException{
        List<Track> tracks = new LinkedList<>();
        Map<Long, Track> table = new LinkedHashMap<>();
        while (resultSet.next()) {
            if (!table.containsKey(resultSet.getLong(ID))) {
                Track track = Track.builder()
                        .id(resultSet.getLong(ID))
                        .name(resultSet.getString(NAME))
                        .genre(Genre.builder()
                                .id(resultSet.getLong(GENRE_ID))
                                .title(resultSet.getString(GENRE_NAME))
                                .build())
                        .length(resultSet.getLong(LENGTH))
                        .releaseDate(resultSet.getDate(RELEASE_DATE).toLocalDate())
                        .singers(new HashSet<>())
                        .authors(new HashSet<>())
                        .uuid(resultSet.getString(UUID))
                        .createDate(resultSet.getTimestamp(CREATED_AT).toLocalDateTime().toLocalDate())
                        .build();
                table.put(track.getId(), track);
            }
        }
        table.forEach((id, entity) -> tracks.add(entity));
        return tracks;
    }
}
