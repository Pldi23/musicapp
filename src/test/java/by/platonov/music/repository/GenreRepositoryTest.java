package by.platonov.music.repository;

import by.platonov.music.db.DatabaseSetupExtension;
import by.platonov.music.db.DbInMemoryH2SetupExtension;
import by.platonov.music.entity.Genre;
import by.platonov.music.exception.RepositoryException;
import by.platonov.music.repository.specification.GenreIdSpecification;
import by.platonov.music.repository.specification.IdIsNotNullSpecification;
import by.platonov.music.repository.specification.IdMoreThanSpecification;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author dzmitryplatonov on 2019-06-11.
 * @version 0.0.1
 */
@ExtendWith(DbInMemoryH2SetupExtension.class)
class GenreRepositoryTest {

    private GenreRepository repository = GenreRepository.getInstance();
    private Genre newGenre = Genre.builder().title("unknown").build();
    private Genre existsGenre = Genre.builder().id(1).title("pop").build();
    private Genre updatedGenre = Genre.builder().id(1).title("new").build();

    @Test
    void addShouldBeTrue() throws RepositoryException {
        assertTrue(repository.add(newGenre));
    }

    @Test
    void addShouldBeFalse() throws RepositoryException {
        assertFalse(repository.add(existsGenre));
    }

    @Test
    void addShouldIncreaseSize() throws RepositoryException {
        //when
        repository.add(newGenre);

        //then
        long actual = repository.count(new IdIsNotNullSpecification());
        long expected = 8;
        assertEquals(expected, actual);
    }

    @Test
    void addShouldNotIncreaseSize() throws RepositoryException {
        //when
        repository.add(existsGenre);

        //then
        long actual = repository.count(new IdIsNotNullSpecification());
        long expected = 7;
        assertEquals(expected, actual);
    }

    @Test
    void addSelectedMusicianShouldBeEqualAddedMusician() throws RepositoryException{
        //given
        long id = 8;
        newGenre.setId(id);

        //when
        repository.add(newGenre);
        Genre selectedGenre = repository.query(new GenreIdSpecification(newGenre.getId())).get(0);

        //then
        assertEquals(newGenre, selectedGenre);
    }

    @Test
    void removeShouldBeTrue() throws RepositoryException {
        assertTrue(repository.remove(existsGenre));
    }

    @Test
    void removeShouldBeFalse() throws RepositoryException {
        assertFalse(repository.remove(newGenre));
    }

    @Test
    void removeShouldDecreaseSize() throws RepositoryException {
        //when
        repository.remove(existsGenre);

        //then
        long actual = repository.count(new IdIsNotNullSpecification());
        long expected = 6;
        assertEquals(expected, actual);
    }

    @Test
    void removeShouldNotDecreaseSize() throws RepositoryException {
        //when
        repository.remove(newGenre);

        //then
        long actual = repository.count(new IdIsNotNullSpecification());
        long expected = 7;
        assertEquals(expected, actual);
    }

    @Test
    void update() throws RepositoryException {
        updatedGenre.setId(5);
        repository.update(updatedGenre);
        Genre actual = repository.query(new GenreIdSpecification(updatedGenre.getId())).get(0);
        Genre expected = updatedGenre;
        assertEquals(expected, actual);
    }

    @Test
    void query() throws RepositoryException {
        //given
        Genre genreRetro = Genre.builder().id(6).title("retro").build();
        Genre genreChanson = Genre.builder().id(7).title("chanson").build();

        //when
        List<Genre> actual = repository.query(new IdMoreThanSpecification(5));
        List<Genre> expected = Arrays.asList(genreRetro, genreChanson);

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