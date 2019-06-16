package by.platonov.music.repository;

import by.platonov.music.exception.RepositoryException;
import by.platonov.music.entity.Musician;
import by.platonov.music.repository.jdbchelper.JdbcHelper;
import by.platonov.music.repository.mapper.PreparedStatementMapper;
import by.platonov.music.repository.mapper.SetMusicianIdMapper;
import by.platonov.music.repository.mapper.SetMusicianNameMapper;
import by.platonov.music.repository.mapper.SetMusicianUpdateMapper;
import by.platonov.music.repository.extractor.MusicianResultSetExtractor;
import by.platonov.music.repository.specification.MusicianIdSpecification;
import by.platonov.music.repository.specification.SqlSpecification;
import lombok.extern.log4j.Log4j2;
import org.intellij.lang.annotations.Language;

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

    private TransactionHandler transactionHandler;
    private JdbcHelper jdbcHelper;

    private MusicianRepository(TransactionHandler transactionHandler, JdbcHelper jdbcHelper) {
        this.transactionHandler = transactionHandler;
        this.jdbcHelper = jdbcHelper;
    }

    public static MusicianRepository getInstance() {
        if (!create.get()) {
            lock.lock();
            try {
                if (instance == null) {
                    instance = new MusicianRepository(TransactionHandler.getInstance(), new JdbcHelper());
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
        return transactionHandler.transactional(connection -> {
            if (jdbcHelper.query(connection, SQL_QUERY_MUSICIAN + new MusicianIdSpecification(musician.getId())
                    .toSqlClauses(), new MusicianResultSetExtractor()).isEmpty()) {
                jdbcHelper.execute(connection, SQL_INSERT_MUSICIAN, musician, new SetMusicianNameMapper());
                log.debug(musician + " has been added");
                return true;
            } else {
                log.info("Musician with id: " + musician.getId() + " already exists");
                return false;
            }
        });
    }

    @Override
    public boolean remove(Musician musician) throws RepositoryException {
        return TransactionHandler.getInstance().transactional(connection -> {
            if (!jdbcHelper.query(connection, SQL_QUERY_MUSICIAN + new MusicianIdSpecification(musician.getId())
                    .toSqlClauses(), new MusicianResultSetExtractor()).isEmpty()) {
                PreparedStatementMapper<Musician> mapper = new SetMusicianIdMapper();
                jdbcHelper.execute(connection, SQL_DELETE_SINGER_LINK, musician, mapper);
                jdbcHelper.execute(connection, SQL_DELETE_AUTHOR_LINK, musician, mapper);
                jdbcHelper.execute(connection, SQL_DELETE_MUSICIAN, musician, mapper);
                log.debug(musician + " successfully removed");
                return true;
            } else {
                log.info("Musician: " + musician.getId() + " no found");
                return false;
            }
        });
    }

    @Override
    public boolean update(Musician musician) throws RepositoryException {
        return TransactionHandler.getInstance().transactional(connection -> {
            if (!jdbcHelper.query(connection, SQL_QUERY_MUSICIAN + new MusicianIdSpecification(musician.getId())
                    .toSqlClauses(), new MusicianResultSetExtractor()).isEmpty()) {
                jdbcHelper.execute(connection, SQL_UPDATE_MUSICIAN, musician, new SetMusicianUpdateMapper());
                log.debug(musician + " has been updated");
                return true;
            } else {
                log.debug("Musician: " + musician.getId() + " not found");
                return false;
            }
        });
    }

    @Override
    public List<Musician> query(SqlSpecification specification) throws RepositoryException {
        return transactionHandler.transactional(connection ->
                jdbcHelper.query(connection, SQL_QUERY_MUSICIAN + specification.toSqlClauses(), new MusicianResultSetExtractor()));
    }

    @Override
    public long count(SqlSpecification specification) throws RepositoryException {
        return transactionHandler.transactional(connection -> jdbcHelper.count(connection, SQL_COUNT_MUSICIAN + specification.toSqlClauses()));
    }
}
