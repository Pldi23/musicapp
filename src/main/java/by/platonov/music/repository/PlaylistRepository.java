package by.platonov.music.repository;

import by.platonov.music.entity.Playlist;
import by.platonov.music.entity.Track;
import by.platonov.music.exception.RepositoryException;
import by.platonov.music.repository.specification.SelectIdSpecification;
import org.intellij.lang.annotations.Language;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * @author dzmitryplatonov on 2019-06-11.
 * @version 0.0.1
 */
public class PlaylistRepository extends AbstractRepository<Playlist> {

    @Language("SQL")
    private static final String SQL_SELECT =
            "select playlist.id playlistid, playlist.name playlistname, playlist_track.track_id trackid, " +
                    "track.name trackname, genre.id as genreid, genre.name as genre, track.length, track.release_date, " +
                    "singer.id singerId, singer.name singerName, " +
                    "author.id authorId, author.name authorName " +
                    "from playlist " +
                    "left join playlist_track on playlist_track.playlist_id = playlist.id " +
                    "left join track on track.id = playlist_track.track_id " +
                    "left join genre on track.genre_id = genre.id " +
                    "left join singer_track on track.id = singer_track.track_id " +
                    "left join author_track on track.id = author_track.track_id " +
                    "left join musician singer on singer_track.singer_id = singer.id " +
                    "left join musician author on author_track.author_id = author.id where ";

    @Language("SQL")
    private static final String SQL_SELECT_SPEC = SQL_SELECT + "playlist.";
    @Language("SQL")
    private static final String SQL_COUNT = "SELECT COUNT(*) FROM playlist WHERE ";
    @Language("SQL")
    private static final String SQL_INSERT_PLAYLIST = "INSERT INTO playlist (name) VALUES (?);";
    @Language("SQL")
    private static final String SQL_INSERT_TRACK_PLAYLIST_LINK = "INSERT INTO playlist_track (playlist_id, track_id) VALUES (?, ?);";

    @Override
    public String buildFindOneSqlRequest() {
        return SQL_SELECT_SPEC;
    }

    @Override
    public String buildCountSqlRequest() {
        return SQL_COUNT;
    }

    @Override
    public String buildQuerySqlRequest() {
        return SQL_SELECT;
    }

    @Override
    public List<PreparedStatement> insertStatements(Connection connection, Playlist playlist) throws SQLException {
        List<PreparedStatement> statements = new ArrayList<>();
        PreparedStatement statementInsertPlaylist = connection.prepareStatement(SQL_INSERT_PLAYLIST);
        statementInsertPlaylist.setString(1, playlist.getName());
        statements.add(statementInsertPlaylist);
        for (Track track : playlist.getTracks()) {
            PreparedStatement statementInsertTrackLink = connection.prepareStatement(SQL_INSERT_TRACK_PLAYLIST_LINK);
            statementInsertTrackLink.setLong(1, playlist.getId());
            statementInsertTrackLink.setLong(2, track.getId());
            statements.add(statementInsertTrackLink);
        }
        return statements;
    }

    @Override
    public List<PreparedStatement> removeStatements(Connection connection, Playlist playlist) throws SQLException {
        return null;
    }

    @Override
    public List<PreparedStatement> updateStatements(Connection connection, Playlist playlist) throws SQLException {
        return null;
    }

    @Override
    public boolean isPresent(Connection connection, Playlist playlist) throws RepositoryException {
        return findOneNonTransactional(connection, new SelectIdSpecification(playlist.getId())).isPresent();
    }

    @Override
    public List<Playlist> buildEntityList(ResultSet resultSet) throws SQLException {
        List<Playlist> result = new ArrayList<>();
        Map<Long, Playlist> table = new HashMap<>();
        while (resultSet.next()) {
            if (!table.containsKey(resultSet.getLong("playlistid"))) {
                Playlist playlist = buildPlaylist(resultSet);
                table.put(playlist.getId(), playlist);
            } else {
                Track track = buildTrack(resultSet);
                table.get(resultSet.getLong("playlistid")).getTracks().add(track);
            }
        }
        table.forEach((l, p) -> result.add(p));
        return result;
    }

    @Override
    public Playlist buildEntity(ResultSet resultSet) throws SQLException {
        return buildPlaylist(resultSet);
    }
}
