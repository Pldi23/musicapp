package by.platonov.music.repository.connectionLeak;

import by.platonov.music.db.ConnectionPool;
import by.platonov.music.db.DatabaseConfiguration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-06-26.
 * @version 0.0.1
 */
public class ConnectionLeakUtil {

//    private DatabaseConfiguration configuration = DatabaseConfiguration.getInstance();

//    private List idleConnectionCounters =
//            Arrays.asList(
//                    PostgreSQLIdleConnectionCounter.INSTANCE
//            );

    private IdleConnectionCounter connectionCounter = PostgreSQLIdleConnectionCounter.INSTANCE;

    private int connectionLeakCount;

    public ConnectionLeakUtil() {
        connectionLeakCount = countConnectionLeaks();
//        for ( IdleConnectionCounter connectionCounter :
//                idleConnectionCounters ) {
//            if ( connectionCounter.appliesTo(
//                    Dialect.getDialect().getClass() ) ) {
//                this.connectionCounter = connectionCounter;
//                break;
//            }
//        }
//        if ( connectionCounter != null ) {
//            connectionLeakCount = countConnectionLeaks();
//        }
    }

    public void assertNoLeaks() {
        if (connectionCounter != null) {
            int currentConnectionLeakCount = countConnectionLeaks();
            int diff = currentConnectionLeakCount - connectionLeakCount;
            if (diff > 0) {
                throw new ConnectionLeakException(
                        String.format(
                                "%d connection(s) have been leaked! Previous leak count: %d, Current leak count: %d",
                                diff,
                                connectionLeakCount,
                                currentConnectionLeakCount
                        )
                );
            }
        }
    }

    private int countConnectionLeaks() {
        try (Connection connection = newConnection()) {
            return connectionCounter.count(connection);
        }
        catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    private Connection newConnection() {
        try {
            return ConnectionPool.getInstance().getConnection();
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
//        try {
//            return DriverManager.getConnection(
//                    configuration.getJdbcUrl(),
//                    configuration.getUser(),
//                    configuration.getPassword()
//            );
//        }
//        catch (SQLException e) {
//            throw new IllegalStateException(e);
//        }
    }
}

