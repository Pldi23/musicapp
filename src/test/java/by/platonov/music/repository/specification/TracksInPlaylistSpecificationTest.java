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

    private TrackRepository repository = TrackRepository.getInstance();

    @Test
    void toSqlClauses() throws RepositoryException {
        //given
        long playlistId = 5;
        SqlSpecification specification = new TracksInPlaylistSpecification(playlistId);

        //then
        long actual = repository.query(specification).size();
        long expected = 4;

        //when
        assertEquals(expected, actual);
    }
}