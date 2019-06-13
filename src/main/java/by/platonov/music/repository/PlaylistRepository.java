package by.platonov.music.repository;

import by.platonov.music.entity.Playlist;
import by.platonov.music.entity.Track;
import by.platonov.music.exception.RepositoryException;
import by.platonov.music.repository.mapper.AbstractRowMapper;
import by.platonov.music.repository.mapper.PlaylistRowMapper;
import by.platonov.music.repository.specification.PlaylistIdSpecification;
import by.platonov.music.repository.specification.SqlSpecification;
import lombok.extern.log4j.Log4j2;
import org.intellij.lang.annotations.Language;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author dzmitryplatonov on 2019-06-12.
 * @version 0.0.1
 */
@Log4j2
public class PlaylistRepository implements Repository<Playlist> {

    @Language("SQL")
    private static final String SQL_INSERT_PLAYLIST = "INSERT INTO playlist (name) VALUES (?) RETURNING ID;";
    @Language("SQL")
    private static final String SQL_INSERT_TRACK_PLAYLIST_LINK = "INSERT INTO playlist_track (playlist_id, track_id) VALUES (?, ?);";
    @Language("SQL")
    private static final String SQL_DELETE_PLAYLIST = "DELETE FROM playlist WHERE id = ?";
    @Language("SQL")
    private static final String SQL_DELETE_PLAYLIST_TRACK_LINK = "DELETE FROM playlist_track WHERE playlist_id = ?";
    @Language("SQL")
    private static final String SQL_DELETE_PLAYLIST_USER_LINK = "DELETE FROM user_playlist WHERE playlist_id = ?";
    @Language("SQL")
    private static final String SQL_UPDATE_PLAYLIST = "UPDATE playlist SET name = ? WHERE id = ?;";
    @Language("SQL")
    private static final String SQL_SELECT_PLAYLIST = "SELECT id, name FROM playlist ";
    @Language("SQL")
    private static final String SQL_COUNT = "SELECT COUNT(*) FROM playlist ";


    private static PlaylistRepository instance;
    private static ReentrantLock lock = new ReentrantLock();
    private static AtomicBoolean create = new AtomicBoolean(false);

    private PlaylistRepository() {
    }

