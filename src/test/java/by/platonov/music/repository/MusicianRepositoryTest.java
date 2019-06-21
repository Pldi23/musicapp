package by.platonov.music.repository;

import by.platonov.music.exception.RepositoryException;
import by.platonov.music.db.DatabaseSetupExtension;
import by.platonov.music.entity.Musician;
import by.platonov.music.repository.specification.MusicianIdSpecification;
import by.platonov.music.repository.specification.IdIsNotNullSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author dzmitryplatonov on 2019-06-09.
 * @version 0.0.1
 */
@ExtendWith(DatabaseSetupExtension.class)
class MusicianRepositoryTest {

    private MusicianRepository repository = MusicianRepository.getInstance();
    private Musician newMusician = Musician.builder().name("Bah").build();
    private Musician existsMusician = Musician.builder().name("Linkin Park").build();
    private Musician updatedMusician = Musician.builder().name("Linkin Park").build();

    @BeforeEach
    void setUp() {
        existsMusician.setId(5);
    }

    @Test
    void addShouldBeTrue() throws RepositoryException {
        assertTrue(repository.add(newMusician));
    }

    @Test
    void addShouldBeFalse() throws RepositoryException {
        assertFalse(repository.add(existsMusician));
    }

    @Test
    void addShouldIncreaseSize() throws RepositoryException {
        //when
        repository.add(newMusician);

        //then
        long actual = repository.count(new IdIsNotNullSpecification());
        long expected = 8;
        assertEquals(expected, actual);
    }

    @Test
    void addShouldNotIncreaseSize() throws RepositoryException {
        //when
        repository.add(existsMusician);

        //then
        long actual = repository.count(new IdIsNotNullSpecification());
        long expected = 7;
        assertEquals(expected, actual);
    }

    @Test
    void addSelectedMusicianShouldBeEqualAddedMusician() throws RepositoryException{
        //given
        long id = 8;
        newMusician.setId(id);

        //when
        repository.add(newMusician);
        Musician selectedMusician = repository.query(new MusicianIdSpecification(newMusician.getId())).get(0);

        //then
        assertEquals(newMusician, selectedMusician);
    }

    @Test
    void removeShouldBeTrue() throws RepositoryException {
        assertTrue(repository.remove(existsMusician));
    }

    @Test
    void removeShouldBeFalse() throws RepositoryException {
        assertFalse(repository.remove(newMusician));
    }

    @Test
    void removeShouldDecreaseSize() throws RepositoryException {
        //when
        repository.remove(existsMusician);

        //then
        long actual = repository.count(new IdIsNotNullSpecification());
        long expected = 6;
        assertEquals(expected, actual);
    }

    @Test
    void removeShouldNotDecreaseSize() throws RepositoryException {
        //when
        repository.remove(newMusician);

        //then
        long actual = repository.count(new IdIsNotNullSpecification());
        long expected = 7;
        assertEquals(expected, actual);
    }

    @Test
    void update() throws RepositoryException {
        updatedMusician.setId(5);
        repository.update(updatedMusician);
        Musician actual = repository.query(new MusicianIdSpecification(updatedMusician.getId())).get(0);
        Musician expected = updatedMusician;
        assertEquals(expected, actual);
    }

    @Test
    void query() throws RepositoryException {
        //given
        Musician musicianKirkorov = Musician.builder().id(7).name("Филипп Киркоров").build();
        Musician musicianBethowen = Musician.builder().id(6).name("Bethowen").build();

        //when
        List<Musician> actual = repository.query(()-> "where id > 5");
        List<Musician> expected = Arrays.asList(musicianBethowen, musicianKirkorov);

        //then
        assertEquals(expected, actual);

    }

    @Test
    void count() throws RepositoryException {
        long actual = repository.count(new IdIsNotNullSpecification());
        long expected = 7;
        assertEquals(expected, actual);
    }
}