package by.platonov.music.repository;

import by.platonov.music.entity.Musician;
import by.platonov.music.exception.RepositoryException;
import by.platonov.music.entity.Track;
import by.platonov.music.repository.jdbchelper.JdbcHelper;
import by.platonov.music.repository.mapper.preparedStatement.PreparedStatementMapper;
import by.platonov.music.repository.mapper.preparedStatement.SetTrackFieldsMapper;
import by.platonov.music.repository.mapper.preparedStatement.SetTrackIdMapper;
import by.platonov.music.repository.mapper.resultSet.TrackRowMapper;
import by.platonov.music.repository.specification.SqlSpecification;
import by.platonov.music.repository.specification.TrackIdSpecification;
import lombok.extern.log4j.Log4j2;
import org.intellij.lang.annotations.Language;

import java.sql.*;
import java.sql.Date;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author dzmitryplatonov on 2019-06-07.
 * @version 0.0.1
 */
@Log4j2
public class TrackRepository implements Repository<Track> {

    @Language("SQL")
    private static final String SQL_SELECT_TRACK =
            "SELECT track.id , track.name, genre.id as genreId, genre.name as genre, track.length, track.release_date " +
                    "FROM track " +
                    "JOIN genre on genre.id = track.genre_id ";
    @Language("SQL")
    private static final String SQL_COUNT_TRACK = "SELECT COUNT(*) FROM track ";
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

    private static TrackRepository instance;
    private static ReentrantLock lock = new ReentrantLock();
    private static AtomicBoolean create = new AtomicBoolean(false);

    private TransactionHandler transactionHandler;
    private JdbcHelper jdbcHelper;

    private TrackRepository(TransactionHandler transactionHandler, JdbcHelper jdbcHelper) {
        this.transactionHandler = transactionHandler;
        this.jdbcHelper = jdbcHelper;
    }

