package by.platonov.music.repository.specification;

import by.platonov.music.db.DatabaseSetupExtension;
import by.platonov.music.exception.RepositoryException;
import by.platonov.music.repository.TrackRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-08-08.
 * @version 0.0.1
 */
@ExtendWith(DatabaseSetupExtension.class)
class TrackIdIsNotNullSpecificationTest {

    @Test
    void toPreparedStatement() throws RepositoryException {
        long actual = TrackRepository.getInstance().count(new TrackIdIsNotNullSpecification());
        long expected = 6;

        assertEquals(expected, actual);
    }
}