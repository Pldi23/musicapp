package by.platonov.music.db;

import by.platonov.music.repository.TransactionHandler;
import lombok.extern.log4j.Log4j2;
import org.h2.tools.RunScript;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ResourceBundle;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-09-15.
 * @version 0.0.1
 */
@Log4j2
public class DbInMemoryH2SetupExtension implements BeforeEachCallback, AfterEachCallback {

    private static final String DB_TEST_DRIVER = "org.h2.Driver";
    private static final String DB_TEST_USER = "";
    private static final String DB_TEST_PASSWORD = "";
    private static final String DB_TEST_NAME = "test;MODE=PostgreSQL;";
    private static final String DB_TEST_PORT = "~";
    private static final String DB_TEST_HOST = "";
    private static final String DB_TEST_JDBC = "jdbc:h2";

    private static final String DB_INIT_SCRYPT_PATH = "src/test/resources/h2init.sql";
    private static final String DB_DROP_SCRYPT_PATH = "src/test/resources/h2drop.sql";

    private static final String DATABASE_PROPERTIES_PATH = "database";
    private static final String DB_HOST = "db.host";
    private static final String DB_USER = "db.user";
    private static final String DB_PASS = "db.password";
    private static final String DB_PORT = "db.port";
    private static final String DB_NAME = "db.name";
    private static final String DB_DRIVER = "db.driver";
    private static final String JDBC_POSTGRE = "db.jdbc";


    private DatabaseConfiguration dbConfig = DatabaseConfiguration.getInstance();


    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {
        dbConfig.setDbDriver(DB_TEST_DRIVER);
        dbConfig.setUser(DB_TEST_USER);
        dbConfig.setPassword(DB_TEST_PASSWORD);
        dbConfig.setHost(DB_TEST_HOST);
        dbConfig.setPort(DB_TEST_PORT);
        dbConfig.setDbName(DB_TEST_NAME);
        dbConfig.setJdbc(DB_TEST_JDBC);

        TransactionHandler transactionHandler = new TransactionHandler();
        transactionHandler.transactional(connection -> {
            try {
                return RunScript.execute(connection, new FileReader(DB_INIT_SCRYPT_PATH));
            } catch (FileNotFoundException e) {
                log.warn("test initialization failed", e);
            }
            return true;
        });
    }

    @Override
    public void afterEach(ExtensionContext extensionContext) throws Exception {
        TransactionHandler transactionHandler = new TransactionHandler();
        transactionHandler.transactional(connection -> {
            try {
                return RunScript.execute(connection, new FileReader(DB_DROP_SCRYPT_PATH));
            } catch (FileNotFoundException e) {
                log.warn("test db extension, tier down method failed", e);
            }
            return true;
        });

        ResourceBundle resourceBundle = ResourceBundle.getBundle(DATABASE_PROPERTIES_PATH);
        dbConfig.setHost(resourceBundle.getString(DB_HOST));
        dbConfig.setDbName(resourceBundle.getString(DB_NAME));
        dbConfig.setUser(resourceBundle.getString(DB_USER));
        dbConfig.setPassword(resourceBundle.getString(DB_PASS));
        dbConfig.setDbDriver(resourceBundle.getString(DB_DRIVER));
        dbConfig.setPort(resourceBundle.getString(DB_PORT));
        dbConfig.setJdbc(resourceBundle.getString(JDBC_POSTGRE));
        ConnectionPool.getInstance().destroyPool();

    }

}
