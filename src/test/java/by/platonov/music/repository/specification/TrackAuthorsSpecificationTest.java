package by.platonov.music.repository.specification;

import by.platonov.music.entity.Musician;
import by.platonov.music.exception.RepositoryException;
import by.platonov.music.repository.MusicianRepository;
import by.platonov.music.repository.Repository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author dzmitryplatonov on 2019-06-14.
 * @version 0.0.1
 */
class TrackAuthorsSpecificationTest {

    @Test
    void toSqlClauses() throws RepositoryException {
        //Given
        long trackId = 6;
        SqlSpecification specification = new TrackAuthorsSpecification(trackId);
        Repository<Musician> repository = MusicianRepository.getInstance();

        //When
        long actual = repository.count(specification);
        long expected = 2;

        //Then
        assertEquals(expected, actual);
    }
}