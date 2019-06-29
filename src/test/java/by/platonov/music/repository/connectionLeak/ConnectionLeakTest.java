package by.platonov.music.repository.connectionLeak;

import by.platonov.music.db.ConnectionPool;
import by.platonov.music.db.DatabaseConfiguration;
import by.platonov.music.db.DatabaseSetupExtension;
import by.platonov.music.exception.RepositoryException;
import by.platonov.music.repository.connectionLeak.ConnectionLeakUtil;
import lombok.extern.log4j.Log4j2;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-06-25.
 * @version 0.0.1
 */
@Log4j2
@ExtendWith(DatabaseSetupExtension.class)
class ConnectionLeakTest {

    private static ConnectionLeakUtil connectionLeakUtil;
    private static boolean enableConnectionLeakDetection = true;

    @BeforeEach
    void initConnectionLeakUtility() {
        if (enableConnectionLeakDetection) {
            connectionLeakUtil = new ConnectionLeakUtil();
        }
    }

    @AfterEach
    void assertNoLeaks() {
        if (enableConnectionLeakDetection) {
            connectionLeakUtil.assertNoLeaks();
        }
    }

    @Test
    void shouldReturnException() throws InterruptedException, SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();

        Connection connection = pool.getConnection();
        PreparedStatement statement = connection.prepareStatement("select * from musician");
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
    }
}