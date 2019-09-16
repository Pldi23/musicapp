package by.platonov.music.service;

import by.platonov.music.db.DatabaseSetupExtension;
import by.platonov.music.entity.User;
import by.platonov.music.exception.RepositoryException;
import by.platonov.music.exception.ServiceException;
import by.platonov.music.repository.Repository;
import by.platonov.music.repository.UserRepository;
import by.platonov.music.repository.specification.UserLoginSpecification;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author dzmitryplatonov on 2019-06-21.
 * @version 0.0.1
 */
@ExtendWith(DatabaseSetupExtension.class)
class UserServiceTest {

    private UserService service;

    private User user = User.builder()
            .login("pldi4")
            .password("qwerty")
            .admin(false)
            .firstname("Leo")
            .lastname("Messi")
            .email("messi@gmail.com")
            .gender(User.Gender.MALE)
            .registrationDate(LocalDate.now())
            .birthDate(LocalDate.of(1987, 1, 1))
            .playlists(new LinkedHashSet<>())
            .active(true)
            .verificationUuid(null)
            .photoPath("/usr/local/Cellar/tomcat/9.0.20/libexec/musicappfiles/photo/default_ava.png")
            .payments(new LinkedHashSet<>())
            .paidPeriod(LocalDateTime.of(2030,1,1,0,0))
            .build();

    @Test
    void activatePositive() throws ServiceException {

        service = new UserService();
        String email = "messi@gmail.com";
        String hash = "1";

        boolean actual = service.activate(email, hash);

        assertTrue(actual);
    }

    @Test
    void activatePositiveUserShouldBeActive() throws RepositoryException, ServiceException {

        Repository<User> repository = UserRepository.getInstance();
        service = new UserService();
        String email = "messi@gmail.com";
        String hash = "1";
        service.activate(email, hash);
        List<User> result = repository.query(new UserLoginSpecification("pldi4"));

        User actual = result.get(0);
        User expected = user;

        assertEquals(expected, actual);
    }

    @Test
    void activateNegative() throws ServiceException {

        service = new UserService();
        String email = "messi@gmail.com";
        String hash = "0";

        boolean actual = service.activate(email, hash);

        assertFalse(actual);
    }

//    @Test
//    void testRegisterPositive() throws ServiceException, RepositoryException {
//        service = new UserService();
//        UserRepository userRepository = Mockito.mock(UserRepository.class);
//        Mockito.when(userRepository.add(user)).thenReturn(true).thenAnswer();
//        assertTrue(service.register(user));
//    }
}