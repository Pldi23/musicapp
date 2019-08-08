package by.platonov.music.repository.specification;

import by.platonov.music.db.DatabaseSetupExtension;
import by.platonov.music.entity.Playlist;
import by.platonov.music.exception.RepositoryException;
import by.platonov.music.repository.PlaylistRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-08-08.
 * @version 0.0.1
 */
@ExtendWith(DatabaseSetupExtension.class)
class PlaylistByTrackAndUserSpecificationTest {

    @Test
    void toPreparedStatement() throws RepositoryException {
        List<Playlist> actual = PlaylistRepository.getInstance()
                .query(new PlaylistByTrackAndUserSpecification(4, "pldi1"));

        List<Playlist> expected = List.of(
                Playlist.builder().id(1).name("spring2019").personal(false).tracks(new ArrayList<>()).build(),
                Playlist.builder().id(5).name("new year party mix").tracks(new ArrayList<>()).build());

        assertEquals(expected, actual);
    }
}