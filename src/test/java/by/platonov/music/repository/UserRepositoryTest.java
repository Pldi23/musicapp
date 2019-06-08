package by.platonov.music.repository;

import by.platonov.exception.RepositoryException;
import by.platonov.music.db.ConnectionPool;
import by.platonov.music.db.DatabaseConfiguration;
import by.platonov.music.db.DatabaseSetupExtension;
import by.platonov.music.entity.Gender;
import by.platonov.music.entity.User;
import org.intellij.lang.annotations.Language;
import org.junit.Rule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author dzmitryplatonov on 2019-06-08.
 * @version 0.0.1
 */
@ExtendWith(DatabaseSetupExtension.class)
class UserRepositoryTest {

    UserRepository repository = new UserRepository();

    int countSize() throws SQLException, InterruptedException {
        ConnectionPool pool = ConnectionPool.getInstance();

        Connection connectionCount = pool.getConnection();
        @Language("SQL")
        String count = "select count(*) from application_user where login is not null";
        ResultSet resultSetCount = connectionCount.prepareStatement(count).executeQuery();
        resultSetCount.next();
        int actualSize = resultSetCount.getInt(1);
        pool.releaseConnection(connectionCount);
        return actualSize;
    }

    User select(String login) throws SQLException, InterruptedException {
        ConnectionPool pool = ConnectionPool.getInstance();
        @Language("SQL")
        String select = "select login, password, is_admin, first_name, last_name, e_mail, gender, date_of_birth from application_user where login = ?";
        Connection connectionSelect = pool.getConnection();
        PreparedStatement statement = connectionSelect.prepareStatement(select);
        statement.setString(1, login);
        ResultSet resultSetUser = statement.executeQuery();
        User actualUser = null;
        while (resultSetUser.next()) {
            actualUser = User.builder()
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
        pool.releaseConnection(connectionSelect);
        return actualUser;
    }

    @Test
    void add() throws InterruptedException, SQLException, RepositoryException {
        User user = User.builder().login("pldi6").password("pldi6").admin(true).firstname("Dima").lastname("Plat")
                .gender(Gender.MALE).email("pl@pl.ru").birthDate(LocalDate.of(1986, 7, 2)).build();

        assertTrue(repository.add(user));
        int actualSize = countSize();
        assertEquals(6, actualSize);
        User actualUser = select("pldi6");
        assertEquals(user, actualUser);
    }

    @Test
    void remove() throws RepositoryException, SQLException, InterruptedException {
        User user = User.builder().login("pldi").build();
        assertTrue(repository.remove(user));
        int actualSize = countSize();
        assertEquals(4, actualSize);
    }

    @Test
    void update() throws RepositoryException, SQLException, InterruptedException {
        User user = User.builder().login("pldi4").password("Ronaldo").admin(false).firstname("Cristiano")
                .lastname("Ronaldo").email("Ronaldo@gmail.com").gender(Gender.MALE)
                .birthDate(LocalDate.of(1985, 6,1)).build();
        repository.update(user);
        User actualUser = select("pldi4");
        User expectedUser = User.builder().login("pldi4").password("Ronaldo").admin(false).firstname("Cristiano")
                .lastname("Ronaldo").email("Ronaldo@gmail.com").gender(Gender.MALE)
                .birthDate(LocalDate.of(1985, 6,1)).build();
        assertEquals(expectedUser, actualUser);
    }

    @Test
    void query() {
        SqlSpecification<User> sqlSpecification = new SelectUserLogin("pldi3");
        List<User> actual = repository.query(sqlSpecification);
        List<User> expected = new ArrayList<>();
        User user = User.builder().login("pldi3").password("qwerty").admin(false).firstname("Zinedin")
                .lastname("Zidane").email("zidane@gmail.com").gender(Gender.MALE)
                .birthDate(LocalDate.of(1975, 10,10)).build();
        expected.add(user);
        assertEquals(expected, actual);

    }
}