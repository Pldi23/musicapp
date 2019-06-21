package by.platonov.music.repository.specification;

import by.platonov.music.db.DatabaseSetupExtension;
import by.platonov.music.entity.User;
import by.platonov.music.exception.RepositoryException;
import by.platonov.music.repository.Repository;
import by.platonov.music.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author dzmitryplatonov on 2019-06-21.
 * @version 0.0.1
 */
@ExtendWith(DatabaseSetupExtension.class)
class UserEmailHashSpecificationTest {

    @Test
    void toSqlClauses() throws RepositoryException {
        //given
        String email = "messi@gmail.com";
        String hash = "1";
        SqlSpecification specification = new UserEmailHashSpecification(email, hash);
        Repository<User> repository = UserRepository.getInstance();

        //then
        int actual = repository.query(specification).size();
        int expected = 1;

        //when
        assertEquals(expected, actual);
    }
}