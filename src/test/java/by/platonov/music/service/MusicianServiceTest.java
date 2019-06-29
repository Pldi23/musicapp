package by.platonov.music.service;

import by.platonov.music.db.DatabaseSetupExtension;
import by.platonov.music.entity.Musician;
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
class MusicianServiceTest {

    private MusicianService service = new MusicianService();

    @Test
    void getMusicianShouldFindOne() throws ServiceException {
        Musician actual = service.getMusician("Avici");
        Musician expected = Musician.builder().id(1).name("Avici").build();
        assertEquals(expected, actual);
    }

    @Test
    void getMusicianShouldCreateNewMusicianInRepository() throws ServiceException {
        Musician actual = service.getMusician("Friend");
        Musician expected = Musician.builder().id(8).name("Friend").build();
        assertEquals(expected, actual);
    }
}