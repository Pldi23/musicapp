package by.platonov.music.db;

import lombok.extern.log4j.Log4j2;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
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
    private static DatabaseConfiguration configuration = DatabaseConfiguration.getInstance();
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

    private static ConnectionPool init() {
        ConnectionPool connectionPool = new ConnectionPool();
        try {
            Class.forName(configuration.getDbDriver());
        } catch (ClassNotFoundException e) {
            log.fatal("Driver registration error", e);
            throw new RuntimeException("Database driver connection failed", e);
        }
        connectionPool.connections = new ArrayBlockingQueue<>(configuration.getPoolSize());
        for (int i = 0; i < configuration.getPoolSize(); i++) {
            connectionPool.connections.add(createConnection());
        }
        log.debug("Connection pool initialized with " + connectionPool.connections.size() + " connections");
        return connectionPool;
    }

    private static Connection createConnection() {
        try {
            log.trace("Connection created");
            return DriverManager.getConnection(configuration.getJdbcUrl(), configuration.getUser(),
                    configuration.getPassword());
        } catch (SQLException e) {
            log.fatal("Can't create database connection", e);
            throw new RuntimeException("Database connection failed", e);
        }
    }

    public Connection getConnection() throws InterruptedException {
        log.trace("Connection taken");
        return connections.take();
    }

    public void releaseConnection(Connection connection) {
        connections.add(connection);
        log.trace("Connection released, available connections: " + connections.size());
    }

    public void destroyPool() {
        if (create.get()) {
            lock.lock();
            try {
                for (Connection connection : connections) {
                    connection.close();
                }
                instance = null;
                Enumeration<Driver> driverEnumeration = DriverManager.getDrivers();
                while (driverEnumeration.hasMoreElements()) {
                    log.info("Deregistering driver");
                    DriverManager.deregisterDriver(driverEnumeration.nextElement());
                }
                create.set(false);
            } catch (SQLException e) {
                log.error("Exception during destroying connection pool", e);
            } finally {
                lock.unlock();
            }
        }
        log.debug("Connection pool destroyed");
    }
}
