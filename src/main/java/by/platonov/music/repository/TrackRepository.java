package by.platonov.music.repository;

import by.platonov.music.entity.Genre;
import by.platonov.music.entity.Musician;
import by.platonov.music.exception.RepositoryException;
import by.platonov.music.entity.Track;
import by.platonov.music.repository.specification.SelectIdSpecification;
import by.platonov.music.repository.specification.SqlSpecification;
import lombok.extern.log4j.Log4j2;
import org.intellij.lang.annotations.Language;

import java.sql.*;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author dzmitryplatonov on 2019-06-07.
 * @version 0.0.1
 */
@Log4j2
public class TrackRepository implements Repository<Track> {

    @Language("SQL")
    private static final String SQL_SELECT_TRACK =
            "select track.id, track.name as trackName, genre.id as genreId, genre.name as genre, track.length, track.release_date, " +
                    "singer.id singerId, singer.name singerName, singer.is_singer singer_is_singer, singer.is_author singer_is_author, " +
                    "author.id authorId, author.name authorName, author.is_singer author_is_singer, author.is_author author_is_author " +
                    "from track " +
                    "join genre on track.genre_id = genre.id " +
                    "join singer_track on track.id = singer_track.track_id " +
                    "join author_track on track.id = author_track.track_id " +
                    "join musician singer on singer_track.singer_id = singer.id " +
                    "join musician author on author_track.author_id = author.id " +
                    "where track.";

    @Language("SQL")
    private static final String SQL_COUNT_TRACK = "SELECT COUNT(*) FROM track WHERE ";
    @Language("SQL")
    private static final String SQL_INSERT_TRACK =
            "INSERT INTO track(name, genre_id, length, release_date) VALUES (?, ?, ?, ?) RETURNING ID;";
    @Language("SQL")
    private static final String SQL_INSERT_SINGER_LINK = "INSERT INTO singer_track(track_id, singer_id) VALUES (?, ?);";
    @Language("SQL")
    private static final String SQL_INSERT_AUTHOR_LINK = "INSERT INTO author_track(track_id, author_id) VALUES (?, ?);";
    @Language("SQl")
    private static final String SQL_DELETE_TRACK = "DELETE FROM track WHERE id = ?";
    @Language("SQL")
    private static final String SQL_DELETE_SINGER_LINK = "DELETE FROM singer_track WHERE track_id = ?";
    @Language("SQL")
    private static final String SQL_DELETE_AUTHOR_LINK = "DELETE FROM author_track WHERE track_id = ?";
    @Language("SQL")
    private static final String SQL_DELETE_PLAYLIST_LINK = "DELETE FROM playlist_track WHERE track_id = ?";
    @Language("SQL")
    private static final String SQL_UPDATE_TRACK = "UPDATE track SET name = ?, genre_id = ?, length = ?, release_date = ? WHERE id = ?;";
    @Language("SQL")
    private static final String SQL_UPDATE_TRACK_SINGER_LINK = "UPDATE singer_track SET singer_id = ? WHERE track_id = ?;";
    @Language("SQL")
    private static final String SQL_UPDATE_TRACK_AUTHOR_LINK = "UPDATE author_track SET author_id = ? WHERE track_id = ?;";
//    @Language("SQL")
//    private static final String SQL_UPDATE_PLAYLIST_TRACK_LINK = "UPDATE playlist_track SET playlist_id, track_id = ? WHERE track_id = ?;";


    @Override
    public boolean add(Track track) throws RepositoryException {
        return TransactionHandler.transactional(connection -> {
            Optional<Track> optionalTrack = findOneNonTransactional(connection, new SelectIdSpecification(track.getId()));
            if (optionalTrack.isEmpty()) {
                try (PreparedStatement statementTrack = connection.prepareStatement(SQL_INSERT_TRACK);
                     PreparedStatement statementSingerLink = connection.prepareStatement(SQL_INSERT_SINGER_LINK);
                     PreparedStatement statementAuthorLink = connection.prepareStatement(SQL_INSERT_AUTHOR_LINK)) {
                    statementTrack.setString(1, track.getName());
                    statementTrack.setLong(2, track.getGenre().getId());
                    statementTrack.setLong(3, track.getLength());
                    statementTrack.setDate(4, Date.valueOf(track.getReleaseDate()));
                    ResultSet resultSet = statementTrack.executeQuery();
                    resultSet.next();
                    long trackId = resultSet.getLong(1);
                    track.getSingers().forEach(s -> {
                        try {
                            statementSingerLink.setLong(1, trackId);
                            statementSingerLink.setLong(2, s.getId());
                            statementSingerLink.execute();
                        } catch (SQLException e) {
                            log.error(e);
                        }
                    });
                    track.getAuthors().forEach(a -> {
                        try {
                            statementAuthorLink.setLong(1, trackId);
                            statementAuthorLink.setLong(2, a.getId());
                            statementAuthorLink.execute();
                        } catch (SQLException e) {
                            log.error(e);
                        }
                    });
                    log.debug("Track: " + trackId + " added");
                    return true;
                } catch (SQLException e) {
                    throw new RepositoryException(e);
                }
            } else {
                log.debug("Track id: " + track.getId() + " is already exists");
                return false;
            }
        });
    }

