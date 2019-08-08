package by.platonov.music.repository;

import by.platonov.music.exception.RepositoryException;
import by.platonov.music.db.DatabaseSetupExtension;
import by.platonov.music.entity.User;
import by.platonov.music.repository.specification.UserLoginIsNotNullSpecification;
import by.platonov.music.repository.specification.UserLoginPasswordSpecification;
import by.platonov.music.repository.specification.UserLoginSpecification;
import by.platonov.music.repository.specification.SqlSpecification;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author dzmitryplatonov on 2019-06-08.
 * @version 0.0.1
 */
@ExtendWith(DatabaseSetupExtension.class)
class UserRepositoryTest {

    private UserRepository repository = UserRepository.getInstance();
    private User newUser = User.builder().login("pldi6").password("pldi6").admin(true).firstname("Dima").lastname("Plat")
            .gender(User.Gender.MALE).email("pl@pl.ru").birthDate(LocalDate.of(1986, 7, 2))
            .playlists(new LinkedHashSet<>()).registrationDate(LocalDate.now())
            .active(false)
            .verificationUuid(null)
            .payments(new LinkedHashSet<>())
            .photoPath("/usr/local/Cellar/tomcat/9.0.20/libexec/musicappfiles/photo/default_ava.png")
            .paidPeriod(LocalDateTime.of(2030, 1, 1, 0, 0))
            .build();
    private User updatedUser = User.builder().login("pldi4").password("Ronaldo").admin(false).firstname("Cristiano")
            .lastname("Ronaldo").email("Ronaldo@gmail.com").gender(User.Gender.MALE)
            .registrationDate(LocalDate.now())
            .birthDate(LocalDate.of(1985, 6, 1)).playlists(new LinkedHashSet<>())
            .active(false)
            .verificationUuid(null)
            .payments(new LinkedHashSet<>())
            .photoPath("/usr/local/Cellar/tomcat/9.0.20/libexec/musicappfiles/photo/default_ava.png")
            .paidPeriod(LocalDateTime.of(2030, 1, 1, 0, 0))
            .build();
    private User selectedUser = User.builder().login("pldi3").password("qwerty").admin(false).firstname("Zinedin")
            .lastname("Zidane").email("pldi@mail.ru").gender(User.Gender.MALE)
            .registrationDate(LocalDate.now())
            .birthDate(LocalDate.of(1975, 10, 10)).playlists(new LinkedHashSet<>())
            .active(false)
            .verificationUuid("1")
            .payments(new LinkedHashSet<>())
            .photoPath("/usr/local/Cellar/tomcat/9.0.20/libexec/musicappfiles/photo/default_ava.png")
            .paidPeriod(LocalDateTime.of(2030, 1, 1, 0, 0))
            .build();

    @Test
    void addShouldBeTrue() throws RepositoryException {
        assertTrue(repository.add(newUser));
    }

    @Test
    void addShouldIncreaseSize() throws RepositoryException {
        repository.add(newUser);
        long actualSize = repository.count(new UserLoginIsNotNullSpecification());
        int expectedSize = 6;
        assertEquals(expectedSize, actualSize);
    }

    @Test
    void addSelectedUserShouldBeEqualToAddedUser() throws RepositoryException {
        repository.add(newUser);
        User actual = repository.query(new UserLoginSpecification(newUser.getLogin())).get(0);
        User expected = newUser;
        assertEquals(expected, actual);
    }

    @Test
    void addExistingUserShouldBeFalse() throws RepositoryException {
        assertFalse(repository.add(updatedUser));
    }

    @Test
    void addExistingUserShouldNotIncreaseSize() throws RepositoryException {
        repository.add(updatedUser);
        long actualSize = repository.count(new UserLoginIsNotNullSpecification());
        int expectedSize = 5;
        assertEquals(expectedSize, actualSize);
    }

    @Test
    void removeShouldBeTrue() throws RepositoryException {
        assertTrue(repository.remove(selectedUser));
    }

    @Test
    void removeShouldBeFalse() throws RepositoryException {
        assertFalse(repository.remove(newUser));
    }

    @Test
    void removeShouldDecreaseSize() throws RepositoryException {
        repository.remove(selectedUser);
        long actual = repository.count(new UserLoginIsNotNullSpecification());
        int expected = 4;
        assertEquals(expected, actual);
    }

    @Test
    void update() throws RepositoryException {
        repository.update(updatedUser);
        User actualUser = repository.query(new UserLoginSpecification("pldi4")).get(0);
        User expectedUser = updatedUser;
        assertEquals(expectedUser, actualUser);
    }

    @Test
    void query() throws RepositoryException {
        //given
        SqlSpecification sqlSpecification = new UserLoginSpecification("pldi3");
        User user = selectedUser;

        //when
        List<User> actual = repository.query(sqlSpecification);
        List<User> expected = List.of(user);

        //then
        assertEquals(expected, actual);
    }

    @Test
    void count() throws RepositoryException {
        int expected = 5;
        assertEquals(expected, repository.count(new UserLoginIsNotNullSpecification()));
    }

    @Test
    void shouldPreventSqlInjection() throws RepositoryException {
        //given
        UserLoginPasswordSpecification spec = new UserLoginPasswordSpecification("pldi", "qwerty'; " +
                "truncate table user_playlist; truncate table application_user;");

        //when
        repository.query(spec);
        long actual = repository.count(new UserLoginIsNotNullSpecification());

        //then
        assertEquals(5, actual);
    }
}