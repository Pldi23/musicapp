package by.platonov.music;

import by.platonov.music.db.ConnectionPool;
import by.platonov.music.db.DatabaseConfiguration;
import org.junit.Rule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author dzmitryplatonov on 2019-06-08.
 * @version 0.0.1
 */
public class DataBaseInitializationTest {

    DatabaseConfiguration dbConfig = DatabaseConfiguration.getInstance();

    @Rule
    private PostgreSQLContainer postgresContainer = (PostgreSQLContainer) new PostgreSQLContainer()
            .withInitScript("init.sql")
            .withDatabaseName(dbConfig.getDbName())
            .withUsername(dbConfig.getUser())
            .withPassword(dbConfig.getPassword());

    ConnectionPool pool;

    @BeforeEach
    void setUp() {
        postgresContainer.start();
        DatabaseConfiguration.getInstance().setHost(postgresContainer.getContainerIpAddress());
        DatabaseConfiguration.getInstance().setPort(postgresContainer.getMappedPort(5432));
        pool = ConnectionPool.getInstance();
    }

}
