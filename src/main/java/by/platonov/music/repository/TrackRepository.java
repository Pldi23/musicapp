package by.platonov.music.repository;

import by.platonov.music.db.ConnectionPool;
import by.platonov.music.entity.Track;
import lombok.Cleanup;
import org.intellij.lang.annotations.Language;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author dzmitryplatonov on 2019-06-07.
 * @version 0.0.1
 */
public class TrackRepository implements Repository<Track> {

    @Override
    public boolean add(Track entity) {
        Connection connection = null;
        boolean result = false;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            connection.setAutoCommit(false);
            @Language("SQL")
            String insertTrackTable = String
                    .format("insert into track (name, genre_id, release_date, length) values (%s, %d, %s, %d)",
                            entity.getName(), entity.getGenre().getId(), entity.getReleaseDate(), entity.getLength());
            String insertSingerTrackTable = String
                    .format("insert into singer_track (track_id, singer_id) values %s",
                            entity.getSingers().stream()
                                    .map(s -> String.format("(%s, %s)", entity.getId(), s.getId()))
                                    .collect(Collectors.joining(",")));

            preparedStatement = connection.prepareStatement(insertTrackTable);
            ConnectionPool.getInstance().releaseConnection(connection);
            result = preparedStatement.execute();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    public boolean remove(Track entity) {
        return false;
    }

    @Override
    public boolean update(Track entity) {
        return false;
    }

    @Override
    public List<Track> query(Specification<Track> specification) {
        return null;
    }
}
