package by.platonov.music.repository;

import by.platonov.music.entity.Playlist;
import by.platonov.music.entity.Track;
import by.platonov.music.exception.RepositoryException;
import by.platonov.music.repository.mapper.PreparedStatementMapper;
import by.platonov.music.repository.mapper.SetPlaylistFieldsMapper;
import by.platonov.music.repository.mapper.SetPlaylistIdMapper;
import by.platonov.music.repository.mapper.SetPlaylistUpdateMapper;
import by.platonov.music.repository.extractor.PlaylistResultSetExtractor;
import by.platonov.music.repository.specification.PlaylistIdSpecification;
import by.platonov.music.repository.specification.SqlSpecification;
import lombok.extern.log4j.Log4j2;
import org.intellij.lang.annotations.Language;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * a class that interacts with the database and accumulates in itself all methods to add/update/remove or receive
 * {@link Playlist} from database
 * @author dzmitryplatonov on 2019-06-12.
 * @version 0.0.1
 */
@Log4j2
public class PlaylistRepository implements Repository<Playlist> {

    @Language("SQL")
    private static final String SQL_INSERT_PLAYLIST = "INSERT INTO playlist (name, private) VALUES (?, ?) RETURNING ID;";
    @Language("SQL")
    private static final String SQL_INSERT_TRACK_PLAYLIST_LINK = "INSERT INTO playlist_track (playlist_id, track_id) VALUES (?, ?);";
    @Language("SQL")
    private static final String SQL_DELETE_PLAYLIST = "DELETE FROM playlist WHERE id = ?";
    @Language("SQL")
    private static final String SQL_DELETE_PLAYLIST_TRACK_LINK = "DELETE FROM playlist_track WHERE playlist_id = ?";
    @Language("SQL")
    private static final String SQL_DELETE_PLAYLIST_USER_LINK = "DELETE FROM user_playlist WHERE playlist_id = ?";
    @Language("SQL")
    private static final String SQL_UPDATE_PLAYLIST = "UPDATE playlist SET name = ?, private = ? WHERE id = ?;";

    @Language("SQL")
    private static final String SQL_SELECT_PLAYLIST = "SELECT playlist.id as id, playlist.name as name, playlist.private " +
            "FROM playlist LEFT JOIN playlist_track pt on playlist.id = pt.playlist_id ";
    @Language("SQL")
    private static final String SQL_COUNT = "SELECT COUNT(*) FROM playlist ";


    private static PlaylistRepository instance;
    private static ReentrantLock lock = new ReentrantLock();
    private static AtomicBoolean create = new AtomicBoolean(false);

    private TransactionHandler transactionHandler;
    private JdbcHelper jdbcHelper;

    private PlaylistRepository(TransactionHandler transactionHandler, JdbcHelper jdbcHelper) {
        this.transactionHandler = transactionHandler;
        this.jdbcHelper = jdbcHelper;
    }

    public static PlaylistRepository getInstance() {
        if (!create.get()) {
            lock.lock();
            try {
                if (instance == null) {
                    instance = new PlaylistRepository(new TransactionHandler(), new JdbcHelper());
                    create.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    @Override
    public boolean add(Playlist playlist) throws RepositoryException {
        return transactionHandler.transactional(connection -> {
            if (jdbcHelper.query(connection, SQL_SELECT_PLAYLIST, new PlaylistIdSpecification(playlist.getId()),
                    new PlaylistResultSetExtractor()).isEmpty()) {
                long playlistId = jdbcHelper.insert(connection, SQL_INSERT_PLAYLIST, playlist, new SetPlaylistFieldsMapper());
                playlist.setId(playlistId);
                for (Track track : playlist.getTracks()) {
                    jdbcHelper.execute(connection, SQL_INSERT_TRACK_PLAYLIST_LINK, playlist.getId(), ((preparedStatement, entity) -> {
                        preparedStatement.setLong(1, playlist.getId());
                        preparedStatement.setLong(2, track.getId());
                    }));
                }
                log.debug(playlist + " added successfully");
                return true;
            } else {
                log.debug("Track id: " + playlist.getId() + " is already exists");
                return false;
            }
        });
    }

    @Override
    public boolean remove(Playlist playlist) throws RepositoryException {
        return transactionHandler.transactional(connection -> {
            if (!jdbcHelper.query(connection, SQL_SELECT_PLAYLIST, new PlaylistIdSpecification(playlist.getId()),
                    new PlaylistResultSetExtractor()).isEmpty()) {
                PreparedStatementMapper<Playlist> mapper = new SetPlaylistIdMapper();
                jdbcHelper.execute(connection, SQL_DELETE_PLAYLIST_TRACK_LINK, playlist, mapper);
                jdbcHelper.execute(connection, SQL_DELETE_PLAYLIST_USER_LINK, playlist, mapper);
                jdbcHelper.execute(connection, SQL_DELETE_PLAYLIST, playlist, mapper);
                log.debug(playlist + " removed");
                return true;
            } else {
                log.debug("Track: " + playlist.getId() + " was not found");
                return false;
            }
        });
    }

    @Override
    public boolean update(Playlist playlist) throws RepositoryException {
        return transactionHandler.transactional(connection -> {
            if (!jdbcHelper.query(connection, SQL_SELECT_PLAYLIST, new PlaylistIdSpecification(playlist.getId()),
                    new PlaylistResultSetExtractor()).isEmpty()) {
                jdbcHelper.execute(connection, SQL_DELETE_PLAYLIST_TRACK_LINK, playlist, new SetPlaylistIdMapper());
                for (Track track : playlist.getTracks()) {
                    jdbcHelper.execute(connection, SQL_INSERT_TRACK_PLAYLIST_LINK, playlist.getId(), ((preparedStatement, entity) -> {
                        preparedStatement.setLong(1, playlist.getId());
                        preparedStatement.setLong(2, track.getId());
                    }));
                }
                log.debug(playlist + " updated");
                jdbcHelper.execute(connection, SQL_UPDATE_PLAYLIST, playlist, new SetPlaylistUpdateMapper());
                return true;
            } else {
                log.debug("Track number: " + playlist.getId() + " was not found");
                return false;
            }
        });
    }

    @Override
    public List<Playlist> query(SqlSpecification specification) throws RepositoryException {
        return transactionHandler.transactional(connection ->
                jdbcHelper.query(connection, SQL_SELECT_PLAYLIST, specification, new PlaylistResultSetExtractor()));
    }

    @Override
    public long count(SqlSpecification specification) throws RepositoryException {
        return transactionHandler.transactional(connection ->
                jdbcHelper.count(connection, SQL_COUNT, specification));
    }
}
