package by.platonov.music.repository.specification;

import by.platonov.music.db.DatabaseSetupExtension;
import by.platonov.music.entity.Genre;
import by.platonov.music.entity.Track;
import by.platonov.music.exception.RepositoryException;
import by.platonov.music.repository.TrackRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-08-08.
 * @version 0.0.1
 */
@ExtendWith(DatabaseSetupExtension.class)
class TrackSortedLengthSpecificationTest {

    @Test
    void toPreparedStatement() throws RepositoryException {
        List<Track> actual = TrackRepository.getInstance()
                .query(new TrackSortedLengthSpecification(true, 2, 0));
        List<Track> expected = new ArrayList<>();
        expected.add(Track.builder().id(1).name("Tim").genre(Genre.builder().id(1).title("pop").build())
                .releaseDate(LocalDate.of(2019, 1, 1)).length(180)
                .uuid("/users/dzmitryplatonov/Dropbox/music/avicii-tim.mp3").authors(new HashSet<>())
                .singers(new HashSet<>()).createDate(LocalDate.now()).build());
        expected.add(Track.builder().id(4).name("Зацепила").genre(Genre.builder().id(1).title("pop").build())
                .releaseDate(LocalDate.of(2019, 3, 11)).length(185)
                .uuid("/users/dzmitryplatonov/Dropbox/music/artur_pirozhkov_zacepila.mp3").authors(new HashSet<>())
                .singers(new HashSet<>()).createDate(LocalDate.now()).build());


        assertEquals(expected, actual);
    }
}