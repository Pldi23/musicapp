package by.platonov.music.repository;

import by.platonov.music.exception.RepositoryException;
import by.platonov.music.db.DatabaseSetupExtension;
import by.platonov.music.entity.Gender;
import by.platonov.music.entity.User;
import by.platonov.music.repository.specification.UserLoginSpecification;
import by.platonov.music.repository.specification.SqlSpecification;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author dzmitryplatonov on 2019-06-08.
 * @version 0.0.1
 */
@ExtendWith(DatabaseSetupExtension.class)
class UserRepositoryTest {

    UserRepository repository = UserRepository.getInstance();
    User newUser = User.builder().login("pldi6").password("pldi6").admin(true).firstname("Dima").lastname("Plat")
            .gender(Gender.MALE).email("pl@pl.ru").birthDate(LocalDate.of(1986, 7, 2))
            .playlists(new HashSet<>()).build();
    User updatedUser = User.builder().login("pldi4").password("Ronaldo").admin(false).firstname("Cristiano")
            .lastname("Ronaldo").email("Ronaldo@gmail.com").gender(Gender.MALE)
            .birthDate(LocalDate.of(1985, 6,1)).playlists(new HashSet<>()).build();
    User selectedUser = User.builder().login("pldi3").password("qwerty").admin(false).firstname("Zinedin")
            .lastname("Zidane").email("zidane@gmail.com").gender(Gender.MALE)
            .birthDate(LocalDate.of(1975, 10,10)).playlists(new HashSet<>()).build();

    @Test
    void addShouldBeTrue() throws RepositoryException {
        assertTrue(repository.add(newUser));
    }

    @Test
    void addShouldIncreaseSize() throws RepositoryException {
        repository.add(newUser);
        long actualSize = repository.count(() -> "where login is not null");
        int expectedSize = 6;
        assertEquals(expectedSize, actualSize);
    }

    @Test
    void addSelectedUserShouldBeEqualToAddedUser() throws RepositoryException{
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
        long actualSize = repository.count(() -> "where login is not null");
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
        long actual = repository.count(() -> "where login is not null");
        int expected = 4;
        assertEquals(expected, actual);
    }

    @Test
    void update() throws RepositoryException{
        repository.update(updatedUser);
        User actualUser = repository.query(() -> "where login = 'pldi4'").get(0);
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
        assertEquals(expected, repository.count(() -> "where login is not null"));
    }
//
//    @Test
//    void findOneExistingUser() throws RepositoryException {
//        Optional<User> actual = repository.findOne(() -> "where login = 'pldi3'");
//        Optional<User> expected = Optional.of(selectedUser);
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    void findOneNonExistingUser() throws RepositoryException {
//        Optional<User> actual = repository.findOne(() -> "where login = 'pldi11'");
//        Optional<User> expected = Optional.empty();
//        assertEquals(expected, actual);
//    }
}