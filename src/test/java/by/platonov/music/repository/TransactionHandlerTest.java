package by.platonov.music.repository;

import by.platonov.music.db.DatabaseSetupExtension;
import by.platonov.music.exception.RepositoryException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author dzmitryplatonov on 2019-06-09.
 * @version 0.0.1
 */
@ExtendWith(DatabaseSetupExtension.class)
class TransactionHandlerTest {

    @Test
    void runInTransactionRollback() throws RepositoryException {
        TransactionHandler.runInTransaction(connection -> {
            try(PreparedStatement statement = connection.prepareStatement("")) {
               throw new SQLException();
            }
        });
    }

}