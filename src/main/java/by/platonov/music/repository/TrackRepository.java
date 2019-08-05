package by.platonov.music.repository;

import by.platonov.music.entity.Musician;
import by.platonov.music.exception.RepositoryException;
import by.platonov.music.entity.Track;
import by.platonov.music.repository.mapper.SetTrackFieldsMapper;
import by.platonov.music.repository.mapper.SetTrackIdMapper;
import by.platonov.music.repository.mapper.SetTrackUpdateMapper;
import by.platonov.music.repository.extractor.TrackResultSetExtractor;
import by.platonov.music.repository.specification.SqlSpecification;
import by.platonov.music.repository.specification.TrackIdSpecification;
import lombok.extern.log4j.Log4j2;
import org.intellij.lang.annotations.Language;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * a class that interacts with the database and accumulates in itself all methods to add/update/remove or receive
 * {@link Track} from application db
 * @author dzmitryplatonov on 2019-06-07.
 * @version 0.0.1
 */
@Log4j2
public class TrackRepository implements Repository<Track> {

    @Language("SQL")
    private static final String SQL_SELECT_TRACK =
            "SELECT track.id as id , track.name as name, genre.id as genreId, genre.genre_name, length," +
                    " track.release_date, track.created_at, track.uuid " +
                    "FROM track " +
                    "JOIN genre on genre.id = track.genre_id ";
    @Language("SQL")
    private static final String SQL_COUNT_TRACK = "SELECT COUNT(*) FROM track ";
    @Language("SQL")
    private static final String SQL_INSERT_TRACK =
            "INSERT INTO track(name, genre_id, length, release_date, uuid) VALUES (?, ?, ?, ?, ?) RETURNING ID;";
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
    private static final String SQL_UPDATE_TRACK = "UPDATE track SET name = ?, genre_id = ?, length = ?, release_date = ?," +
            " uuid = ? WHERE id = ?;";

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
                    instance = new TrackRepository(new TransactionHandler(), new JdbcHelper());
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
            if (jdbcHelper.query(connection, SQL_SELECT_TRACK, new TrackIdSpecification(track.getId()),
                    new TrackResultSetExtractor()).isEmpty()) {
                long trackId = jdbcHelper.insert(connection, SQL_INSERT_TRACK, track, new SetTrackFieldsMapper());
                track.setId(trackId);
                for (Musician singer : track.getSingers()) {
                    jdbcHelper.execute(connection, SQL_INSERT_SINGER_LINK, track, (preparedStatement, entity) -> {
                        preparedStatement.setLong(1, track.getId());
                        preparedStatement.setLong(2, singer.getId());
                    });
                }

                for (Musician author : track.getAuthors()) {
                    jdbcHelper.execute(connection, SQL_INSERT_AUTHOR_LINK, track, ((preparedStatement, entity) -> {
                        preparedStatement.setLong(1, track.getId());
                        preparedStatement.setLong(2, author.getId());
                    }));
                }
                log.debug(track + " added successfully");
                return true;
            } else {
                log.debug("Track id: " + track.getId() + " is already exists");
                return false;
            }
        });
    }

    @Override
    public boolean remove(Track track) throws RepositoryException {
        return transactionHandler.transactional(connection -> {
            if (!jdbcHelper.query(connection, SQL_SELECT_TRACK, new TrackIdSpecification(track.getId()),
                    new TrackResultSetExtractor()).isEmpty()) {
                SetTrackIdMapper setTrackIdMapper = new SetTrackIdMapper();
                jdbcHelper.execute(connection, SQL_DELETE_PLAYLIST_LINK, track, setTrackIdMapper);
                jdbcHelper.execute(connection, SQL_DELETE_AUTHOR_LINK, track, setTrackIdMapper);
                jdbcHelper.execute(connection, SQL_DELETE_SINGER_LINK, track, setTrackIdMapper);
                jdbcHelper.execute(connection, SQL_DELETE_TRACK, track, setTrackIdMapper);
                log.debug(track + " removed");
                return true;
            } else {
                log.debug("Track: " + track.getId() + " was not found");
                return false;
            }
        });
    }

    @Override
    public boolean update(Track track) throws RepositoryException {
        return transactionHandler.transactional(connection -> {
            if (!jdbcHelper.query(connection, SQL_SELECT_TRACK, new TrackIdSpecification(track.getId()),
                    new TrackResultSetExtractor()).isEmpty()) {
                jdbcHelper.execute(connection, SQL_DELETE_SINGER_LINK, track, new SetTrackIdMapper());
                for (Musician singer : track.getSingers()) {
                    jdbcHelper.execute(connection, SQL_INSERT_SINGER_LINK, track, (preparedStatement, e) -> {
                            preparedStatement.setLong(1, track.getId());
                            preparedStatement.setLong(2, singer.getId());
                    });
                }
                jdbcHelper.execute(connection, SQL_DELETE_AUTHOR_LINK, track, new SetTrackIdMapper());
                for (Musician author : track.getAuthors()) {
                    jdbcHelper.execute(connection, SQL_INSERT_AUTHOR_LINK, track, (preparedStatement, e) -> {
                        preparedStatement.setLong(1, track.getId());
                        preparedStatement.setLong(2, author.getId());
                    });
                }
                jdbcHelper.execute(connection, SQL_UPDATE_TRACK, track, new SetTrackUpdateMapper());
                log.debug(track + " updated");
                return true;
            } else {
                log.debug("Track number: " + track.getId() + " was not found");
                return false;
            }
        });
    }

    @Override
    public List<Track> query(SqlSpecification specification) throws RepositoryException {
        return transactionHandler.transactional(connection ->
                jdbcHelper.query(connection, SQL_SELECT_TRACK, specification, new TrackResultSetExtractor()));
    }

    @Override
    public long count(SqlSpecification specification) throws RepositoryException {
        return transactionHandler.transactional(connection ->
                jdbcHelper.count(connection, SQL_COUNT_TRACK, specification));
    }
}
