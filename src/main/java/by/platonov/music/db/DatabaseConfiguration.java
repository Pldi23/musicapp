package by.platonov.music.db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author dzmitryplatonov on 2019-06-06.
 * @version 0.0.1
 */
@Data
@AllArgsConstructor
@Log4j2
public class DatabaseConfiguration {

    private static final String DATABASE_PROPERTIES_PATH = "database";

    private static DatabaseConfiguration instance;

    private static ReentrantLock lock = new ReentrantLock();
    private static AtomicBoolean create = new AtomicBoolean(false);

    private String host;
    private String user;
    private String password;
    private int poolSize;
    private int port;
    private String dbName;
    private String dbDriver;

    public static DatabaseConfiguration getInstance() {
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

    private static DatabaseConfiguration init() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(DATABASE_PROPERTIES_PATH);
        String host = resourceBundle.getString("db.host");
        String user = resourceBundle.getString("db.user");
        String password = resourceBundle.getString("db.password");
        int poolSize = Integer.parseInt(resourceBundle.getString("db.poolsize"));
        int port = Integer.parseInt(resourceBundle.getString("db.port"));
        String dbName = resourceBundle.getString("db.name");
        String dbDriver = resourceBundle.getString("db.driver");

        return new DatabaseConfiguration(host, user, password, poolSize, port, dbName, dbDriver);
    }

    public String getJdbcUrl() {
        return "jdbc:postgresql://" + host + ":" + port + "/" + dbName;
    }
}
