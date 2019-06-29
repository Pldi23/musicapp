package by.platonov.music.service;

import by.platonov.music.db.DatabaseSetupExtension;
import by.platonov.music.entity.Genre;
import by.platonov.music.exception.ServiceException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-06-28.
 * @version 0.0.1
 */
@ExtendWith(DatabaseSetupExtension.class)
class GenreServiceTest {

    private GenreService genreService = new GenreService();

    @Test
    void getGenreShouldGetPopGenre() throws ServiceException {
        Genre actual = genreService.getGenre("pop");
        Genre expected = Genre.builder().id(1).title("pop").build();
        assertEquals(expected, actual);
    }

    @Test
    void getGenreShouldGetNewGenre() throws ServiceException {
        Genre actual = genreService.getGenre("pop/funk");
        Genre expected = Genre.builder().id(8).title("pop/funk").build();
        assertEquals(expected, actual);
    }
}