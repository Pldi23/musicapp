package by.platonov.music.repository;

import by.platonov.music.db.DatabaseSetupExtension;
import by.platonov.music.entity.FilePartBean;
import by.platonov.music.entity.Genre;
import by.platonov.music.entity.Track;
import by.platonov.music.exception.FilePartBeanException;
import by.platonov.music.exception.RepositoryException;
import by.platonov.music.repository.specification.IdIsNotNullSpecification;
import by.platonov.music.repository.specification.SearchNameSpecification;
import by.platonov.music.repository.specification.TrackIdSpecification;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.File;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author dzmitryplatonov on 2019-06-07.
 * @version 0.0.1
 */
@ExtendWith(DatabaseSetupExtension.class)
class TrackRepositoryTest {

    private TrackRepository repository = TrackRepository.getInstance();
    private Track trackTim = Track.builder().id(1).name("Tim").genre(Genre.builder().id(1).title("pop").build())
            .releaseDate(LocalDate.of(2019, 1, 1))
//            .length(180)
//            .filePartBean(new FilePartBean(new File("/users/dzmitryplatonov/Dropbox/music/avicii-tim.mp3")))
//            .path(Path.of("/users/dzmitryplatonov/Dropbox/music/avicii-tim.mp3"))
            .singers(new HashSet<>())
            .authors(new HashSet<>()).build();

    private Track trackDuet = Track.builder().id(6).name("Duet").genre(Genre.builder().id(3).title("rap").build())
            .releaseDate(LocalDate.of(2019, 1,6))
//            .length(201)
            .singers(new HashSet<>())
            .authors(new HashSet<>())
//            .path(Path.of("/users/dzmitryplatonov/Dropbox/music/testfile.mp3"))
//            .filePartBean(new FilePartBean(new File("/users/dzmitryplatonov/Dropbox/music/testfile.mp3")))
            .build();

    private Track newTrackWithOldMusician = Track.builder().name("Oldcommer").genre(Genre.builder().id(1).title("pop").build())
            .releaseDate(LocalDate.of(2019, 1, 3))
//            .length(190)
            .singers(new HashSet<>())
            .authors(new HashSet<>())
//            .filePartBean(new FilePartBean(new File("/users/dzmitryplatonov/Dropbox/music/testfile.mp3")))
//            .path(Path.of("/users/dzmitryplatonov/Dropbox/music/testfile.mp3"))
            .build();

    TrackRepositoryTest() throws FilePartBeanException {
    }

    @Test
    void addShouldBeTrue() throws RepositoryException {
        assertTrue(repository.add(newTrackWithOldMusician));
    }

    @Test
    void addShouldBeFalse() throws RepositoryException {
        assertFalse(repository.add(trackTim));
    }

    @Test
    void addShouldIncreaseSize() throws RepositoryException {
        repository.add(newTrackWithOldMusician);
        assertEquals(7, repository.count(new IdIsNotNullSpecification()));
    }

    @Test
    void addShouldNotIncreaseSize() throws RepositoryException {
        repository.add(trackTim);
        assertEquals(6, repository.count(new IdIsNotNullSpecification()));
    }

    @Test
    void addTrackAndCheckEqual() throws RepositoryException {
        repository.add(trackDuet);
        Track actual = repository.query(new TrackIdSpecification(trackDuet.getId())).get(0);
        Track expected = trackDuet;
        assertEquals(expected, actual);
    }

    @Test
    void count() throws RepositoryException{
        long actual = repository.count(new IdIsNotNullSpecification());
        int expected = 6;
        assertEquals(expected, actual);
    }

    @Test
    void removeShouldBeTrue() throws RepositoryException {
        assertTrue(repository.remove(trackTim));
    }

    @Test
    void removeShouldBeFalse() throws RepositoryException {
        assertFalse(repository.remove(newTrackWithOldMusician));
    }

    @Test
    void removeShouldDecreaseSize() throws RepositoryException {
        repository.remove(trackTim);
        assertEquals(5, repository.count(new IdIsNotNullSpecification()));
    }

    @Test
    void removeShouldNotDecreaseSize() throws RepositoryException {
        repository.remove(newTrackWithOldMusician);
        assertEquals(6, repository.count(new IdIsNotNullSpecification()));
    }

    @Test
    void update() throws RepositoryException {
        //given
        newTrackWithOldMusician.setId(1);

        //when
        repository.update(newTrackWithOldMusician);

        //then
        Track actual = repository.query(new TrackIdSpecification(newTrackWithOldMusician.getId())).get(0);
        Track expected = newTrackWithOldMusician;

        assertEquals(expected, actual);
    }

    @Test
    void query() throws RepositoryException {
        List<Track> actual = repository.query(new SearchNameSpecification("T"));
        List<Track> expected = List.of(trackTim, trackDuet);
        assertEquals(expected, actual);
    }
}