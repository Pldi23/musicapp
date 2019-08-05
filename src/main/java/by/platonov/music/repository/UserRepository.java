package by.platonov.music.repository;

import by.platonov.music.entity.Playlist;
import by.platonov.music.exception.RepositoryException;
import by.platonov.music.entity.User;
import by.platonov.music.repository.mapper.SetUserFieldsMapper;
import by.platonov.music.repository.mapper.SetUserIdMapper;
import by.platonov.music.repository.mapper.SetUserUpdateMapper;
import by.platonov.music.repository.extractor.UserResultSetExtractor;
import by.platonov.music.repository.specification.UserLoginSpecification;
import by.platonov.music.repository.specification.SqlSpecification;
import lombok.extern.log4j.Log4j2;
import org.intellij.lang.annotations.Language;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * a class that interacts with the database and accumulates in itself all methods to add/update/remove or receive the
 * {@link User} of the application
 * @author dzmitryplatonov on 2019-06-07.
 * @version 0.0.1
 */
@Log4j2
public class UserRepository implements Repository<User> {

    @Language("SQL")
    private static final String SQL_INSERT_USER =
            "INSERT INTO application_user(login, password, is_admin, first_name, last_name, e_mail, gender, " +
                    "date_of_birth, active_status, verification_uuid, photo_path) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    @Language("SQL")
    private static final String SQL_INSERT_USER_PLAYLIST_LINK =
            "INSERT INTO user_playlist(user_login, playlist_id) VALUES (?, ?);";
    @Language("SQL")
    private static final String SQL_DELETE_USER =
            "DELETE FROM application_user WHERE login = ?;";
    @Language("SQL")
    private static final String SQL_DELETE_USER_PLAYLIST_LINK =
            "DELETE FROM user_playlist WHERE user_login = ?;";
    @Language("SQL")
    private static final String SQL_UPDATE_USER =
            "UPDATE application_user " +
                    "SET password = ?, is_admin = ?, first_name = ?, last_name = ?, e_mail = ?, gender = ?, " +
                    "date_of_birth = ?, active_status = ?, verification_uuid = ?, photo_path = ? " +
                    "WHERE login = ?;";
    @Language("SQL")
    private static final String SQL_COUNT_USER =
            "SELECT count(*) FROM application_user ";
    @Language("SQL")
    private static final String SQL_SELECT_USER =
            "SELECT login, password, is_admin, first_name, last_name, e_mail, gender, date_of_birth, created_at, " +
                    "active_status, verification_uuid, photo_path " +
                    "FROM application_user ";


    private static UserRepository instance;
    private static ReentrantLock lock = new ReentrantLock();
    private static AtomicBoolean create = new AtomicBoolean(false);

    private TransactionHandler transactionHandler;
    private JdbcHelper jdbcHelper;

    private UserRepository(TransactionHandler transactionHandler, JdbcHelper jdbcHelper) {
        this.transactionHandler = transactionHandler;
        this.jdbcHelper = jdbcHelper;
    }

    public static UserRepository getInstance() {
        if (!create.get()) {
            lock.lock();
            try {
                if (instance == null) {
                    instance = new UserRepository(new TransactionHandler(), new JdbcHelper());
                    create.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }


    @Override
    public boolean add(User user) throws RepositoryException {
        return transactionHandler.transactional(connection -> {
            if (jdbcHelper.query(connection, SQL_SELECT_USER, new UserLoginSpecification(user.getLogin()),
                    new UserResultSetExtractor()).isEmpty()) {
                for (Playlist playlist : user.getPlaylists()) {
                    jdbcHelper.execute(connection, SQL_INSERT_USER_PLAYLIST_LINK, user, ((preparedStatement, entity) -> {
                        preparedStatement.setString(1, user.getLogin());
                        preparedStatement.setLong(2, playlist.getId());
                    }));
                }
                jdbcHelper.execute(connection, SQL_INSERT_USER, user, new SetUserFieldsMapper());
                log.debug(user + " added successfully");
                return true;
            } else {
                log.warn("User: " + user.getLogin() + " already exists");
                return false;
            }
        });
    }

    @Override
    public boolean remove(User user) throws RepositoryException {
        return transactionHandler.transactional(connection -> {
            if (!jdbcHelper.query(connection, SQL_SELECT_USER, new UserLoginSpecification(user.getLogin()),
                    new UserResultSetExtractor()).isEmpty()) {
                jdbcHelper.execute(connection, SQL_DELETE_USER_PLAYLIST_LINK, user, new SetUserIdMapper());
                jdbcHelper.execute(connection, SQL_DELETE_USER, user, new SetUserIdMapper());
                log.debug(user + " removed");
                return true;
            } else {
                log.warn(user + " was not found");
                return false;
            }
        });
    }

    @Override
    public boolean update(User user) throws RepositoryException {
        return transactionHandler.transactional(connection -> {
            if (!jdbcHelper.query(connection, SQL_SELECT_USER, new UserLoginSpecification(user.getLogin()),
                    new UserResultSetExtractor()).isEmpty()) {
                jdbcHelper.execute(connection, SQL_DELETE_USER_PLAYLIST_LINK, user, new SetUserIdMapper());
                for (Playlist playlist : user.getPlaylists()) {
                    jdbcHelper.execute(connection, SQL_INSERT_USER_PLAYLIST_LINK, user, ((preparedStatement, entity) -> {
                        preparedStatement.setString(1, user.getLogin());
                        preparedStatement.setLong(2, playlist.getId());
                    }));
                }
                jdbcHelper.execute(connection, SQL_UPDATE_USER, user, new SetUserUpdateMapper());
                log.debug(user + " updated");
                return true;
            } else {
                log.warn(user + " was not found");
                return false;
            }
        });
    }

    @Override
    public long count(SqlSpecification specification) throws RepositoryException {
        return transactionHandler.transactional(connection ->
                jdbcHelper.count(connection, SQL_COUNT_USER, specification));
    }

    @Override
    public List<User> query(SqlSpecification specification) throws RepositoryException {
        return transactionHandler.transactional(connection ->
                jdbcHelper.query(connection, SQL_SELECT_USER, specification, new UserResultSetExtractor()));
    }
}
