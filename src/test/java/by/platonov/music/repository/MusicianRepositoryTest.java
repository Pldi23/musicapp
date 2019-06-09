package by.platonov.music.repository;

import by.platonov.music.exception.RepositoryException;
import by.platonov.music.db.DatabaseSetupExtension;
import by.platonov.music.entity.Musician;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author dzmitryplatonov on 2019-06-09.
 * @version 0.0.1
 */
@ExtendWith(DatabaseSetupExtension.class)
class MusicianRepositoryTest {

    MusicianRepository repository = new MusicianRepository();
    Musician newMusician = Musician.builder().name("Bah").singer(false).author(true).build();

    @Test
    void addShouldBeTrue() throws RepositoryException {
        assertTrue(repository.add(newMusician));
    }

    @Test
    void addShouldIncreaseSize() throws RepositoryException {
        repository.add(newMusician);
        int actual = repository.size();
        int expected = 8;
        assertEquals(expected, actual);
    }

    @Test
    void size() throws RepositoryException {
        int actual = repository.size();
        int expected = 7;
        assertEquals(expected, actual);
    }
}