package by.platonov.music.repository.extractor;

import by.platonov.music.entity.FilePartBean;
import by.platonov.music.entity.Genre;
import by.platonov.music.entity.Track;
import by.platonov.music.exception.FilePartBeanException;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Path;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * @author dzmitryplatonov on 2019-06-13.
 * @version 0.0.1
 */
public class TrackResultSetExtractor implements AbstractResultSetExtractor<Track> {
    @Override
    public List<Track> extract(ResultSet resultSet) throws SQLException{
        List<Track> tracks = new ArrayList<>();
        Map<Long, Track> table = new HashMap<>();
        while (resultSet.next()) {
            if (!table.containsKey(resultSet.getLong("id"))) {
//                Path path = Path.of(resultSet.getString("media_path"));
                Track track = Track.builder()
                        .id(resultSet.getLong("id"))
                        .name(resultSet.getString("name"))
                        .genre(Genre.builder()
                                .id(resultSet.getLong("genreid"))
                                .title(resultSet.getString("genre_name"))
                                .build())
//                        .length(resultSet.getLong("length"))
                        .releaseDate(resultSet.getDate("release_date").toLocalDate())
                        .singers(new HashSet<>())
                        .authors(new HashSet<>())
                        .uuid(resultSet.getString("media_path"))
//                        .path(path)
//                        .filePartBean(new FilePartBean(path.toFile()))
                        .build();
                table.put(track.getId(), track);
            }
        }
        table.forEach((id, entity) -> tracks.add(entity));
        return tracks;
    }
}
