package by.platonov.music.repository.specification;

import by.platonov.music.db.DatabaseSetupExtension;
import by.platonov.music.exception.RepositoryException;
import by.platonov.music.repository.TrackRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author dzmitryplatonov on 2019-06-13.
 * @version 0.0.1
 */
@ExtendWith(DatabaseSetupExtension.class)
class TracksInPlaylistSpecificationTest {

    SqlSpecification specification;
    TrackRepository repository = TrackRepository.getInstance();

    @Test
    void toSqlClauses() throws RepositoryException {
        //given
        long playlistId = 5;
        specification = new TracksInPlaylistSpecification(playlistId);

        //then
        int actual = repository.count(specification);
        int expected = 4;

        //when
        assertEquals(expected, actual);
    }
}