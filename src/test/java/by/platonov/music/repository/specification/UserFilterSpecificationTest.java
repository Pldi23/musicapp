package by.platonov.music.repository.specification;

import by.platonov.music.db.DatabaseSetupExtension;
import by.platonov.music.entity.User;
import by.platonov.music.entity.filter.UserFilter;
import by.platonov.music.exception.RepositoryException;
import by.platonov.music.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Dzmitry Platonov on 2019-08-08.
 * @version 0.0.1
 */
@ExtendWith(DatabaseSetupExtension.class)
class UserFilterSpecificationTest {

    @Test
    void toPreparedStatement() throws RepositoryException {
        UserFilter filter = UserFilter.builder().login("pldi").email("pldi@mail.ru").firstname("Dima").lastname("Platonov")
                .role(true)
                .birthdateFrom(LocalDate.of(1900,1,1))
                .birthdateTo(LocalDate.now())
                .registrationFrom(LocalDate.of(1900,1,1))
                .regisrationTo(LocalDate.of(2030,1,1))
                .build();

        List<User> actual = UserRepository.getInstance().query(new UserFilterSpecification(filter, Integer.MAX_VALUE, 0));

        List<User> expected = List.of(User.builder().login("pldi").password("qwerty").admin(true).firstname("Dima")
                .lastname("Platonov").email("pldi@mail.ru").birthDate(LocalDate.of(1986, 7, 2))
                .gender(User.Gender.MALE).active(false).verificationUuid(null).photoPath(null).registrationDate(LocalDate.now())
                .playlists(new LinkedHashSet<>()).payments(new LinkedHashSet<>())
                .paidPeriod(LocalDateTime.of(2030, 1, 1, 0, 0)).build());

        assertEquals(expected, actual);
    }
}