package by.platonov.music.db;

import org.junit.Rule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author dzmitryplatonov on 2019-06-06.
 * @version 0.0.1
 */
class ConnectionPoolTest {

    private DatabaseConfiguration dbConfig = DatabaseConfiguration.getInstance();

    @Rule
    private PostgreSQLContainer postgresContainer = (PostgreSQLContainer) new PostgreSQLContainer()
            .withInitScript("init.sql")
            .withDatabaseName(dbConfig.getDbName())
            .withUsername(dbConfig.getUser())
            .withPassword(dbConfig.getPassword());

    private ConnectionPool pool;

    private List<Connection> usedConnections = new ArrayList<>();

    @BeforeEach
    void setUp() {
        postgresContainer.start();
        DatabaseConfiguration.getInstance().setHost(postgresContainer.getContainerIpAddress());
        DatabaseConfiguration.getInstance().setPort(postgresContainer.getMappedPort(5432));
        pool = ConnectionPool.getInstance();
    }

    @AfterEach
    void tearDown() throws InterruptedException {
        //since ConnectionPool is singleton we need to release connections
        for (Connection c : usedConnections) {
            pool.releaseConnection(c);
        }
    }

    @Test
    void shouldReleaseAndRetakeTheSameConnectionWhenAllAreTaken() throws InterruptedException {
        //when all connections are taken
        int poolSize = DatabaseConfiguration.getInstance().getPoolSize();
        for (int i = 0; i < poolSize; i++) {
            usedConnections.add(pool.getConnection());
        }

        //when one is released
        Connection releasedConnection = usedConnections.get(0);
        pool.releaseConnection(releasedConnection);

        //when one is taken
        Connection retakenConnection = pool.getConnection();

        //then
        assertEquals(releasedConnection, retakenConnection);
    }
}