    public static TrackRepository getInstance() {
        if (!create.get()) {
            lock.lock();
            try {
                if (instance == null) {
                    instance = new TrackRepository(TransactionHandler.getInstance(), new JdbcHelper());
                    create.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    @Override
    public boolean add(Track track) throws RepositoryException {
        return transactionHandler.transactional(connection -> {
            if (jdbcHelper.query(connection, SQL_SELECT_TRACK + new TrackIdSpecification(track.getId()).toSqlClauses(),
                    new TrackRowMapper()).isEmpty()) {
                long trackId = addTrackNonTransactional(connection, track);
                addSingerLinkNonTransactional(connection, track, trackId);
                addAuthorLinkNonTransactional(connection, track, trackId);
                return true;
            } else {
                log.debug("Track id: " + track.getId() + " is already exists");
                return false;
            }
        });
    }

    private long addTrackNonTransactional(Connection connection, Track track) throws RepositoryException {
        try (PreparedStatement statementTrack = connection.prepareStatement(SQL_INSERT_TRACK)) {
            PreparedStatementMapper<Track> mapper = new SetTrackFieldsMapper();
            mapper.map(statementTrack, track);
//            statementTrack.setString(1, track.getName());
//            statementTrack.setLong(2, track.getGenre().getId());
//            statementTrack.setLong(3, track.getLength());
//            statementTrack.setDate(4, Date.valueOf(track.getReleaseDate()));
            try(ResultSet resultSet = statementTrack.executeQuery()) {
                resultSet.next();
                long trackId = resultSet.getLong(1);
                log.debug("Track: " + trackId + " added");
                return trackId;
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    private void addSingerLinkNonTransactional(Connection connection, Track track, long trackId) throws RepositoryException {
        try (PreparedStatement statementSingerLink = connection.prepareStatement(SQL_INSERT_SINGER_LINK)) {
            for (Musician singer : track.getSingers()) {
                statementSingerLink.setLong(1, trackId);
                statementSingerLink.setLong(2, singer.getId());
                statementSingerLink.execute();
                log.debug("Track link to " + singer + " connected");
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    private void addAuthorLinkNonTransactional(Connection connection, Track track, long trackId) throws RepositoryException {
        try (PreparedStatement statementAuthorLink = connection.prepareStatement(SQL_INSERT_AUTHOR_LINK)) {
            for (Musician author : track.getAuthors()) {
                statementAuthorLink.setLong(1, trackId);
                statementAuthorLink.setLong(2, author.getId());
                statementAuthorLink.execute();
                log.debug("Track link to " + author + " connected");
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public boolean remove(Track track) throws RepositoryException {
        return transactionHandler.transactional(connection -> {
            if (!jdbcHelper.query(connection, SQL_SELECT_TRACK + new TrackIdSpecification(track.getId()).toSqlClauses(),
                    new TrackRowMapper()).isEmpty()) {
                deletePlaylistLinkNonTransactional(connection, track);
                deleteAuthorLinkNonTransactional(connection, track);
                deleteSingerLinkNonTransactional(connection, track);
                deleteTrackNonTransactional(connection, track);
                return true;
            } else {
                log.debug("Track: " + track.getId() + " was not found");
                return false;
            }
        });
    }

    private void deleteTrackNonTransactional(Connection connection, Track track) throws RepositoryException {
        try (PreparedStatement statementTrack = connection.prepareStatement(SQL_DELETE_TRACK)) {
            statementTrack.setLong(1, track.getId());
            statementTrack.execute();
            log.debug(track + " was removed");
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    private void deleteSingerLinkNonTransactional(Connection connection, Track track) throws RepositoryException {
        try (PreparedStatement statementSingerLink = connection.prepareStatement(SQL_DELETE_SINGER_LINK)) {
            statementSingerLink.setLong(1, track.getId());
            statementSingerLink.execute();
            log.debug("Singer links was removed");
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    private void deleteAuthorLinkNonTransactional(Connection connection, Track track) throws RepositoryException {
        try (PreparedStatement statementAuthorLink = connection.prepareStatement(SQL_DELETE_AUTHOR_LINK)) {
            statementAuthorLink.setLong(1, track.getId());
            statementAuthorLink.execute();
            log.debug("Author links was removed");
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    private void deletePlaylistLinkNonTransactional(Connection connection, Track track) throws RepositoryException {
        try (PreparedStatement statementPlaylistLink = connection.prepareStatement(SQL_DELETE_PLAYLIST_LINK)) {
            statementPlaylistLink.setLong(1, track.getId());
            statementPlaylistLink.execute();
            log.debug("Playlist links was removed");
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public boolean update(Track track) throws RepositoryException {
        return transactionHandler.transactional(connection -> {
            if (!jdbcHelper.query(connection, SQL_SELECT_TRACK + new TrackIdSpecification(track.getId()).toSqlClauses(),
                    new TrackRowMapper()).isEmpty()) {
                jdbcHelper.execute(connection, SQL_DELETE_SINGER_LINK, track, new SetTrackIdMapper());
                for (Musician singer : track.getSingers()) {
                    jdbcHelper.execute(connection, SQL_INSERT_SINGER_LINK, track, (preparedStatement, e) -> {
                            preparedStatement.setLong(1, track.getId());
                            preparedStatement.setLong(2, singer.getId());
                    });
                }
                
                return true;
//                deleteSingerLinkNonTransactional(connection, track);
//                addSingerLinkNonTransactional(connection, track, track.getId());
//                deleteAuthorLinkNonTransactional(connection, track);
//                addAuthorLinkNonTransactional(connection, track, track.getId());
//                return updateTrackNonTransactional(connection, track);
            } else {
                log.debug("Track number: " + track.getId() + " was not found");
                return false;
            }
        });
    }

//    @Override
//    public boolean update(Track track) throws RepositoryException {
//        return transactionHandler.transactional(connection -> {
//            if (!queryNonTransactional(connection, new TrackIdSpecification(track.getId())).isEmpty()) {
//                deleteSingerLinkNonTransactional(connection, track);
//                addSingerLinkNonTransactional(connection, track, track.getId());
//                deleteAuthorLinkNonTransactional(connection, track);
//                addAuthorLinkNonTransactional(connection, track, track.getId());
//                return updateTrackNonTransactional(connection, track);
//            } else {
//                log.debug("Track number: " + track.getId() + " was not found");
//                return false;
//            }
//        });
//    }

    private boolean updateTrackNonTransactional(Connection connection, Track track) throws RepositoryException {
        try (PreparedStatement statementUpdateTrack = connection.prepareStatement(SQL_UPDATE_TRACK)) {
            statementUpdateTrack.setString(1, track.getName());
            statementUpdateTrack.setLong(2, track.getGenre().getId());
            statementUpdateTrack.setLong(3, track.getLength());
            statementUpdateTrack.setDate(4, Date.valueOf(track.getReleaseDate()));
            statementUpdateTrack.setLong(5, track.getId());
            log.debug(track + " updated");
            return statementUpdateTrack.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }
    @Override
    public List<Track> query(SqlSpecification specification) throws RepositoryException {
        return transactionHandler.transactional(connection ->
                jdbcHelper.query(connection, SQL_SELECT_TRACK + specification.toSqlClauses(), new TrackRowMapper()));
    }

//    private List<Track> queryNonTransactional(Connection connection, SqlSpecification specification) throws RepositoryException {
//        List<Track> tracks = new ArrayList<>();
//        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_TRACK + specification.toSqlClauses());
//             ResultSet resultSet = statement.executeQuery()) {
//            Map<Long, Track> table = new HashMap<>();
//            AbstractRowMapper<Track> mapper = new TrackRowMapper();
//            while (resultSet.next()) {
//                if (!table.containsKey(resultSet.getLong("id"))) {
//                    Track track = mapper.map(resultSet);
//                    table.put(track.getId(), track);
//                }
////                    else {
////                        Musician singer = mapper.mapSinger(resultSet);
////                        Musician author = mapper.mapAuthor(resultSet);
////                        table.get(resultSet.getLong("id")).getSingers().add(singer);
////                        table.get(resultSet.getLong("id")).getAuthors().add(author);
////                    }
//            }
//            table.forEach((id, track) -> tracks.add(track));
//
//        } catch (SQLException e) {
//            throw new RepositoryException(e);
//        }
//        return tracks;
//    }


    @Override
    public long count(SqlSpecification specification) throws RepositoryException {
        return transactionHandler.transactional(connection ->
                jdbcHelper.count(connection, SQL_COUNT_TRACK +  specification.toSqlClauses()));
    }
}
