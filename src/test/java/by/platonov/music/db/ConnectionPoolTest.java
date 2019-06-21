package by.platonov.music.db;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author dzmitryplatonov on 2019-06-06.
 * @version 0.0.1
 */
@ExtendWith(DatabaseSetupExtension.class)
class ConnectionPoolTest {

    private List<Connection> usedConnections = new ArrayList<>();

    @Test
    void shouldReleaseAndRetakeTheSameConnectionWhenAllAreTaken() throws InterruptedException {
        //when all connections are taken
        ConnectionPool pool = ConnectionPool.getInstance();
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