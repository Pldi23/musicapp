package by.platonov.music.db;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.containers.PostgreSQLContainer;

/**
 * @author dzmitryplatonov on 2019-06-09.
 * @version 0.0.1
 */
public class DatabaseSetupExtension implements BeforeEachCallback, AfterEachCallback {

    private PostgreSQLContainer postgreSQLContainer;

    @Override
    public void beforeEach(ExtensionContext extensionContext) {
        DatabaseConfiguration dbConfig = DatabaseConfiguration.getInstance();
        postgreSQLContainer = (PostgreSQLContainer) new PostgreSQLContainer()
                .withInitScript("init.sql")
                .withDatabaseName(dbConfig.getDbName())
                .withUsername(dbConfig.getUser())
                .withPassword(dbConfig.getPassword());
        postgreSQLContainer.start();
        dbConfig.setHost(postgreSQLContainer.getContainerIpAddress());
        dbConfig.setPort(String.valueOf(postgreSQLContainer.getMappedPort(5432)));
    }

    @Override
    public void afterEach(ExtensionContext extensionContext) {
        ConnectionPool.getInstance().destroyPool();
        postgreSQLContainer.stop();
    }
}
