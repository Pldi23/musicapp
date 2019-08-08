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
class TrackSortedGenreNameSpecificationTest {

    @Test
    void toPreparedStatement() throws RepositoryException {
        List<Track> actual = TrackRepository.getInstance()
                .query(new TrackSortedGenreNameSpecification(true, 2, 0));
        List<Track> expected = new ArrayList<>();
        expected.add(Track.builder().id(2).name("i_Suss").genre(Genre.builder().id(7).title("chanson").build())
                .releaseDate(LocalDate.of(2019, 2, 2)).length(195)
                .uuid("/users/dzmitryplatonov/Dropbox/music/leningrad_i_suss.mp3").authors(new HashSet<>())
                .singers(new HashSet<>()).createDate(LocalDate.now()).build());
        expected.add(Track.builder().id(3).name("Властелин калек").genre(Genre.builder().id(1).title("pop").build())
                .releaseDate(LocalDate.of(2018, 12, 1)).length(200)
                .uuid("/users/dzmitryplatonov/Dropbox/music/saluki_vlastelin_kalek.mp3").authors(new HashSet<>())
                .singers(new HashSet<>()).createDate(LocalDate.now()).build());

        assertEquals(expected, actual);
    }
}