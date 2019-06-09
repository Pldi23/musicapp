package by.platonov.music.repository;

import by.platonov.music.exception.RepositoryException;
import by.platonov.music.db.DatabaseConfiguration;
import by.platonov.music.entity.Gender;
import by.platonov.music.entity.User;
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
    private static final String SQL_INSERT_USER = "insert into application_user(login, password, is_admin, first_name," +
            " last_name, e_mail, gender, date_of_birth) values (?, ?, ?, ?, ?, ?, ?, ?);";
    @Language("SQL")
    private static final String SQL_DELETE_USER = "delete from application_user where login = ?;";
    @Language("SQL")
    private static final String SQL_DELETE_USER_PLAYLIST_LINK = "delete from user_playlist where user_login = ?;";
    @Language("SQL")
    private static final String SQL_UPDATE_USER = "update application_user set password = ?, is_admin = ?, first_name = ?," +
            " last_name = ?, e_mail = ?, gender = ?, date_of_birth = ? where login = ?;";
    @Language("SQL")
    private static final String SQL_QUERY = "select login, password, is_admin, first_name, last_name, e_mail, gender," +
            " date_of_birth from application_user where ";
    @Language("SQL")
    private static final String SQL_COUNT = "select count(*) from application_user where login is not null";
    @Language("SQL")
    private static final String SQL_SELECT_LOGIN = "select login, password, is_admin, first_name, last_name, e_mail," +
            " gender, date_of_birth from application_user where login = ?";


    @Override
    public boolean add(User entity) throws RepositoryException {
        boolean result = false;
        if (select(entity.getLogin()).isEmpty()) {
            TransactionHandler.runInTransaction(connection -> {
                try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT_USER)) {
                    statement.setString(1, entity.getLogin());
                    statement.setString(2, entity.getPassword());
                    statement.setBoolean(3, entity.isAdmin());
                    statement.setString(4, entity.getFirstname());
                    statement.setString(5, entity.getLastname());
                    statement.setString(6, entity.getEmail());
                    statement.setBoolean(7, entity.getGender() == Gender.MALE);
                    statement.setDate(8, Date.valueOf(entity.getBirthDate()));
                    statement.execute();
                    log.debug(entity + " has been added");
                } catch (SQLException e) {
                    throw new RepositoryException(e);
                }
            });
            result = true;
        } else {
            log.debug("User: " + entity.getLogin() + " already exist");
        }
        return result;
    }

    @Override
    public boolean remove(User entity) throws RepositoryException {
        boolean result = false;
        if (select(entity.getLogin()).isPresent()) {
            TransactionHandler.runInTransaction(connection -> {
                try (PreparedStatement statementDeleteUser = connection.prepareStatement(SQL_DELETE_USER);
                     PreparedStatement statementDeleteLink = connection.prepareStatement(SQL_DELETE_USER_PLAYLIST_LINK)) {
                    statementDeleteLink.setString(1, entity.getLogin());
                    statementDeleteLink.execute();
                    statementDeleteUser.setString(1, entity.getLogin());
                    statementDeleteUser.execute();
                } catch (SQLException e) {
                    throw new RepositoryException(e);
                }
            });
            result = true;
        } else {
            log.debug("User: " + entity.getLogin() + " not found");
        }
        return result;
    }

    @Override
    public boolean update(User entity) throws RepositoryException {
        boolean result = false;
        if (select(entity.getLogin()).isPresent()) {
            TransactionHandler.runInTransaction(connection -> {
                try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_USER)) {
                    statement.setString(1, entity.getPassword());
                    statement.setBoolean(2, entity.isAdmin());
                    statement.setString(3, entity.getFirstname());
                    statement.setString(4, entity.getLastname());
                    statement.setString(5, entity.getEmail());
                    statement.setBoolean(6, entity.getGender() == Gender.MALE);
                    statement.setDate(7, Date.valueOf(entity.getBirthDate()));
                    statement.setString(8, entity.getLogin());
                    statement.executeUpdate();
                    log.debug(entity + " updated in " + DatabaseConfiguration.getInstance().getDbName() + "database");
                } catch (SQLException e) {
                    throw new RepositoryException(e);
                }
            });
            result = true;
        } else {
            log.debug("User: " + entity.getLogin() + " not found");
        }
        return result;
    }

    @Override
    public List<User> query(SqlSpecification<User> specification) throws RepositoryException {
        List<User> users = new ArrayList<>();
        TransactionHandler.runInTransaction(connection -> {
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
        });
        log.debug(users.size() + " users found");
        return users;
    }

    public int size() throws RepositoryException {
        final int[] result = new int[1];
        TransactionHandler.runInTransaction(connection -> {
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(SQL_COUNT)) {
                resultSet.next();
                result[0] = resultSet.getInt(1);
            } catch (SQLException e) {
                throw new RepositoryException(e);
            }
        });
        return result[0];
    }

    public Optional<User> select(String login) throws RepositoryException {
        final User[] user = {null};
        TransactionHandler.runInTransaction(connection -> {
            try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_LOGIN)) {
                statement.setString(1, login);
                try (ResultSet resultSetUser = statement.executeQuery()) {
                    while (resultSetUser.next()) {
                        user[0] = User.builder()
                                .login(resultSetUser.getString(1))
                                .password(resultSetUser.getString(2))
                                .admin(resultSetUser.getBoolean(3))
                                .firstname(resultSetUser.getString(4))
                                .lastname(resultSetUser.getString(5))
                                .email(resultSetUser.getString(6))
                                .gender(resultSetUser.getBoolean(7) ? Gender.MALE : Gender.FEMALE)
                                .birthDate(resultSetUser.getDate(8).toLocalDate())
                                .build();
                    }
                }
                log.debug(user[0] + " selected");
            } catch (SQLException e) {
                throw new RepositoryException(e);
            }
        });
        return Optional.ofNullable(user[0]);
    }
}
