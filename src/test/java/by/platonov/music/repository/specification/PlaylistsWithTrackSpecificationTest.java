package by.platonov.music.repository.specification;

import by.platonov.music.db.DatabaseSetupExtension;
import by.platonov.music.entity.Playlist;
import by.platonov.music.exception.RepositoryException;
import by.platonov.music.repository.PlaylistRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-08-08.
 * @version 0.0.1
 */
@ExtendWith(DatabaseSetupExtension.class)
class PlaylistsWithTrackSpecificationTest {

    @Test
    void toPreparedStatement() throws RepositoryException {
        List<Playlist> actual = PlaylistRepository.getInstance().query(new PlaylistsWithTrackSpecification(4));
        List<Playlist> expected = List.of(
                Playlist.builder().id(1).name("spring2019").tracks(new LinkedList<>()).build(),
                Playlist.builder().id(5).name("new year party mix").tracks(new LinkedList<>()).build()
        );

        assertEquals(expected, actual);

    }
}