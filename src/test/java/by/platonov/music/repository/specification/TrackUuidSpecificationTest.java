package by.platonov.music.repository.specification;

import by.platonov.music.db.DatabaseSetupExtension;
import by.platonov.music.entity.Genre;
import by.platonov.music.entity.Track;
import by.platonov.music.exception.RepositoryException;
import by.platonov.music.repository.TrackRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Dzmitry Platonov on 2019-08-08.
 * @version 0.0.1
 */
@ExtendWith(DatabaseSetupExtension.class)
class TrackUuidSpecificationTest {

    @Test
    void toPreparedStatement() throws RepositoryException {
        List<Track> actual = TrackRepository.getInstance()
                .query(new TrackUuidSpecification("/users/dzmitryplatonov/Dropbox/music/avicii-tim.mp3"));
        List<Track> expected = List.of(Track.builder().id(1).name("Tim").genre(Genre.builder().id(1).title("pop").build())
                .releaseDate(LocalDate.of(2019, 1, 1)).length(180)
                .uuid("/users/dzmitryplatonov/Dropbox/music/avicii-tim.mp3").authors(new HashSet<>())
                .singers(new HashSet<>()).createDate(LocalDate.now()).build());

        assertEquals(expected, actual);
    }
}