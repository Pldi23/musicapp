package by.platonov.music.repository.mapper.resultSet;

import by.platonov.music.entity.Genre;
import by.platonov.music.entity.Track;
import by.platonov.music.exception.RepositoryException;
import by.platonov.music.repository.mapper.resultSet.AbstractRowMapper;
import by.platonov.music.repository.specification.SqlSpecification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * @author dzmitryplatonov on 2019-06-13.
 * @version 0.0.1
 */
public class TrackRowMapper implements AbstractRowMapper<Track> {
    @Override
    public Track map(ResultSet resultSet) throws SQLException {
        return Track.builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("name"))
                .genre(Genre.builder()
                        .id(resultSet.getLong("genreid"))
                        .title(resultSet.getString("genre"))
                        .build())
                .length(resultSet.getLong("length"))
                .releaseDate(resultSet.getDate("release_date").toLocalDate())
                .singers(new HashSet<>())
                .authors(new HashSet<>())
                .build();
    }
}
//    public List<Track> map(ResultSet resultSet) throws SQLException {
//            List<Track> tracks = new ArrayList<>();
//            Map<Long, Track> table = new HashMap<>();
//            while (resultSet.next()) {
//                if (!table.containsKey(resultSet.getLong("id"))) {
//                    Track track = Track.builder()
//                            .id(resultSet.getLong("id"))
//                            .name(resultSet.getString("name"))
//                            .genre(Genre.builder()
//                                    .id(resultSet.getLong("genreid"))
//                                    .title(resultSet.getString("genre"))
//                                    .build())
//                            .length(resultSet.getLong("length"))
//                            .releaseDate(resultSet.getDate("release_date").toLocalDate())
//                            .singers(new HashSet<>())
//                            .authors(new HashSet<>())
//                            .build();
//                    table.put(track.getId(), track);
//                }
//            }
//            table.forEach((id, track) -> tracks.add(track));
//        return tracks;
//    }
//                    else {
//                        Musician singer = mapper.mapSinger(resultSet);
//                        Musician author = mapper.mapAuthor(resultSet);
//                        table.get(resultSet.getLong("id")).getSingers().add(singer);
//                        table.get(resultSet.getLong("id")).getAuthors().add(author);
//                    }
