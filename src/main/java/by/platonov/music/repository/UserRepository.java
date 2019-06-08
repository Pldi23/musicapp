package by.platonov.music.repository;

import by.platonov.exception.RepositoryException;
import by.platonov.music.db.ConnectionPool;
import by.platonov.music.entity.Gender;
import by.platonov.music.entity.User;
import lombok.extern.log4j.Log4j2;
import org.intellij.lang.annotations.Language;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dzmitryplatonov on 2019-06-07.
 * @version 0.0.1
 */
@Log4j2
public class UserRepository implements Repository<User> {

    @Language("SQL")
    private static final String INSERT_USER = "insert into application_user(login, password, is_admin, first_name," +
            " last_name, e_mail, gender, date_of_birth) values (?, ?, ?, ?, ?, ?, ?, ?);";
    @Language("SQL")
    private static final String DELETE_USER = "delete from application_user where login = ?;";
    @Language("SQL")
    private static final String DELETE_USER_PLAYLIST_LINK = "delete from user_playlist where user_login = ?;";
    @Language("SQL")
    private static final String UPDATE_USER = "update application_user set password = ?, is_admin = ?, first_name = ?," +
            " last_name = ?, e_mail = ?, gender = ?, date_of_birth = ? where login = ?;";
    @Language("SQL")
    private static final String QUERY = "select login, password, is_admin, first_name, last_name, e_mail, gender," +
            " date_of_birth from application_user where ";

    @Override
    public boolean add(User entity) throws RepositoryException {
        boolean result;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = pool.getConnection();
            statement = connection.prepareStatement(INSERT_USER);
            statement.setString(1, entity.getLogin());
            statement.setString(2, entity.getPassword());
            statement.setBoolean(3, entity.isAdmin());
            statement.setString(4, entity.getFirstname());
            statement.setString(5, entity.getLastname());
            statement.setString(6, entity.getEmail());
            statement.setBoolean(7, entity.getGender() == Gender.MALE);
            statement.setDate(8, Date.valueOf(entity.getBirthDate()));
            statement.execute();
            result = true;
        } catch (SQLException | InterruptedException e) {
            log.error("Connection failed", e);
            Thread.currentThread().interrupt();
            throw new RepositoryException(e);
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                log.error("Connection failed in finally", e);
            }
            try {
                pool.releaseConnection(connection);
            } catch (SQLException e) {
                log.error("Connection failed in finally", e);
            }
        }
        log.debug(entity + " added to database");
        return result;
    }

    @Override
    public boolean remove(User entity) throws RepositoryException {
        boolean result;
        ConnectionPool pool = ConnectionPool.getInstance();
        PreparedStatement statementDeleteUser = null;
        PreparedStatement statementDeleteLink = null;
        Connection connection = null;
        try {
            connection = pool.getConnection();
            connection.setAutoCommit(false);
            statementDeleteLink = connection.prepareStatement(DELETE_USER_PLAYLIST_LINK);
            statementDeleteLink.setString(1, entity.getLogin());
            statementDeleteLink.execute();
            statementDeleteUser = connection.prepareStatement(DELETE_USER);
            statementDeleteUser.setString(1, entity.getLogin());
            statementDeleteUser.execute();
            connection.commit();
            result = true;
        } catch (SQLException | InterruptedException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                log.error("Connection failed", e);
                throw new RepositoryException(e);
            }
            log.error("Connection failed", e);
            Thread.currentThread().interrupt();
            throw new RepositoryException(e);
        } finally {
            try {
                statementDeleteUser.close();
            } catch (SQLException e) {
                log.error("Connection failed in finally", e);
            }
            try {
                statementDeleteLink.close();
            } catch (SQLException e) {
                log.error("Connection failed in finally", e);
            }
            try {
                pool.releaseConnection(connection);
            } catch (SQLException e) {
                log.error("Connection failed in finally", e);
            }
        }

        return result;
    }

    @Override
    public boolean update(User entity) throws RepositoryException {
        boolean result;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = pool.getConnection();
            statement = connection.prepareStatement(UPDATE_USER);
            statement.setString(1, entity.getPassword());
            statement.setBoolean(2, entity.isAdmin());
            statement.setString(3, entity.getFirstname());
            statement.setString(4, entity.getLastname());
            statement.setString(5, entity.getEmail());
            statement.setBoolean(6, entity.getGender() == Gender.MALE);
            statement.setDate(7, Date.valueOf(entity.getBirthDate()));
            statement.setString(8, entity.getLogin());
            statement.execute();
            result = true;
        } catch (SQLException | InterruptedException e) {
            log.error("Connection failed", e);
            Thread.currentThread().interrupt();
            throw new RepositoryException(e);
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                log.error("Connection failed in finally", e);
            }
            try {
                pool.releaseConnection(connection);
            } catch (SQLException e) {
                log.error("Connection failed in finally", e);
            }
        }
        return result;
    }

    @Override
    public List<User> query(SqlSpecification<User> specification) throws RepositoryException {
        List<User> users = new ArrayList<>();
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = pool.getConnection();
            statement = connection.prepareStatement(QUERY + specification.toSqlClauses());
            ResultSet resultSet = statement.executeQuery();
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
                users.add(user);
            }
        } catch (SQLException | InterruptedException e) {
            log.error("Connection failed", e);
            Thread.currentThread().interrupt();
            throw new RepositoryException(e);
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                log.error("Connection failed in finally", e);
            }
            try {
                pool.releaseConnection(connection);
            } catch (SQLException e) {
                log.error("Connection failed in finally", e);
            }
        }
        return users;
    }
}
