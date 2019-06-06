package by.platonov.music.db;

import org.intellij.lang.annotations.Language;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author dzmitryplatonov on 2019-06-06.
 * @version 0.0.1
 */
class ConnectionPoolTest {

    ConnectionPool connectionPool = ConnectionPool.getInstance();

    @ClassRule
    public static GenericContainer simpleWebServer
            = new GenericContainer("alpine:3.2")
            .withExposedPorts(80)
            .withCommand("/bin/sh", "-c", "while true; do echo "
                    + "\"HTTP/1.1 200 OK\n\nHello World!\" | nc -l -p 80; done");

    @Rule
    public PostgreSQLContainer postgresContainer = new PostgreSQLContainer();

    @Test
    void getConnection() throws InterruptedException, SQLException {
        Connection connection = connectionPool.getConnection();
        @Language("SQL")
        String exec = "SELECT 1 from genre";
        ResultSet resultSet = connection.createStatement().executeQuery(exec);
        resultSet.next();
        int result = resultSet.getInt(1);
        assertEquals(1, result);
    }

    @Test
    void releaseConnection() {

    }
}