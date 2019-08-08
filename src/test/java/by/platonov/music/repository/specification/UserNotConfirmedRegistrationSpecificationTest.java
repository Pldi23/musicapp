package by.platonov.music.repository.specification;

import by.platonov.music.db.DatabaseSetupExtension;
import by.platonov.music.entity.User;
import by.platonov.music.exception.RepositoryException;
import by.platonov.music.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-08-08.
 * @version 0.0.1
 */
@ExtendWith(DatabaseSetupExtension.class)
class UserNotConfirmedRegistrationSpecificationTest {

    @Test
    void toPreparedStatement() throws RepositoryException {
        List<User> actual = UserRepository.getInstance().query(new UserNotConfirmedRegistrationSpecification());
        List<User> expected = new ArrayList<>();
        assertEquals(expected, actual);
    }
}