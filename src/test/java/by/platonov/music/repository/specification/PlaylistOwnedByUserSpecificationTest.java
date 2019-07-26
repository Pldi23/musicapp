package by.platonov.music.repository.specification;

import by.platonov.music.db.DatabaseSetupExtension;
import by.platonov.music.entity.Playlist;
import by.platonov.music.exception.RepositoryException;
import by.platonov.music.repository.PlaylistRepository;
import by.platonov.music.repository.Repository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author dzmitryplatonov on 2019-06-14.
 * @version 0.0.1
 */
@ExtendWith(DatabaseSetupExtension.class)
class PlaylistOwnedByUserSpecificationTest {


    @Test
    void toSqlClauses() throws RepositoryException {
        //given
        SqlSpecification specification;
        Repository<Playlist> repository;
        String login = "pldi1";
        specification = new PlaylistOwnedByUserSpecification(login);
        repository = PlaylistRepository.getInstance();

        //then
        int actual = repository.query(specification).size();
        int expected = 2;

        //when
        assertEquals(expected, actual);
    }
}