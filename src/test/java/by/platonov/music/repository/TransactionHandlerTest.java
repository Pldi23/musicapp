package by.platonov.music.repository;

import by.platonov.music.db.ConnectionPool;
import by.platonov.music.db.DatabaseConfiguration;
import by.platonov.music.db.DatabaseSetupExtension;
import by.platonov.music.exception.RepositoryException;
import by.platonov.music.repository.connectionLeak.ConnectionLeakUtil;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-06-25.
 * @version 0.0.1
 */
@ExtendWith(DatabaseSetupExtension.class)
class TransactionHandlerTest {

    private static ConnectionLeakUtil connectionLeakUtil;
    private static boolean enableConnectionLeakDetection = true;

    @BeforeClass
    public static void initConnectionLeakUtility() {
        if ( enableConnectionLeakDetection ) {
            connectionLeakUtil = new ConnectionLeakUtil();
        }
    }

    @AfterClass
    public static void assertNoLeaks() {
        if ( enableConnectionLeakDetection ) {
            connectionLeakUtil.assertNoLeaks();
        }
    }

    @Test
    void transactional() throws RepositoryException {


    }
}