package by.platonov.music.repository;

import by.platonov.music.entity.Playlist;
import by.platonov.music.exception.RepositoryException;
import by.platonov.music.db.DatabaseConfiguration;
import by.platonov.music.entity.Gender;
import by.platonov.music.entity.User;
import by.platonov.music.repository.jdbchelper.JdbcHelper;
import by.platonov.music.repository.mapper.preparedStatement.SetUserFieldsMapper;
import by.platonov.music.repository.mapper.preparedStatement.SetUserIdMapper;
import by.platonov.music.repository.mapper.preparedStatement.SetUserUpdateMapper;
import by.platonov.music.repository.mapper.resultSet.AbstractRowMapper;
import by.platonov.music.repository.mapper.resultSet.UserRowMapper;
import by.platonov.music.repository.specification.UserLoginSpecification;
import by.platonov.music.repository.specification.SqlSpecification;
import lombok.extern.log4j.Log4j2;
import org.intellij.lang.annotations.Language;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author dzmitryplatonov on 2019-06-07.
 * @version 0.0.1
 */
@Log4j2
public class UserRepository implements Repository<User> {

    @Language("SQL")
    private static final String SQL_INSERT_USER =
            "INSERT INTO application_user(login, password, is_admin, first_name, last_name, e_mail, gender, date_of_birth) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
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
                    "SET password = ?, is_admin = ?, first_name = ?, last_name = ?, e_mail = ?, gender = ?, date_of_birth = ? " +
                    "WHERE login = ?;";
    @Language("SQL")
    private static final String SQL_COUNT_USER =
            "SELECT count(*) FROM application_user ";
    @Language("SQL")
    private static final String SQL_SELECT_USER =
            "SELECT login, password, is_admin, first_name, last_name, e_mail, gender, date_of_birth " +
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
                    instance = new UserRepository(TransactionHandler.getInstance(), new JdbcHelper());
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
        return TransactionHandler.getInstance().transactional(connection -> {
            if (jdbcHelper.query(connection, SQL_SELECT_USER + new UserLoginSpecification(user.getLogin()).toSqlClauses(),
                    new UserRowMapper()).isEmpty()) {
                for (Playlist playlist : user.getPlaylists()) {
                    jdbcHelper.execute(connection, SQL_INSERT_USER_PLAYLIST_LINK, user, ((preparedStatement, entity) -> {
                        preparedStatement.setString(1, user.getLogin());
                        preparedStatement.setLong(2, playlist.getId());
                    }));
                }
                jdbcHelper.execute(connection, SQL_INSERT_USER, user, new SetUserFieldsMapper());
//                addUserPlaylistLinkNonTransactional(connection, user);
//                return addUserNonTransactional(connection, user);
                log.debug(user + " added successfully");
                return true;
            } else {
                log.warn("User: " + user.getLogin() + " already exists");
                return false;
            }
        });
    }

    private void addUserPlaylistLinkNonTransactional(Connection connection, User user) throws RepositoryException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT_USER_PLAYLIST_LINK)) {
            for (Playlist playlist : user.getPlaylists()) {
                statement.setString(1, user.getLogin());
                statement.setLong(2, playlist.getId());
                statement.execute();
                log.debug("User link to playlist added");
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    private boolean addUserNonTransactional(Connection connection, User user) throws RepositoryException {
        try (PreparedStatement statementInsertUser = connection.prepareStatement(SQL_INSERT_USER)) {
            statementInsertUser.setString(1, user.getLogin());
            statementInsertUser.setString(2, user.getPassword());
            statementInsertUser.setBoolean(3, user.isAdmin());
            statementInsertUser.setString(4, user.getFirstname());
            statementInsertUser.setString(5, user.getLastname());
            statementInsertUser.setString(6, user.getEmail());
            statementInsertUser.setBoolean(7, user.getGender() == Gender.MALE);
            statementInsertUser.setDate(8, Date.valueOf(user.getBirthDate()));
            statementInsertUser.execute();
            log.debug(user + " has been added");
            return true;
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public boolean remove(User user) throws RepositoryException {
        return TransactionHandler.getInstance().transactional(connection -> {
            if (!jdbcHelper.query(connection, SQL_SELECT_USER + new UserLoginSpecification(user.getLogin()).toSqlClauses(), new UserRowMapper()).isEmpty()) {
                jdbcHelper.execute(connection, SQL_DELETE_USER_PLAYLIST_LINK, user, new SetUserIdMapper());
//                removeUserPlaylistLinkNonTransactional(connection, user);
//                return removeUserNonTransactional(connection, user);
                log.debug(user + " removed");
                return false;
            } else {
                log.warn(user + " was not found");
                return false;
            }
        });
    }

    private void removeUserPlaylistLinkNonTransactional(Connection connection, User user) throws RepositoryException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE_USER_PLAYLIST_LINK)) {
            statement.setString(1, user.getLogin());
            statement.execute();
            log.debug("User links deleted");
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    private boolean removeUserNonTransactional(Connection connection, User user) throws RepositoryException {
        try (PreparedStatement statementDeleteUser = connection.prepareStatement(SQL_DELETE_USER)) {
            statementDeleteUser.setString(1, user.getLogin());
            statementDeleteUser.execute();
            log.debug("User: " + user.getLogin() + " has been deleted");
            return true;
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public boolean update(User user) throws RepositoryException {
        return TransactionHandler.getInstance().transactional(connection -> {
            if (!jdbcHelper.query(connection, SQL_SELECT_USER + new UserLoginSpecification(user.getLogin()).toSqlClauses(), new UserRowMapper()).isEmpty()) {
                jdbcHelper.execute(connection, SQL_DELETE_USER_PLAYLIST_LINK, user, new SetUserIdMapper());
                for (Playlist playlist : user.getPlaylists()) {
                    jdbcHelper.execute(connection, SQL_INSERT_USER_PLAYLIST_LINK, user, ((preparedStatement, entity) -> {
                        preparedStatement.setString(1, user.getLogin());
                        preparedStatement.setLong(2, playlist.getId());
                    }));
                }
                jdbcHelper.execute(connection, SQL_UPDATE_USER, user, new SetUserUpdateMapper());
                log.debug(user + " updated");
//                removeUserPlaylistLinkNonTransactional(connection, user);
//                addUserPlaylistLinkNonTransactional(connection, user);
//                return updateUserNonTransactional(connection, user);

                return true;
            } else {
                log.warn(user + " was not found");
                return false;
            }
        });
    }

    private boolean updateUserNonTransactional(Connection connection, User user) throws RepositoryException {
        try (PreparedStatement statementUpdateUser = connection.prepareStatement(SQL_UPDATE_USER)) {
            statementUpdateUser.setString(1, user.getPassword());
            statementUpdateUser.setBoolean(2, user.isAdmin());
            statementUpdateUser.setString(3, user.getFirstname());
            statementUpdateUser.setString(4, user.getLastname());
            statementUpdateUser.setString(5, user.getEmail());
            statementUpdateUser.setBoolean(6, user.getGender() == Gender.MALE);
            statementUpdateUser.setDate(7, Date.valueOf(user.getBirthDate()));
            statementUpdateUser.setString(8, user.getLogin());

            log.debug(user + " updated in " + DatabaseConfiguration.getInstance().getDbName() + "database");
            return statementUpdateUser.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public long count(SqlSpecification specification) throws RepositoryException {
        return TransactionHandler.getInstance().transactional(connection ->
                jdbcHelper.count(connection, SQL_COUNT_USER + specification.toSqlClauses()));
    }

    @Override
    public List<User> query(SqlSpecification specification) throws RepositoryException {
        return transactionHandler.transactional(connection ->
                jdbcHelper.query(connection, SQL_SELECT_USER + specification.toSqlClauses(), new UserRowMapper()));
    }

//    private List<User> queryNonTransactional(Connection connection, SqlSpecification specification) throws RepositoryException {
//        List<User> users = new ArrayList<>();
//        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_USER + specification.toSqlClauses());
//             ResultSet resultSet = statement.executeQuery()) {
//            AbstractRowMapper<User> mapper = new UserRowMapper();
//            while (resultSet.next()) {
//                User user = mapper.map(resultSet);
//                log.trace(user + " added to query list");
//                users.add(user);
//            }
//        } catch (SQLException e) {
//            throw new RepositoryException(e);
//        }
//        log.debug(users.size() + " users found");
//        return users;
//    }
}
