package by.platonov.music.repository.specification;

import by.platonov.music.db.DatabaseSetupExtension;
import by.platonov.music.entity.Genre;
import by.platonov.music.exception.RepositoryException;
import by.platonov.music.repository.GenreRepository;
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
class GenreIdSpecificationTest {

    @Test
    void toPreparedStatement() throws RepositoryException {
        List<Genre> actual = GenreRepository.getInstance().query(new GenreIdSpecification(1));
        List<Genre> expected = List.of(Genre.builder().id(1).title("pop").build());

        assertEquals(expected, actual);
    }
}