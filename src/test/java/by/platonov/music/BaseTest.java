package by.platonov.music;

import by.platonov.music.db.ConnectionPool;
import by.platonov.music.db.DatabaseConfiguration;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.testcontainers.containers.PostgreSQLContainer;

/**
 * @author dzmitryplatonov on 2019-06-08.
 * @version 0.0.1
 */
public class BaseTest {

    private DatabaseConfiguration dbConfig = DatabaseConfiguration.getInstance();

    @Rule
    private PostgreSQLContainer postgresContainer = (PostgreSQLContainer) new PostgreSQLContainer()
            .withInitScript("init.sql")
            .withDatabaseName(dbConfig.getDbName())
            .withUsername(dbConfig.getUser())
            .withPassword(dbConfig.getPassword());

    private ConnectionPool pool;

    @BeforeEach
    void setUp() {
        postgresContainer.start();
        DatabaseConfiguration.getInstance().setHost(postgresContainer.getContainerIpAddress());
        DatabaseConfiguration.getInstance().setPort(postgresContainer.getMappedPort(5432));
        pool = ConnectionPool.getInstance();
    }
}
