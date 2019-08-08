package by.platonov.music.service;

import by.platonov.music.db.DatabaseSetupExtension;
import by.platonov.music.entity.Playlist;
import by.platonov.music.exception.ServiceException;
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
class CommonServiceTest {

    private CommonService commonService = new CommonService();

    @Test
    void testSortedPlaylistsId() throws ServiceException {

        List<Playlist> actual = commonService.sortedPlaylistsId(true, 3, 1);

        List<Playlist> expected = new LinkedList<>();
        expected.add(Playlist.builder().id(2).name("summer2019").tracks(new LinkedList<>()).build());
        expected.add(Playlist.builder().id(3).name("authum2019").tracks(new LinkedList<>()).build());
        expected.add(Playlist.builder().id(4).name("winter2019").tracks(new LinkedList<>()).build());

        assertEquals(expected, actual);
    }

    @Test
    void testCountPlaylists() throws ServiceException {

        long actual = commonService.countPlaylists(true);

        long expected = 5;

        assertEquals(expected, actual);
    }

    @Test
    void testSearchPlaylists() throws ServiceException {

        long actual = commonService.searchPlaylists(5, 0, true).size();

        long expected = 5;

        assertEquals(expected, actual);
    }

}