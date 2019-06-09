package by.platonov.music.repository;

import by.platonov.music.exception.RepositoryException;
import by.platonov.music.db.ConnectionPool;
import by.platonov.music.entity.Musician;
import by.platonov.music.entity.Track;
import by.platonov.music.repository.specification.SqlSpecification;
import lombok.extern.log4j.Log4j2;
import org.intellij.lang.annotations.Language;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author dzmitryplatonov on 2019-06-07.
 * @version 0.0.1
 */
@Log4j2
public class TrackRepository implements Repository<Track> {

    @Override
    public boolean add(Track entity) {
        Connection connection = null;
        boolean result = false;
        PreparedStatement statementTrack = null;
        PreparedStatement statementSinger = null;
        PreparedStatement statementAuthor = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            connection.setAutoCommit(false);

            @Language("SQL")
            String insertTrackTable = "insert into track (name, genre_id, release_date, length) values (?, ?, ?, ?);";
            statementTrack = connection.prepareStatement(insertTrackTable);
            statementTrack.setString(1, entity.getName());
            statementTrack.setLong(2, entity.getGenre().getId());
            statementTrack.setTimestamp(3, new Timestamp(entity.getReleaseDate().toEpochDay()));
            statementTrack.setLong(4, entity.getLength());
            log.debug("Statement to track table ready to execute " + statementTrack);
            statementTrack.execute();

            @Language("SQL")
            String insertSingerTrackTable = "insert into singer_track (track_id, singer_id) values (?, ?);";
            statementSinger = connection.prepareStatement(insertSingerTrackTable);
            for (Musician m : entity.getSingers()) {
                statementSinger.setLong(1, entity.getId());
                statementSinger.setLong(2, m.getId());
                log.debug("Statement to singer-track table ready to execute " + statementSinger);
                statementSinger.execute();
            }

            @Language("SQL")
            String insertAuthorTrackTable = "insert into author_track (track_id, author_id) values (?, ?);";
            statementAuthor = connection.prepareStatement(insertAuthorTrackTable);
            for (Musician m : entity.getAuthors()) {
                statementAuthor.setLong(1, entity.getId());
                statementAuthor.setLong(2, m.getId());
                log.debug("Statement to author-track table ready to execute " + statementAuthor);
                statementAuthor.execute();
            }
            connection.commit();
            result = true;
        } catch (InterruptedException | SQLException e) {
            log.error("Transaction failed");
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            Thread.currentThread().interrupt();
        } finally {
            try {
                statementTrack.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                statementSinger.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                statementAuthor.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            ConnectionPool.getInstance().releaseConnection(connection);

        }
        return result;
    }

    @Override
    public boolean remove(Track entity) throws RepositoryException {
        return false;
    }

    @Override
    public boolean update(Track entity) {
        return false;
    }

    @Override
    public List<Track> query(SqlSpecification<Track> specification) {
        return null;
    }

}
