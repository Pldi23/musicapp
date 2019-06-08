package by.platonov.music.repository;

import by.platonov.music.db.ConnectionPool;
import by.platonov.music.db.DatabaseConfiguration;
import by.platonov.music.db.DatabaseSetupExtension;
import by.platonov.music.entity.Genre;
import by.platonov.music.entity.Musician;
import by.platonov.music.entity.Track;
import org.intellij.lang.annotations.Language;
import org.junit.Rule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author dzmitryplatonov on 2019-06-07.
 * @version 0.0.1
 */
@ExtendWith(DatabaseSetupExtension.class)
class TrackRepositoryTest {

    private TrackRepository repository = new TrackRepository();

    @Test
    void add() throws InterruptedException, SQLException {
        Genre pop = Genre.builder().id(1).title("pop").build();
        Musician singer = Musician.builder().id(5).name("Linkin Park").singer(true).author(true).build();
        Track track = Track.builder().name("noname").genre(pop).singers(Arrays.asList(singer))
                .authors(Arrays.asList(singer)).releaseDate(LocalDate.EPOCH).length(200).build();
        track.setId(6);
        assertTrue(repository.add(track));
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        @Language("SQL")
        String count = "select count(*) from track where id > 0";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(count);
        resultSet.next();
        int expectedSize = resultSet.getInt(1);
        assertEquals(6, expectedSize);
    }
}