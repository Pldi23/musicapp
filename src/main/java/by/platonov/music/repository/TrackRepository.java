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
import java.sql.Date;
import java.util.*;

/**
 * @author dzmitryplatonov on 2019-06-07.
 * @version 0.0.1
 */
@Log4j2
public class TrackRepository implements Repository<Track> {

    @Language("SQL")
    private static final String SQL_SELECT_TRACK =
            "select track.id, track.name as trackName, genre.id as genreId, genre.name as genre, track.length, track.release_date, " +
                    "singer.id singerId, singer.name singerName, " +
                    "author.id authorId, author.name authorName " +
                    "from track " +
                    "join genre on track.genre_id = genre.id " +
                    "join singer_track on track.id = singer_track.track_id " +
                    "join author_track on track.id = author_track.track_id " +
                    "join musician singer on singer_track.singer_id = singer.id " +
                    "join musician author on author_track.author_id = author.id WHERE ";

    @Language("SQL")
    private static final String SQL_SELECT_TRACK_SPECIFY = SQL_SELECT_TRACK + "track.";
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
                    for (Musician singer : track.getSingers()) {
                        statementSingerLink.setLong(1, trackId);
                        statementSingerLink.setLong(2, singer.getId());
                        statementSingerLink.execute();
                    }
                    for (Musician author : track.getAuthors()) {
                        statementAuthorLink.setLong(1, trackId);
                        statementAuthorLink.setLong(2, author.getId());
                        statementAuthorLink.execute();
                    }
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
                try (PreparedStatement statementTrack = connection.prepareStatement(SQL_DELETE_TRACK);
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
                try (PreparedStatement statementUpdateTrack = connection.prepareStatement(SQL_UPDATE_TRACK);
                     PreparedStatement statementUpdateSingerLink = connection.prepareStatement(SQL_UPDATE_TRACK_SINGER_LINK);
                     PreparedStatement statementUpdateAuthorLink = connection.prepareStatement(SQL_UPDATE_TRACK_AUTHOR_LINK)) {
                    statementUpdateTrack.setString(1, track.getName());
                    statementUpdateTrack.setLong(2, track.getGenre().getId());
                    statementUpdateTrack.setLong(3, track.getLength());
                    statementUpdateTrack.setDate(4, Date.valueOf(track.getReleaseDate()));
                    statementUpdateTrack.setLong(5, track.getId());
                    for (Musician musician : track.getSingers()) {
                        statementUpdateSingerLink.setLong(1, musician.getId());
                        statementUpdateSingerLink.setLong(2, track.getId());
                        statementUpdateSingerLink.executeUpdate();
                    }
                    for (Musician musician : track.getAuthors()) {
                        statementUpdateAuthorLink.setLong(1, musician.getId());
                        statementUpdateAuthorLink.setLong(2, track.getId());
                        statementUpdateAuthorLink.executeUpdate();
                    }
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
        return TransactionHandler.transactional(connection -> {
            List<Track> tracks = new ArrayList<>();
            try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_TRACK + specification.toSqlClauses());
                 ResultSet resultSet = statement.executeQuery()) {
                Map<Long, Track> table = new HashMap<>();
                while (resultSet.next()) {
                    if (!table.containsKey(resultSet.getLong("id"))) {
                        Track track = buildTrack(resultSet);
                        table.put(track.getId(), track);
                    } else {
                        Musician singer = buildSinger(resultSet);
                        Musician author = buildAuthor(resultSet);
                        table.get(resultSet.getLong("id")).getSingers().add(singer);
                        table.get(resultSet.getLong("id")).getAuthors().add(author);
                    }
                }
                table.forEach((id, track) -> tracks.add(track));

            } catch (SQLException e) {
                throw new RepositoryException(e);
            }
            return tracks;
        });
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
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_TRACK_SPECIFY + specification.toSqlClauses());
             ResultSet resultSet = statement.executeQuery()) {
            Track track = null;
            if (resultSet.next()) {
                track = buildTrack(resultSet);
                while (resultSet.next()) {
                    Musician singer = buildSinger(resultSet);
                    Musician author = buildAuthor(resultSet);
                    track.getSingers().add(singer);
                    track.getAuthors().add(author);
                }
            }
            return Optional.ofNullable(track);
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    private Track buildTrack(ResultSet resultSet) throws SQLException {
        Set<Musician> singers = new HashSet<>();
        Set<Musician> authors = new HashSet<>();
        Track track = Track.builder()
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
        Musician singer = buildSinger(resultSet);
        singers.add(singer);
        Musician author = buildAuthor(resultSet);
        authors.add(author);
        return track;
    }

    private Musician buildSinger(ResultSet resultSet) throws SQLException {
        return Musician.builder()
                .id(resultSet.getLong("singerid"))
                .name(resultSet.getString("singername"))
                .build();
    }

    private Musician buildAuthor(ResultSet resultSet) throws SQLException {
        return Musician.builder()
                .id(resultSet.getLong("authorid"))
                .name(resultSet.getString("authorname"))
                .build();
    }
}
