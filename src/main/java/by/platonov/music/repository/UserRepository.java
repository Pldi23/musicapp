package by.platonov.music.repository;

import by.platonov.music.exception.RepositoryException;
import by.platonov.music.db.DatabaseConfiguration;
import by.platonov.music.entity.Gender;
import by.platonov.music.entity.User;
import by.platonov.music.repository.specification.SelectUserLoginSpecification;
import by.platonov.music.repository.specification.SqlSpecification;
import lombok.extern.log4j.Log4j2;
import org.intellij.lang.annotations.Language;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    private static final String SQL_DELETE_USER = "DELETE FROM application_user " +
                                                    "WHERE login = ?;";
    @Language("SQL")
    private static final String SQL_DELETE_USER_PLAYLIST_LINK = "DELETE FROM user_playlist " +
                                                                "WHERE user_login = ?;";
    @Language("SQL")
    private static final String SQL_UPDATE_USER =
                    "UPDATE application_user " +
                    "SET password = ?, is_admin = ?, first_name = ?, last_name = ?, e_mail = ?, gender = ?, date_of_birth = ? " +
                    "WHERE login = ?;";
    @Language("SQL")
    private static final String SQL_QUERY =
                    "SELECT login, password, is_admin, first_name, last_name, e_mail, gender, date_of_birth " +
                    "FROM application_user " +
                    "WHERE ";
    @Language("SQL")
    private static final String SQL_COUNT = "SELECT count(*) " +
                                            "FROM application_user " +
                                            "WHERE ";
    @Language("SQL")
    private static final String SQL_SELECT =
                    "SELECT login, password, is_admin, first_name, last_name, e_mail, gender, date_of_birth " +
                    "FROM application_user " +
                    "WHERE ";


    @Override
    public boolean add(User user) throws RepositoryException {
        return TransactionHandler.transactional(connection -> {
            Optional<User> optionalUser = findOneNonTransactional(connection, new SelectUserLoginSpecification(user.getLogin()));

            if (optionalUser.isEmpty()) {
                try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT_USER)) {
                    statement.setString(1, user.getLogin());
                    statement.setString(2, user.getPassword());
                    statement.setBoolean(3, user.isAdmin());
                    statement.setString(4, user.getFirstname());
                    statement.setString(5, user.getLastname());
                    statement.setString(6, user.getEmail());
                    statement.setBoolean(7, user.getGender() == Gender.MALE);
                    statement.setDate(8, Date.valueOf(user.getBirthDate()));
                    statement.execute();
                    log.debug(user + " has been added");
                    return true;
                } catch (SQLException e) {
                    throw new RepositoryException(e);
                }
            } else {
                log.warn("User: " + user.getLogin() + " already exists");
                return false;
            }
        });
    }

    @Override
    public boolean remove(User user) throws RepositoryException {
        return TransactionHandler.transactional(connection -> {
            Optional<User> optionalUser = findOneNonTransactional(connection, new SelectUserLoginSpecification(user.getLogin()));

            if (optionalUser.isPresent()) {
                try (PreparedStatement statementDeleteUser = connection.prepareStatement(SQL_DELETE_USER);
                     PreparedStatement statementDeleteLink = connection.prepareStatement(SQL_DELETE_USER_PLAYLIST_LINK)) {
                    statementDeleteLink.setString(1, user.getLogin());
                    statementDeleteLink.execute();
                    statementDeleteUser.setString(1, user.getLogin());
                    statementDeleteUser.execute();
                    log.debug("User: " + user.getLogin() + " has been deleted");
                    return true;
                } catch (SQLException e) {
                    throw new RepositoryException(e);
                }
            } else {
                log.warn(user + " was not found");
                return false;
            }
        });
    }

    @Override
    public boolean update(User user) throws RepositoryException {
        return TransactionHandler.transactional(connection -> {
            boolean result = false;
            Optional<User> optionalUser = findOneNonTransactional(connection, new SelectUserLoginSpecification(user.getLogin()));

            if (optionalUser.isPresent()) {
                try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_USER)) {
                    statement.setString(1, user.getPassword());
                    statement.setBoolean(2, user.isAdmin());
                    statement.setString(3, user.getFirstname());
                    statement.setString(4, user.getLastname());
                    statement.setString(5, user.getEmail());
                    statement.setBoolean(6, user.getGender() == Gender.MALE);
                    statement.setDate(7, Date.valueOf(user.getBirthDate()));
                    statement.setString(8, user.getLogin());
                    result = statement.executeUpdate() > 0;
                    log.debug(user + " updated in " + DatabaseConfiguration.getInstance().getDbName() + "database");
                } catch (SQLException e) {
                    throw new RepositoryException(e);
                }
            } else {
                log.warn(user + " was not found");
            }
            return result;
        });
    }

    @Override
    public List<User> query(SqlSpecification specification) throws RepositoryException {
        return TransactionHandler.transactional(connection -> {
            List<User> users = new ArrayList<>();
            try (PreparedStatement statement = connection.prepareStatement(SQL_QUERY + specification.toSqlClauses());
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    User user = User.builder()
                            .login(resultSet.getString("login"))
                            .password(resultSet.getString("password"))
                            .admin(resultSet.getBoolean("is_admin"))
                            .firstname(resultSet.getString("first_name"))
                            .lastname(resultSet.getString("last_name"))
                            .email(resultSet.getString("e_mail"))
                            .birthDate(resultSet.getDate("date_of_birth").toLocalDate())
                            .gender(resultSet.getBoolean("gender") ? Gender.MALE : Gender.FEMALE)
                            .build();
                    log.trace(user + " added to query list");
                    users.add(user);
                }
            } catch (SQLException e) {
                throw new RepositoryException(e);
            }
            log.debug(users.size() + " users found");
            return users;
        });
    }

    @Override
    public int count(SqlSpecification specification) throws RepositoryException {
        return TransactionHandler.transactional(connection -> {
            try (PreparedStatement statement = connection.prepareStatement(SQL_COUNT + specification.toSqlClauses());
                 ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                return resultSet.getInt(1);
            } catch (SQLException e) {
                throw new RepositoryException(e);
            }
        });
    }

    @Override
    public Optional<User> findOne(SqlSpecification specification) throws RepositoryException {
        return TransactionHandler.transactional(connection -> findOneNonTransactional(connection, specification));
    }

    private Optional<User> findOneNonTransactional(Connection connection, SqlSpecification specification) throws RepositoryException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT + specification.toSqlClauses());
             ResultSet resultSetUser = statement.executeQuery()) {
            if (resultSetUser.next()) {
                User user = User.builder()
                        .login(resultSetUser.getString(1))
                        .password(resultSetUser.getString(2))
                        .admin(resultSetUser.getBoolean(3))
                        .firstname(resultSetUser.getString(4))
                        .lastname(resultSetUser.getString(5))
                        .email(resultSetUser.getString(6))
                        .gender(resultSetUser.getBoolean(7) ? Gender.MALE : Gender.FEMALE)
                        .birthDate(resultSetUser.getDate(8).toLocalDate())
                        .build();
                return Optional.of(user);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }
}
