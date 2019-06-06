package by.platonov.music.db;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.util.ResourceBundle;

/**
 * @author dzmitryplatonov on 2019-06-06.
 * @version 0.0.1
 */
@Getter
@Log4j2
public class DatabaseConfiguration {

    private static final String DATABASE_PROPERTIES_PATH = "database";

    private String jdbcUrl;
    private String user;
    private String password;
    private int poolSize;

    static DatabaseConfiguration getDatabaseConfiguration() {
        DatabaseConfiguration configuration = new DatabaseConfiguration();
        ResourceBundle resourceBundle = ResourceBundle.getBundle(DATABASE_PROPERTIES_PATH);
        configuration.jdbcUrl = resourceBundle.getString("db.url");
        configuration.user = resourceBundle.getString("db.user");
        configuration.password = resourceBundle.getString("db.password");
        configuration.poolSize = Integer.parseInt(resourceBundle.getString("db.poolsize"));
        log.debug("configuration setted");
        return configuration;
    }

}