    public static PlaylistRepository getInstance() {
        if (!create.get()) {
            lock.lock();
            try {
                if (instance == null) {
                    instance = new PlaylistRepository();
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
        return TransactionHandler.getInstance().transactional(connection -> {
            if (queryNonTransactional(connection, new PlaylistIdSpecification(playlist.getId())).isEmpty()) {
               long playlistId = addPlaylistNonTransactional(connection, playlist);
               addLinkNonTransactional(connection, playlist, playlistId);
               return true;
            } else {
                log.debug("Track id: " + playlist.getId() + " is already exists");
                return false;
            }
        });
    }

    private long addPlaylistNonTransactional(Connection connection, Playlist playlist) throws RepositoryException {
        try (PreparedStatement statementPlaylist = connection.prepareStatement(SQL_INSERT_PLAYLIST)) {
            statementPlaylist.setString(1, playlist.getName());
            try(ResultSet resultSet = statementPlaylist.executeQuery()) {
                resultSet.next();
                long playlistId = resultSet.getLong(1);
                log.debug("Track: " + playlistId + " added");
                return playlistId;
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    private void addLinkNonTransactional(Connection connection, Playlist playlist, long playlistId) throws RepositoryException {
        try (PreparedStatement statementDeletePlaylistLink = connection.prepareStatement(SQL_INSERT_TRACK_PLAYLIST_LINK)) {
            for (Track track : playlist.getTracks()) {
                statementDeletePlaylistLink.setLong(1, playlistId);
                statementDeletePlaylistLink.setLong(2, track.getId());
                statementDeletePlaylistLink.execute();
                log.debug("Playlist link to track added");
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public boolean remove(Playlist playlist) throws RepositoryException {
        return TransactionHandler.getInstance().transactional(connection -> {
            if (!queryNonTransactional(connection, new PlaylistIdSpecification(playlist.getId())).isEmpty()) {
                removePlaylistTrackLinkNonTransactional(connection, playlist);
                removeUserPlaylistLinkNonTransactional(connection, playlist);
                return removePlaylistNonTransactional(connection, playlist);
            } else {
                log.debug("Track: " + playlist.getId() + " was not found");
                return false;
            }
        });
    }

    private boolean removePlaylistNonTransactional(Connection connection, Playlist playlist) throws RepositoryException {
        try (PreparedStatement statementPlaylist = connection.prepareStatement(SQL_DELETE_PLAYLIST)) {
            statementPlaylist.setLong(1, playlist.getId());
            statementPlaylist.execute();
            log.debug(playlist + " was removed");
            return true;
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    private void removePlaylistTrackLinkNonTransactional(Connection connection, Playlist playlist) throws RepositoryException {
        try (PreparedStatement statementPlaylistTrackLink = connection.prepareStatement(SQL_DELETE_PLAYLIST_TRACK_LINK)) {
            statementPlaylistTrackLink.setLong(1, playlist.getId());
            statementPlaylistTrackLink.execute();
            log.debug(playlist + "links was removed");
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    private void removeUserPlaylistLinkNonTransactional(Connection connection, Playlist playlist) throws RepositoryException {
        try (PreparedStatement statementPlaylistUserLink = connection.prepareStatement(SQL_DELETE_PLAYLIST_USER_LINK)) {
            statementPlaylistUserLink.setLong(1, playlist.getId());
            statementPlaylistUserLink.execute();
            log.debug(playlist + "links was removed");
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public boolean update(Playlist playlist) throws RepositoryException {
        return TransactionHandler.getInstance().transactional(connection -> {
            if (!queryNonTransactional(connection, new PlaylistIdSpecification(playlist.getId())).isEmpty()) {
                removePlaylistTrackLinkNonTransactional(connection, playlist);
                addLinkNonTransactional(connection, playlist, playlist.getId());
                return updatePlaylistNonTransactional(connection, playlist);
            } else {
                log.debug("Track number: " + playlist.getId() + " was not found");
                return false;
            }
        });
    }

    private boolean updatePlaylistNonTransactional(Connection connection, Playlist playlist) throws RepositoryException {
        try (PreparedStatement statementUpdatePlaylist = connection.prepareStatement(SQL_UPDATE_PLAYLIST)) {
            statementUpdatePlaylist.setString(1, playlist.getName());
            statementUpdatePlaylist.setLong(2, playlist.getId());
            log.debug(playlist + " updated");
            return statementUpdatePlaylist.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public List<Playlist> query(SqlSpecification specification) throws RepositoryException {
        return TransactionHandler.getInstance().transactional(connection -> queryNonTransactional(connection, specification));
    }

    private List<Playlist> queryNonTransactional(Connection connection, SqlSpecification specification) throws RepositoryException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_PLAYLIST + specification.toSqlClauses());
             ResultSet resultSet = statement.executeQuery()) {
            List<Playlist> result = new ArrayList<>();
            Map<Long, Playlist> table = new HashMap<>();
            AbstractRowMapper<Playlist> mapper = new PlaylistRowMapper();
            while (resultSet.next()) {
                if (!table.containsKey(resultSet.getLong("id"))) {
                    Playlist playlist = mapper.map(resultSet);
                    table.put(playlist.getId(), playlist);
                }
//                    else {
//                        Track track = mapper.mapTrack(resultSet);
//                        table.get(resultSet.getLong("playlistid")).getTracks().add(track);
//                    }
            }
            table.forEach((l, p) -> result.add(p));
            return result;
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }


    @Override
    public int count(SqlSpecification specification) throws RepositoryException {
        return TransactionHandler.getInstance().transactional(connection -> {
            try (PreparedStatement statement = connection.prepareStatement(SQL_COUNT + specification.toSqlClauses());
                 ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                return resultSet.getInt(1);
            } catch (SQLException e) {
                throw new RepositoryException(e);
            }
        });
    }
}
