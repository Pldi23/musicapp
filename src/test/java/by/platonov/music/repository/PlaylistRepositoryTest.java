package by.platonov.music.repository;

import by.platonov.music.db.DatabaseSetupExtension;
import by.platonov.music.entity.Genre;
import by.platonov.music.entity.Musician;
import by.platonov.music.entity.Playlist;
import by.platonov.music.entity.Track;
import by.platonov.music.exception.RepositoryException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author dzmitryplatonov on 2019-06-12.
 * @version 0.0.1
 */
@ExtendWith(DatabaseSetupExtension.class)
class PlaylistRepositoryTest {

    Repository repository = new PlaylistRepository();
    Musician artur = Musician.builder().id(3).name("Артур Пирожков").build();
    Musician avici = Musician.builder().id(1).name("Avici").build();
    Musician leningrad = Musician.builder().id(2).name("Ленинград").build();
    Musician saluki = Musician.builder().id(3).name("Saluki").build();
    Genre pop = Genre.builder().id(1).title("pop").build();
    Genre chanson = Genre.builder().id(7).title("chanson").build();


    Track zacepila = Track.builder().id(4).name("Зацепила").genre(pop)
            .releaseDate(LocalDate.of(2019, 3, 11)).length(185)
            .singers(Set.of(artur))
            .authors(Set.of(artur)).build();
    Track tim = Track.builder().id(1).name("Tim").genre(pop)
            .releaseDate(LocalDate.of(2019, 1, 1)).length(180)
            .singers(Set.of(avici))
            .authors(Set.of(avici)).build();
    Track isuss = Track.builder().id(2).name("i_Suss").genre(chanson)
            .releaseDate(LocalDate.of(2019, 2, 2)).length(195)
            .singers(Set.of(leningrad))
            .authors(Set.of(leningrad)).build();
    Track vlastelin = Track.builder().id(3).name("Властелин калек").genre(pop)
            .releaseDate(LocalDate.of(2018, 12, 1)).length(200)
            .singers(Set.of(saluki))
            .authors(Set.of(saluki)).build();

    Playlist spring2019 = Playlist.builder().id(1).name("spring2019").tracks(Set.of(zacepila)).build();
    Set<Track> tracks = new TreeSet<>();
    Playlist newyearMix = Playlist.builder().id(5).name("new year party mix").tracks(tracks).build();


    @Test
    void count() throws RepositoryException {
        int actual = repository.count(() -> "id is not null;");
        int expected = 5;
        assertEquals(expected, actual);
    }

    @Test
    void query() throws RepositoryException {
        List<Playlist> actual = repository.query(() -> "playlist.id = 1 or playlist.id = 5");
        newyearMix.getTracks().add(tim);
        newyearMix.getTracks().add(zacepila);
        newyearMix.getTracks().add(vlastelin);
        newyearMix.getTracks().add(isuss);
        List<Playlist> expected = List.of(spring2019, newyearMix);
        assertEquals(expected, actual);
    }

    @Test
    void findOne() throws RepositoryException {
        Optional<Playlist> actual = repository.findOne(() -> "id = 1");
        Optional<Playlist> expected = Optional.of(spring2019);
        assertEquals(expected, actual);

    }


}