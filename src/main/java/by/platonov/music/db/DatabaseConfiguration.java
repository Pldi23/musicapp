package by.platonov.music.db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

import java.util.ResourceBundle;

/**
 * Database configurator, class is responsible for reading database settings from properties and making it available
 * for connection pool
 * @author dzmitryplatonov on 2019-06-06.
 * @version 0.0.1
 */
@Data
@AllArgsConstructor
@Log4j2
class DatabaseConfiguration {

    private static final String DATABASE_PROPERTIES_PATH = "database";
    private static final String DB_HOST = "db.host";
    private static final String DB_USER = "db.user";
    private static final String DB_PASSWORD = "db.password";
    private static final String DB_POOLSIZE = "db.poolsize";
    private static final String DB_PORT = "db.port";
    private static final String DB_NAME = "db.name";
    private static final String DB_DRIVER = "db.driver";
    private static final String JDBC_POSTGRE = "jdbc:postgresql://";

    private static DatabaseConfiguration instance;

    private String host;
    private String user;
    private String password;
    private int poolSize;
    private int port;
    private String dbName;
    private String dbDriver;

    static DatabaseConfiguration getInstance() {
        if (instance == null) {
            instance = init();
        }
        return instance;
    }

    private static DatabaseConfiguration init() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(DATABASE_PROPERTIES_PATH);
        String host = resourceBundle.getString(DB_HOST);
        String user = resourceBundle.getString(DB_USER);
        String password = resourceBundle.getString(DB_PASSWORD);
        int poolSize = Integer.parseInt(resourceBundle.getString(DB_POOLSIZE));
        int port = Integer.parseInt(resourceBundle.getString(DB_PORT));
        String dbName = resourceBundle.getString(DB_NAME);
        String dbDriver = resourceBundle.getString(DB_DRIVER);

        return new DatabaseConfiguration(host, user, password, poolSize, port, dbName, dbDriver);
    }

    String getJdbcUrl() {
        return JDBC_POSTGRE + host + ":" + port + "/" + dbName;
    }
}
