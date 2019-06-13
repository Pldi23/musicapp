package by.platonov.music.repository.specification;

import by.platonov.music.db.DatabaseSetupExtension;
import by.platonov.music.entity.Musician;
import by.platonov.music.exception.RepositoryException;
import by.platonov.music.repository.MusicianRepository;
import by.platonov.music.repository.Repository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author dzmitryplatonov on 2019-06-14.
 * @version 0.0.1
 */
@ExtendWith(DatabaseSetupExtension.class)
class TrackSingersSpecificationTest {

    @Test
    void toSqlClauses() throws RepositoryException {
        //given
        long trackId = 1;
        SqlSpecification specification = new TrackSingersSpecification(trackId);
        Repository<Musician> repository = MusicianRepository.getInstance();

        //when
        int actual = repository.query(specification).size();
        int expected = 1;

        //then
        assertEquals(expected, actual);
    }
}