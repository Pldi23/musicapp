package by.platonov.music.repository;

import by.platonov.music.db.DatabaseSetupExtension;
import by.platonov.music.entity.Playlist;
import by.platonov.music.exception.RepositoryException;
import by.platonov.music.repository.specification.PlaylistIdSpecification;
import by.platonov.music.repository.specification.IdIsNotNullSpecification;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author dzmitryplatonov on 2019-06-12.
 * @version 0.0.1
 */
@ExtendWith(DatabaseSetupExtension.class)
class PlaylistRepositoryTest {

    PlaylistRepository repository = PlaylistRepository.getInstance();
//    Genre pop = Genre.builder().id(1).title("pop").build();
//    Genre chanson = Genre.builder().id(7).title("chanson").build();
//
//
//    Track zacepila = Track.builder().id(4).name("Зацепила").genre(pop)
//            .releaseDate(LocalDate.of(2019, 3, 11)).length(185)
//            .singers(new HashSet<>())
//            .authors(new HashSet<>()).build();
//    Track tim = Track.builder().id(1).name("Tim").genre(pop)
//            .releaseDate(LocalDate.of(2019, 1, 1)).length(180)
//            .singers(new HashSet<>())
//            .authors(new HashSet<>()).build();
//    Track isuss = Track.builder().id(2).name("i_Suss").genre(chanson)
//            .releaseDate(LocalDate.of(2019, 2, 2)).length(195)
//            .singers(new HashSet<>())
//            .authors(new HashSet<>()).build();
//    Track vlastelin = Track.builder().id(3).name("Властелин калек").genre(pop)
//            .releaseDate(LocalDate.of(2018, 12, 1)).length(200)
//            .singers(new HashSet<>())
//            .authors(new HashSet<>()).build();

    Playlist spring2019 = Playlist.builder().id(1).name("spring2019").tracks(new HashSet<>()).build();
    Playlist newyearMix = Playlist.builder().id(5).name("new year party mix").tracks(new HashSet<>()).build();
    Playlist newPlaylist = Playlist.builder().id(6).name("new playlist").tracks(new HashSet<>()).build();

    @Test
    void addShouldBeTrue() throws RepositoryException {
        assertTrue(repository.add(newPlaylist));
    }

    @Test
    void addShouldBeFalse() throws RepositoryException {
        assertFalse(repository.add(spring2019));
    }

    @Test
    void addShouldIncreaseSize() throws RepositoryException {
        repository.add(newPlaylist);
        assertEquals(6, repository.count(new IdIsNotNullSpecification()));
    }

    @Test
    void addShouldNotIncreaseSize() throws RepositoryException {
        repository.add(spring2019);
        assertEquals(5, repository.count(new IdIsNotNullSpecification()));
    }

    @Test
    void removeShouldBeTrue() throws RepositoryException {
        assertTrue(repository.remove(spring2019));
    }

    @Test
    void removeShouldBeFalse() throws RepositoryException {
        assertFalse(repository.remove(newPlaylist));
    }

    @Test
    void removeShouldDecreaseSize() throws RepositoryException {
        repository.remove(spring2019);
        assertEquals(4, repository.count(new IdIsNotNullSpecification()));
    }

    @Test
    void removeShouldNotDecreaseSize() throws RepositoryException {
        repository.remove(newPlaylist);
        assertEquals(5, repository.count(new IdIsNotNullSpecification()));
    }

    @Test
    void update() throws RepositoryException {
        //given
        newPlaylist.setId(1);

        //when
        repository.update(newPlaylist);

        //then
        Playlist actual = repository.query(new PlaylistIdSpecification(newPlaylist.getId())).get(0);
        Playlist expected = newPlaylist;

        assertEquals(expected, actual);
    }

    @Test
    void addPlaylistAndCheckEqual() throws RepositoryException {
        repository.add(newPlaylist);
        Playlist actual = repository.query(new PlaylistIdSpecification(newPlaylist.getId())).get(0);
        Playlist expected = newPlaylist;
        assertEquals(expected, actual);
    }

    @Test
    void count() throws RepositoryException {
        long actual = repository.count(() -> "where id is not null;");
        long expected = 5;
        assertEquals(expected, actual);
    }

    @Test
    void query() throws RepositoryException {
        List<Playlist> actual = repository.query(() -> "where playlist.id = 1 or playlist.id = 5");
        List<Playlist> expected = List.of(spring2019, newyearMix);
        assertEquals(expected, actual);
    }
}