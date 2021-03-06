package by.platonov.music.repository;

import by.platonov.music.db.DatabaseSetupExtension;
import by.platonov.music.db.DbInMemoryH2SetupExtension;
import by.platonov.music.entity.Playlist;
import by.platonov.music.exception.RepositoryException;
import by.platonov.music.repository.specification.IdOrClauseSpecification;
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
@ExtendWith(DbInMemoryH2SetupExtension.class)
class PlaylistRepositoryTest {

    private PlaylistRepository repository = PlaylistRepository.getInstance();

    private Playlist spring2019 = Playlist.builder().id(1).name("spring2019").tracks(new LinkedList<>()).build();
    private Playlist newyearMix = Playlist.builder().id(5).name("new year party mix").tracks(new LinkedList<>()).build();
    private Playlist newPlaylist = Playlist.builder().id(6).name("new playlist").tracks(new LinkedList<>()).build();

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
        long actual = repository.count(new IdIsNotNullSpecification());
        long expected = 5;
        assertEquals(expected, actual);
    }

    @Test
    void query() throws RepositoryException {
        List<Playlist> actual = repository.query(new IdOrClauseSpecification(1, 5));
        List<Playlist> expected = List.of(spring2019, newyearMix);
        assertEquals(expected, actual);
    }
}