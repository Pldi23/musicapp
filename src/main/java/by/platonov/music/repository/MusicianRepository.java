package by.platonov.music.repository;

import by.platonov.music.exception.RepositoryException;
import by.platonov.music.entity.Musician;
import by.platonov.music.repository.mapper.AbstractRowMapper;
import by.platonov.music.repository.mapper.MusicianRowMapper;
import by.platonov.music.repository.specification.MusicianIdSpecification;
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
 * @author dzmitryplatonov on 2019-06-09.
 * @version 0.0.1
 */
@Log4j2
public class MusicianRepository implements Repository<Musician> {

    @Language("SQL")
    private static final String SQL_INSERT_MUSICIAN = "INSERT INTO musician (name) VALUES (?);";
    @Language("SQL")
    private static final String SQL_DELETE_MUSICIAN = "DELETE FROM musician WHERE id = ?";
    @Language("SQL")
    private static final String SQL_DELETE_AUTHOR_LINK = "DELETE FROM author_track WHERE author_id = ?";
    @Language("SQL")
    private static final String SQL_DELETE_SINGER_LINK = "DELETE FROM singer_track WHERE singer_id = ?";
    @Language("SQL")
    private static final String SQL_UPDATE_MUSICIAN = "UPDATE musician " +
                                                        "SET name = ? " +
                                                        "WHERE id = ?;";
    @Language("SQL")
    private static final String SQL_QUERY_MUSICIAN = "SELECT id, name " +
                                                        "FROM musician ";
    @Language("SQL")
    private static final String SQL_COUNT_MUSICIAN = "SELECT count(*) FROM musician ";

    private static MusicianRepository instance;
    private static ReentrantLock lock = new ReentrantLock();
    private static AtomicBoolean create = new AtomicBoolean(false);

    private MusicianRepository() {
    }

    public static MusicianRepository getInstance() {
        if (!create.get()) {
            lock.lock();
            try {
                if (instance == null) {
                    instance = new MusicianRepository();
                    create.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    @Override
    public boolean add(Musician musician) throws RepositoryException {
        return TransactionHandler.getInstance().transactional(connection -> {
            if (queryNonTransactional(connection, new MusicianIdSpecification(musician.getId())).isEmpty()) {
                try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT_MUSICIAN)) {
                    statement.setString(1, musician.getName());
                    statement.execute();
                    log.debug(musician + " has been added");
                    return true;
                } catch (SQLException e) {
                    throw new RepositoryException(e);
                }
            } else {
                log.info("Musician with id: " + musician.getId() + " already exists");
                return false;
            }
        });
    }

    @Override
    public boolean remove(Musician musician) throws RepositoryException {
        return TransactionHandler.getInstance().transactional(connection -> {
            if (!queryNonTransactional(connection, new MusicianIdSpecification(musician.getId())).isEmpty()) {
                try(PreparedStatement statementMusician = connection.prepareStatement(SQL_DELETE_MUSICIAN);
                    PreparedStatement statementAuthorLink = connection.prepareStatement(SQL_DELETE_AUTHOR_LINK);
                    PreparedStatement statementSingerLink = connection.prepareStatement(SQL_DELETE_SINGER_LINK)) {
                    statementMusician.setLong(1, musician.getId());
                    statementSingerLink.setLong(1, musician.getId());
                    statementAuthorLink.setLong(1, musician.getId());
                    statementSingerLink.execute();
                    statementAuthorLink.execute();
                    statementMusician.execute();
                    log.debug(musician + " successfully removed");
                    return true;
                } catch (SQLException e) {
                    throw new RepositoryException(e);
                }
            } else {
                log.info("Musician: " + musician.getId() + " no found");
                return false;
            }
        });
    }

    @Override
    public boolean update(Musician musician) throws RepositoryException {
        return TransactionHandler.getInstance().transactional(connection -> {
            if (!queryNonTransactional(connection, new MusicianIdSpecification(musician.getId())).isEmpty()) {
                try(PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_MUSICIAN)) {
                    statement.setString(1, musician.getName());
                    statement.setLong(2, musician.getId());
                    statement.execute();
                    log.debug(musician + " has been updated");
                    return true;
                } catch (SQLException e) {
                    throw new RepositoryException(e);
                }
            } else {
                log.debug("Musician: " + musician.getId() + " not found");
                return false;
            }
        });
    }

    @Override
    public List<Musician> query(SqlSpecification specification) throws RepositoryException {
        return TransactionHandler.getInstance().transactional(connection -> queryNonTransactional(connection, specification));
    }

    private List<Musician> queryNonTransactional(Connection connection, SqlSpecification specification) throws RepositoryException {
        List<Musician> musicians = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement(SQL_QUERY_MUSICIAN + specification.toSqlClauses());
            ResultSet resultSet = statement.executeQuery()) {
            AbstractRowMapper<Musician> mapper = new MusicianRowMapper();
            while (resultSet.next()) {
                Musician musician = mapper.map(resultSet);
                musicians.add(musician);
                log.debug(musician + " added to query list");
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        log.debug(musicians.size() + " musicians selected");
        return musicians;
    }

    @Override
    public int count(SqlSpecification specification) throws RepositoryException {
        return TransactionHandler.getInstance().transactional(connection -> {
            try(PreparedStatement statement = connection.prepareStatement(SQL_COUNT_MUSICIAN + specification.toSqlClauses());
                ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                return resultSet.getInt(1);
            } catch (SQLException e) {
                throw new RepositoryException(e);
            }
        });
    }
}
