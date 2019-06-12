package by.platonov.music.repository;

import by.platonov.music.db.DatabaseSetupExtension;
import by.platonov.music.entity.Genre;
import by.platonov.music.entity.Musician;
import by.platonov.music.entity.Track;
import by.platonov.music.exception.RepositoryException;
import by.platonov.music.repository.specification.SelectIdIsNotNullSpecification;
import by.platonov.music.repository.specification.SelectIdSpecification;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author dzmitryplatonov on 2019-06-07.
 * @version 0.0.1
 */
@ExtendWith(DatabaseSetupExtension.class)
class TrackRepositoryTest {

    private TrackRepository repository = new TrackRepository();
    Track trackTim = Track.builder().id(1).name("Tim").genre(Genre.builder().id(1).title("pop").build())
            .releaseDate(LocalDate.of(2019, 1, 1)).length(180)
            .singers(Set.of(Musician.builder().id(1).name("Avici").build()))
            .authors(Set.of(Musician.builder().id(1).name("Avici").build())).build();

    Track trackDuet = Track.builder().id(6).name("Duet").genre(Genre.builder().id(3).title("rap").build())
            .releaseDate(LocalDate.of(2019, 1,6))
            .length(201)
            .singers(Set.of(Musician.builder().id(3).name("Артур Пирожков").build(),
                    Musician.builder().id(7).name("Филипп Киркоров").build()))
            .authors(Set.of(Musician.builder().id(7).name("Филипп Киркоров").build(),
                    Musician.builder().id(6).name("Bethowen").build()))
            .build();
    Track newTrackWitnNewMusician = Track.builder().id(10).name("Newcommer").genre(Genre.builder().id(1).title("pop").build())
            .releaseDate(LocalDate.of(2019, 1, 3)).length(190)
            .singers(Set.of(Musician.builder().id(10).name("Newbie").build()))
            .authors(Set.of(Musician.builder().id(10).name("Newbie").build()))
            .build();
    Track newTrackWithOldMusician = Track.builder().name("Oldcommer").genre(Genre.builder().id(1).title("pop").build())
            .releaseDate(LocalDate.of(2019, 1, 3)).length(190)
            .singers(Set.of(Musician.builder().id(4).name("Saluki").build()))
            .authors(Set.of(Musician.builder().id(6).name("Bethowen").build()))
            .build();

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
        assertEquals(7, repository.count(new SelectIdIsNotNullSpecification()));
    }

    @Test
    void addShouldNotIncreaseSize() throws RepositoryException {
        repository.add(trackTim);
        assertEquals(6, repository.count(new SelectIdIsNotNullSpecification()));
    }

    @Test
    void addTrackAndCheckEqual() throws RepositoryException {
        repository.add(trackDuet);
        Track actual = repository.findOne(new SelectIdSpecification(trackDuet.getId())).get();
        Track expected = trackDuet;
    }

    @Test
    void findOne() throws RepositoryException {
        Track track = repository.findOne(new SelectIdSpecification(1)).get();
        assertEquals(trackTim, track);
    }

    @Test
    void findOneFindTrackWithTwoAuthorsAndTwoSingers() throws RepositoryException {
        Track track = repository.findOne(new SelectIdSpecification(6)).get();
        assertEquals(trackDuet, track);
    }

    @Test
    void findOneEmpty() throws RepositoryException{
        Optional<Track> track = repository.findOne(new SelectIdSpecification(10));
        assertEquals(Optional.empty(), track);
    }

    @Test
    void count() throws RepositoryException{
        int actual = repository.count(new SelectIdIsNotNullSpecification());
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
        assertEquals(5, repository.count(new SelectIdIsNotNullSpecification()));
    }

    @Test
    void removeShouldNotDecreaseSize() throws RepositoryException {
        repository.remove(newTrackWithOldMusician);
        assertEquals(6, repository.count(new SelectIdIsNotNullSpecification()));
    }

    @Test
    void update() throws RepositoryException {
        //given
        newTrackWithOldMusician.setId(1);

        //when
        repository.update(newTrackWithOldMusician);

        //then
        Track actual = repository.findOne(new SelectIdSpecification(newTrackWithOldMusician.getId())).get();
        Track expected = newTrackWithOldMusician;

        assertEquals(expected, actual);
    }

    @Test
    void query() throws RepositoryException {
        List<Track> actual = repository.query(() -> "track.id = 1 or track.id = 6;");
        List<Track> expected = List.of(trackTim, trackDuet);
        assertEquals(expected, actual);
    }
}