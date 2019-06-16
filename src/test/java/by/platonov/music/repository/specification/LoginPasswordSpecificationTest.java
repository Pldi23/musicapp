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
 * @author dzmitryplatonov on 2019-06-16.
 * @version 0.0.1
 */
@ExtendWith(DatabaseSetupExtension.class)
class LoginPasswordSpecificationTest {

    Repository<User> repository;
    SqlSpecification specification;

    @Test
    void toSqlClauses() throws RepositoryException {
        //given
        String login = "pldi";
        String password = "qwerty";
        specification = new LoginPasswordSpecification(login, password);
        repository = UserRepository.getInstance();

        //then
        int actual = repository.query(specification).size();
        int expected = 1;

        //when
        assertEquals(expected, actual);
    }
}