    @Override
    public boolean remove(Track track) throws RepositoryException {
        return TransactionHandler.transactional(connection -> {
            Optional<Track> optionalTrack = findOneNonTransactional(connection, new SelectIdSpecification(track.getId()));
            if (optionalTrack.isPresent()) {
                try(PreparedStatement statementTrack = connection.prepareStatement(SQL_DELETE_TRACK);
                    PreparedStatement statementSingerLink = connection.prepareStatement(SQL_DELETE_SINGER_LINK);
                    PreparedStatement statementAuthorLink = connection.prepareStatement(SQL_DELETE_AUTHOR_LINK);
                    PreparedStatement statementPlaylistLink = connection.prepareStatement(SQL_DELETE_PLAYLIST_LINK)) {
                    statementTrack.setLong(1, track.getId());
                    statementSingerLink.setLong(1, track.getId());
                    statementAuthorLink.setLong(1, track.getId());
                    statementPlaylistLink.setLong(1, track.getId());
                    statementSingerLink.execute();
                    statementAuthorLink.execute();
                    statementPlaylistLink.execute();
                    statementTrack.execute();
                    log.debug(track + " was removed");
                } catch (SQLException e) {
                    throw new RepositoryException(e);
                }
                return true;
            } else {
                log.debug("Track: " + track.getId() + " was not found");
                return false;
            }
        });
    }

    @Override
    public boolean update(Track track) throws RepositoryException {
        return TransactionHandler.transactional(connection -> {
            Optional<Track> optionalTrack = findOneNonTransactional(connection, new SelectIdSpecification(track.getId()));
            if (optionalTrack.isPresent()) {
                try(PreparedStatement statementUpdateTrack = connection.prepareStatement(SQL_UPDATE_TRACK);
                    PreparedStatement statementUpdateSingerLink = connection.prepareStatement(SQL_UPDATE_TRACK_SINGER_LINK);
                    PreparedStatement statementUpdateAuthorLink = connection.prepareStatement(SQL_UPDATE_TRACK_AUTHOR_LINK)) {
                    statementUpdateTrack.setString(1, track.getName());
                    statementUpdateTrack.setLong(2, track.getGenre().getId());
                    statementUpdateTrack.setLong(3, track.getLength());
                    statementUpdateTrack.setDate(4, Date.valueOf(track.getReleaseDate()));
                    statementUpdateTrack.setLong(5, track.getId());
                    track.getSingers().forEach(s -> {
                        try {
                            statementUpdateSingerLink.setLong(1, s.getId());
                            statementUpdateSingerLink.setLong(2, track.getId());
                            statementUpdateSingerLink.executeUpdate();
                        } catch (SQLException e) {
                            log.error(e);
                        }
                    });
                    track.getAuthors().forEach(a -> {
                        try {
                            statementUpdateAuthorLink.setLong(1, a.getId());
                            statementUpdateAuthorLink.setLong(2, track.getId());
                            statementUpdateAuthorLink.executeUpdate();
                        } catch (SQLException e) {
                            log.error(e);
                        }
                    });
                    statementUpdateTrack.executeUpdate();
                    log.debug(track + " updated");
                    return true;
                } catch (SQLException e) {
                   throw new RepositoryException(e);
                }
            } else {
                log.debug("Track number: " + track.getId() + " was not found");
                return false;
            }
        });
    }

    @Override
    public List<Track> query(SqlSpecification specification) throws RepositoryException {
        return null;
    }

    @Override
    public Optional<Track> findOne(SqlSpecification specification) throws RepositoryException {
        return TransactionHandler.transactional(connection -> findOneNonTransactional(connection, specification));
    }

    @Override
    public int count(SqlSpecification specification) throws RepositoryException {
        return TransactionHandler.transactional(connection -> {
            try (PreparedStatement statement = connection.prepareStatement(SQL_COUNT_TRACK + specification.toSqlClauses());
                 ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                return resultSet.getInt(1);
            } catch (SQLException e) {
                throw new RepositoryException(e);
            }
        });
    }

    private Optional<Track> findOneNonTransactional(Connection connection, SqlSpecification specification) throws RepositoryException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_TRACK + specification.toSqlClauses());
             ResultSet resultSet = statement.executeQuery()) {
            Track track = null;
            if (resultSet.next()) {
                Set<Musician> singers = new HashSet<>();
                Set<Musician> authors = new HashSet<>();
                do {
                    singers.add(Musician.builder()
                            .id(resultSet.getLong("singerid"))
                            .name(resultSet.getString("singername"))
                            .singer(resultSet.getBoolean("singer_is_singer"))
                            .author(resultSet.getBoolean("singer_is_author"))
                            .build());
                    authors.add(Musician.builder()
                            .id(resultSet.getLong("authorid"))
                            .name(resultSet.getString("authorname"))
                            .singer(resultSet.getBoolean("author_is_singer"))
                            .author(resultSet.getBoolean("author_is_author"))
                            .build());
                    track = Track.builder()
                            .id(resultSet.getLong("id"))
                            .name(resultSet.getString("trackname"))
                            .genre(Genre.builder()
                                    .id(resultSet.getLong("genreid"))
                                    .title(resultSet.getString("genre"))
                                    .build())
                            .length(resultSet.getLong("length"))
                            .releaseDate(resultSet.getDate("release_date").toLocalDate())
                            .singers(singers)
                            .authors(authors)
                            .build();
                }
                while (resultSet.next());
            }
            return Optional.ofNullable(track);
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

}
