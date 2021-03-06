package by.platonov.music.repository;

import by.platonov.music.entity.Genre;
import by.platonov.music.exception.RepositoryException;
import by.platonov.music.repository.mapper.PreparedStatementMapper;
import by.platonov.music.repository.mapper.SetGenreFieldsMapper;
import by.platonov.music.repository.mapper.SetGenreIdMapper;
import by.platonov.music.repository.mapper.SetGenreUpdateMapper;
import by.platonov.music.repository.extractor.GenreResultSetExtractor;
import by.platonov.music.repository.specification.GenreIdSpecification;
import by.platonov.music.repository.specification.GenreTitleSpecification;
import by.platonov.music.repository.specification.SqlSpecification;
import lombok.extern.log4j.Log4j2;
import org.intellij.lang.annotations.Language;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * a class that interacts with the database and accumulates in itself all methods to add/update/remove or receive
 * {@link Genre} from database
 * @author dzmitryplatonov on 2019-06-10.
 * @version 0.0.1
 */
@Log4j2
public class GenreRepository implements Repository<Genre> {

    @Language("SQL")
    private static final String SQL_INSERT_GENRE = "INSERT INTO genre (genre_name) VALUES (?);";
    @Language("SQL")
    private static final String SQL_DELETE_GENRE = "DELETE FROM genre WHERE id = ?;";
    @Language("SQL")
    private static final String SQL_DELETE_LINK = "UPDATE track SET genre_id = null WHERE genre_id = ?";
    @Language("SQL")
    private static final String SQL_UPDATE_GENRE = "UPDATE genre SET genre_name = ? WHERE id = ?;";
    @Language("SQL")
    private static final String SQL_QUERY_GENRE = "SELECT id as genreid, genre_name FROM genre ";
    @Language("SQL")
    private static final String SQL_COUNT_GENRE = "SELECT COUNT(*) FROM genre ";

    private static GenreRepository instance;
    private static ReentrantLock lock = new ReentrantLock();
    private static AtomicBoolean create = new AtomicBoolean(false);

    private TransactionHandler transactionHandler;
    private JdbcHelper jdbcHelper;

    private GenreRepository(TransactionHandler transactionHandler, JdbcHelper jdbcHelper) {
        this.transactionHandler = transactionHandler;
        this.jdbcHelper = jdbcHelper;
    }

    public static GenreRepository getInstance() {
        if (!create.get()) {
            lock.lock();
            try {
                if (instance == null) {
                    instance = new GenreRepository(new TransactionHandler(), new JdbcHelper());
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
        return transactionHandler.transactional(connection -> {
            if (jdbcHelper.query(connection, SQL_QUERY_GENRE, new GenreTitleSpecification(genre.getTitle()),
                    new GenreResultSetExtractor()).isEmpty()) {
                jdbcHelper.execute(connection, SQL_INSERT_GENRE, genre, new SetGenreFieldsMapper());
                log.debug(genre + " added");
                return true;
            } else {
                log.debug(genre + " already exists");
                return false;
            }
        });
    }

    @Override
    public boolean remove(Genre genre) throws RepositoryException {
        return transactionHandler.transactional(connection -> {
            if (!jdbcHelper.query(connection, SQL_QUERY_GENRE, new GenreIdSpecification(genre.getId()),
                    new GenreResultSetExtractor()).isEmpty()) {
                PreparedStatementMapper<Genre> mapper = new SetGenreIdMapper();
                jdbcHelper.execute(connection, SQL_DELETE_LINK, genre, mapper);
                jdbcHelper.execute(connection, SQL_DELETE_GENRE, genre, mapper);
                log.debug(genre + " removed");
                return true;
            } else {
                log.debug("Genre: " + genre.getId() + " was not found");
                return false;
            }
        });
    }

    @Override
    public boolean update(Genre genre) throws RepositoryException {
        return transactionHandler.transactional(connection -> {
            if (!jdbcHelper.query(connection, SQL_QUERY_GENRE, new GenreIdSpecification(genre.getId()),
                    new GenreResultSetExtractor()).isEmpty()) {
                jdbcHelper.execute(connection, SQL_UPDATE_GENRE, genre, new SetGenreUpdateMapper());
                log.debug(genre + " updated");
                return true;
            } else {
                log.debug("Genre: " + genre.getId() + " was not found");
                return false;
            }
        });
    }

    @Override
    public List<Genre> query(SqlSpecification specification) throws RepositoryException {
        return transactionHandler.transactional(connection ->
                jdbcHelper.query(connection, SQL_QUERY_GENRE, specification, new GenreResultSetExtractor()));
    }

    @Override
    public long count(SqlSpecification specification) throws RepositoryException {
        return transactionHandler.transactional(connection ->
                jdbcHelper.count(connection, SQL_COUNT_GENRE, specification));
    }
}
