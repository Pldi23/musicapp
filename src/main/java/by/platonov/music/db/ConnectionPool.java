package by.platonov.music.db;

import lombok.extern.log4j.Log4j2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author dzmitryplatonov on 2019-06-05.
 * @version 0.0.1
 */
@Log4j2
public class ConnectionPool {

    private static ConnectionPool instance;
    private static ReentrantLock lock = new ReentrantLock();
    private static AtomicBoolean create = new AtomicBoolean(false);
    private BlockingQueue<Connection> connections;

    private ConnectionPool() {
    }

    public static ConnectionPool getInstance() {
        if (!create.get()) {
            lock.lock();
            try {
                if (instance == null) {
                    instance = init();
                    create.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    private static ConnectionPool init(){
        ConnectionPool connectionPool = new ConnectionPool();
        DatabaseConfiguration configuration = DatabaseConfiguration.getDatabaseConfiguration();
        BlockingQueue<Connection> queue = new ArrayBlockingQueue<>(configuration.getPoolSize());
        for (int i = 0; i < configuration.getPoolSize(); i++) {
            try {
                queue.put(DriverManager.getConnection(configuration.getJdbcUrl(), configuration.getUser(),
                        configuration.getPassword()));
            } catch (SQLException e) {
                log.fatal("Can't initialize database connection", e);
                throw new RuntimeException("Database connection failed", e);
            } catch (InterruptedException e) {
                log.error("Interrupted");
            }
        }
        connectionPool.connections = queue;
        log.debug("Connection pool initialized with " + queue.size() + " connections");
        return connectionPool;
    }

    public Connection getConnection() throws InterruptedException {
        return connections.take();
    }

    public void releaseConnection(Connection connection) throws InterruptedException {
        connections.put(connection);
    }
}
