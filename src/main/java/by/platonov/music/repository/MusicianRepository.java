package by.platonov.music.repository;

import by.platonov.music.exception.RepositoryException;
import by.platonov.music.db.ConnectionPool;
import by.platonov.music.db.DatabaseConfiguration;
import by.platonov.music.entity.Musician;
import by.platonov.music.repository.specification.SqlSpecification;
import lombok.extern.log4j.Log4j2;
import org.intellij.lang.annotations.Language;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author dzmitryplatonov on 2019-06-09.
 * @version 0.0.1
 */
@Log4j2
public class MusicianRepository implements Repository<Musician> {

    @Language("SQL")
    private static final String SQL_INSERT_MUSICIAN = "insert into musician (name, is_singer, is_author) values (?, ?, ?);";
    @Language("SQL")
    private static final String SQL_COUNT_MISICIAN = "select count(*) from musician where id is not null;";

    private Connection takeConnection() throws RepositoryException {
        ConnectionPool pool = ConnectionPool.getInstance();
        try {
            return pool.getConnection();
        } catch (InterruptedException e) {
            log.error("Connection interrupted", e);
            Thread.currentThread().interrupt();
            throw new RepositoryException(e);
        }
    }

    private void returnConnection(Connection connection) {
        ConnectionPool.getInstance().releaseConnection(connection);

    }

    @Override
    public boolean add(Musician entity) throws RepositoryException {
        boolean result;
        Connection connection = takeConnection();
        try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT_MUSICIAN)) {
            statement.setString(1, entity.getName());
            statement.setBoolean(2, entity.isSinger());
            statement.setBoolean(3, entity.isAuthor());
            statement.execute();
            result = true;
            log.debug(entity + " added to " + DatabaseConfiguration.getInstance().getDbName() + " database");
        } catch (SQLException e) {
            throw new RepositoryException(e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);

        }
        return result;
    }

    @Override
    public boolean remove(Musician entity) throws RepositoryException {
        return false;
    }

    @Override
    public boolean update(Musician entity) throws RepositoryException {
        return false;
    }

    @Override
    public List<Musician> query(SqlSpecification<Musician> specification) throws RepositoryException {
        return null;
    }

    public int size() throws RepositoryException {
        int result;
        Connection connection = takeConnection();
        try (PreparedStatement statement = connection.prepareStatement(SQL_COUNT_MISICIAN);
             ResultSet resultSet = statement.executeQuery()) {
            resultSet.next();
            result = resultSet.getInt(1);
        } catch (SQLException e) {
            throw new RepositoryException(e);
        } finally {
            returnConnection(connection);
        }
        return result;
    }
}
