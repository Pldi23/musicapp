package by.platonov.music.repository;

import by.platonov.music.entity.Genre;
import by.platonov.music.exception.RepositoryException;
import by.platonov.music.repository.mapper.AbstractRowMapper;
import by.platonov.music.repository.mapper.GenreRowMapper;
import by.platonov.music.repository.specification.GenreIdSpecification;
import by.platonov.music.repository.specification.SqlSpecification;
import lombok.extern.log4j.Log4j2;
import org.intellij.lang.annotations.Language;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author dzmitryplatonov on 2019-06-10.
 * @version 0.0.1
 */
@Log4j2
public class GenreRepository implements Repository<Genre> {

    @Language("SQL")
    private static final String SQL_INSERT_GENRE = "INSERT INTO genre (name) VALUES (?);";
    @Language("SQL")
    private static final String SQL_DELETE_GENRE = "DELETE FROM genre WHERE id = ?;";
    @Language("SQL")
    private static final String SQL_DELETE_LINK = "UPDATE track SET genre_id = null WHERE genre_id = ?";
    @Language("SQL")
    private static final String SQL_UPDATE_GENRE = "UPDATE genre SET name = ? WHERE id = ?;";
    @Language("SQL")
    private static final String SQL_QUERY_GENRE = "SELECT id genreid, name genre FROM genre ";
    @Language("SQL")
    private static final String SQL_COUNT = "SELECT COUNT(*) FROM genre ";

    private static GenreRepository instance;
    private static ReentrantLock lock = new ReentrantLock();
    private static AtomicBoolean create = new AtomicBoolean(false);

    private GenreRepository() {
    }

    public static GenreRepository getInstance() {
        if (!create.get()) {
            lock.lock();
            try {
                if (instance == null) {
                    instance = new GenreRepository();
                    create.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    @Override
    public boolean add(Genre genre) throws RepositoryException {
        return TransactionHandler.getInstance().transactional(connection -> {
            if (queryNonTransactional(connection, new GenreIdSpecification(genre.getId())).isEmpty()) {
                try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT_GENRE)) {
                    statement.setString(1, genre.getTitle());
                    statement.execute();
                    log.debug(genre + " has been added");
                    return true;
                } catch (SQLException e) {
                    throw new RepositoryException(e);
                }
            } else {
                log.debug(genre + " already exists");
                return false;
            }
        });
    }

    @Override
    public boolean remove(Genre genre) throws RepositoryException {
        return TransactionHandler.getInstance().transactional(connection -> {
            if (!queryNonTransactional(connection, new GenreIdSpecification(genre.getId())).isEmpty()) {
                try (PreparedStatement statementGenre = connection.prepareStatement(SQL_DELETE_GENRE);
                    PreparedStatement statementLink = connection.prepareStatement(SQL_DELETE_LINK)) {
                    statementGenre.setLong(1, genre.getId());
                    statementLink.setLong(1, genre.getId());
                    statementLink.executeUpdate();
                    statementGenre.execute();
                    return true;
                } catch (SQLException e) {
                    throw new RepositoryException(e);
                }
            } else {
                log.debug("Genre: " + genre.getId() + " was not found");
                return false;
            }
        });
    }

    @Override
    public boolean update(Genre genre) throws RepositoryException {
        return TransactionHandler.getInstance().transactional(connection -> {
            if (!queryNonTransactional(connection, new GenreIdSpecification(genre.getId())).isEmpty()) {
                try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_GENRE)) {
                    statement.setString(1, genre.getTitle());
                    statement.setLong(2, genre.getId());
                    statement.execute();
                    return true;
                } catch (SQLException e) {
                    throw new RepositoryException(e);
                }
            } else {
                log.debug("Genre: " + genre.getId() + " was not found");
                return false;
            }
        });
    }

    @Override
    public List<Genre> query(SqlSpecification specification) throws RepositoryException {
        return TransactionHandler.getInstance().transactional(connection -> queryNonTransactional(connection, specification));
    }

    private List<Genre> queryNonTransactional(Connection connection, SqlSpecification specification) throws RepositoryException {
        List<Genre> genres = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SQL_QUERY_GENRE + specification.toSqlClauses());
             ResultSet resultSet = statement.executeQuery()) {
            AbstractRowMapper<Genre> mapper = new GenreRowMapper();
            while (resultSet.next()) {
                Genre genre = mapper.map(resultSet);
                log.debug(genre + " has been added to query list");
                genres.add(genre);
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        log.debug(genres.size() + " genres selected");
        return genres;
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
