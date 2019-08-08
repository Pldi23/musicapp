package by.platonov.music.repository.specification;

import by.platonov.music.db.DatabaseSetupExtension;
import by.platonov.music.entity.Playlist;
import by.platonov.music.entity.User;
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
class PlaylistByNameAndUserTypeLimitOffsetSpecificationTest {

    @Test
    void toPreparedStatement() throws RepositoryException {
        User user = User.builder().login("admin").admin(true).build();
        List<Playlist> actual = PlaylistRepository.getInstance()
                .query(new PlaylistByNameAndUserTypeLimitOffsetSpecification("spring", Integer.MAX_VALUE, 0, user));

        List<Playlist> expected = List.of(Playlist.builder().id(1).name("spring2019").tracks(new ArrayList<>()).personal(false).build());

        assertEquals(expected, actual);
    }
}