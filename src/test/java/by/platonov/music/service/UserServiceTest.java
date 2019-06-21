package by.platonov.music.service;

import by.platonov.music.db.DatabaseSetupExtension;
import by.platonov.music.entity.Gender;
import by.platonov.music.entity.User;
import by.platonov.music.exception.RepositoryException;
import by.platonov.music.repository.Repository;
import by.platonov.music.repository.UserRepository;
import by.platonov.music.repository.specification.UserLoginSpecification;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author dzmitryplatonov on 2019-06-21.
 * @version 0.0.1
 */
@ExtendWith(DatabaseSetupExtension.class)
class UserServiceTest {

    UserService service;

    @Test
    void activatePositive() throws RepositoryException {
        service = new UserService();
        String email = "messi@gmail.com";
        String hash = "1";
        boolean actual = service.activate(email, hash);
        assertTrue(actual);
    }

    @Test
    void activatePositiveUserShouldBeActive() throws RepositoryException {
        Repository<User> repository = UserRepository.getInstance();
        service = new UserService();
        String email = "messi@gmail.com";
        String hash = "1";
        service.activate(email, hash);
        List<User> result = repository.query(new UserLoginSpecification("pldi4"));
        User actual = result.get(0);
        User expected = User.builder().login("pldi4").password("qwerty").admin(false).firstname("Leo")
                .lastname("Messi").email("messi@gmail.com").gender(Gender.MALE)
                .registrationDate(LocalDate.of(2019, 6, 21))
                .birthDate(LocalDate.of(1987, 1, 1)).playlists(new HashSet<>())
                .active(true)
                .hash(null).build();
        assertEquals(expected, actual);
    }

    @Test
    void activateNegative() throws RepositoryException {
        service = new UserService();
        String email = "messi@gmail.com";
        String hash = "0";
        boolean actual = service.activate(email, hash);
        assertFalse(actual);
    }
}