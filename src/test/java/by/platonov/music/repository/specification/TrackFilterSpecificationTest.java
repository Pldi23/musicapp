package by.platonov.music.repository.specification;

import by.platonov.music.db.DatabaseSetupExtension;
import by.platonov.music.entity.filter.TrackFilter;
import by.platonov.music.exception.RepositoryException;
import by.platonov.music.repository.TrackRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-27.
 * @version 0.0.1
 */
@ExtendWith(DatabaseSetupExtension.class)
class TrackFilterSpecificationTest {

    private TrackRepository trackRepository;
    private TrackFilterSpecification specification;
    private TrackFilter filter;

    @Test
    void toPreparedStatement() throws RepositoryException {
        trackRepository = TrackRepository.getInstance();
        filter = TrackFilter.builder()
                .trackName("")
                .genreName("pop")
                .singerName("")
                .fromDate(LocalDate.of(1900,1,1))
                .toDate(LocalDate.of(2019,7,20))
                .build();
        specification = new TrackFilterSpecification(filter, Integer.MAX_VALUE, 0);
        long actual = trackRepository.query(specification).size();
        long expected = 3;
        assertEquals(expected, actual);
    }
}