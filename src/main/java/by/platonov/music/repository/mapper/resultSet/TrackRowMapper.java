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

    @Override
    public String getKey() {
        return "id";
    }
}
