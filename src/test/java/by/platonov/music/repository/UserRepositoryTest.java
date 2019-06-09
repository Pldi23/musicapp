package by.platonov.music.repository;

import by.platonov.music.exception.RepositoryException;
import by.platonov.music.db.DatabaseSetupExtension;
import by.platonov.music.entity.Gender;
import by.platonov.music.entity.User;
import by.platonov.music.repository.specification.SelectUserLoginSpecification;
import by.platonov.music.repository.specification.SqlSpecification;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author dzmitryplatonov on 2019-06-08.
 * @version 0.0.1
 */
@ExtendWith(DatabaseSetupExtension.class)
class UserRepositoryTest {

    UserRepository repository = new UserRepository();
    User newUser = User.builder().login("pldi6").password("pldi6").admin(true).firstname("Dima").lastname("Plat")
            .gender(Gender.MALE).email("pl@pl.ru").birthDate(LocalDate.of(1986, 7, 2)).build();
    User updatedUser = User.builder().login("pldi4").password("Ronaldo").admin(false).firstname("Cristiano")
            .lastname("Ronaldo").email("Ronaldo@gmail.com").gender(Gender.MALE)
            .birthDate(LocalDate.of(1985, 6,1)).build();
    User selectedUser = User.builder().login("pldi3").password("qwerty").admin(false).firstname("Zinedin")
            .lastname("Zidane").email("zidane@gmail.com").gender(Gender.MALE)
            .birthDate(LocalDate.of(1975, 10,10)).build();

    @Test
    void addShouldBeTrue() throws RepositoryException {
        assertTrue(repository.add(newUser));
    }

    @Test
    void addShouldIncreaseSize() throws RepositoryException {
        repository.add(newUser);
        int actualSize = repository.size();
        int expectedSize = 6;
        assertEquals(expectedSize, actualSize);
    }

    @Test
    void addSelectedUserShouldBeEqualToAddedUser() throws RepositoryException{
        repository.add(newUser);
        User actual = repository.select(newUser.getLogin()).get();
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
        int actualSize = repository.size();
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
        int actual = repository.size();
        int expected = 4;
        assertEquals(expected, actual);
    }

    @Test
    void update() throws RepositoryException{
        repository.update(updatedUser);
        User actualUser = repository.select("pldi4").get();
        User expectedUser = updatedUser;
        assertEquals(expectedUser, actualUser);
    }

    @Test
    void query() throws RepositoryException {
        //given
        SqlSpecification<User> sqlSpecification = new SelectUserLoginSpecification("pldi3");
        User user = selectedUser;

        //when
        List<User> actual = repository.query(sqlSpecification);
        List<User> expected = Arrays.asList(user);

        //then
        assertEquals(expected, actual);
    }

    @Test
    void size() throws RepositoryException {
        int expected = 5;
        assertEquals(expected, repository.size());
    }

    @Test
    void selectExistingUser() throws RepositoryException {
        Optional<User> actual = repository.select("pldi3");
        Optional<User> expected = Optional.of(selectedUser);
        assertEquals(expected, actual);
    }

    @Test
    void selectNonExistingUser() throws RepositoryException {
        Optional<User> actual = repository.select("pldi11");
        Optional<User> expected = Optional.empty();
        assertEquals(expected, actual);
    }
}