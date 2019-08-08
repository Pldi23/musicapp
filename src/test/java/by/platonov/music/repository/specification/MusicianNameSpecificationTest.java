package by.platonov.music.repository.specification;

import by.platonov.music.db.DatabaseSetupExtension;
import by.platonov.music.entity.Musician;
import by.platonov.music.exception.RepositoryException;
import by.platonov.music.repository.MusicianRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-08-08.
 * @version 0.0.1
 */
@ExtendWith(DatabaseSetupExtension.class)
class MusicianNameSpecificationTest {

    @Test
    void toPreparedStatement() throws RepositoryException {
        List<Musician> actual = MusicianRepository.getInstance().query(new MusicianNameSpecification("Avici"));
        List<Musician> expected = List.of(Musician.builder().id(1).name("Avici").build());

        assertEquals(expected, actual);
    }
}