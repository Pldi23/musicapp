package by.platonov.music.repository;

import by.platonov.music.db.ConnectionPool;
import by.platonov.music.exception.RepositoryException;
import lombok.extern.log4j.Log4j2;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author dzmitryplatonov on 2019-06-09.
 * @version 0.0.1
 *
 * execute-around method pattern
 * @link https://www.voxxed.com/2015/02/transactions-using-execute-around-methodpattern-and-lambdas/
 */
@Log4j2
class TransactionHandler {

    private TransactionHandler() {
    }

    static <T> T transactional(Transaction<T> transaction) throws RepositoryException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection;
        try {
            connection = pool.getConnection();
        } catch (InterruptedException e) {
            log.error("Connection interrupted", e);
            Thread.currentThread().interrupt();
            throw new RepositoryException(e);
        }
        try {
            connection.setAutoCommit(false);
            T result = transaction.execute(connection);
            connection.commit();
            return result;
        } catch (SQLException e) {
            try {
                connection.rollback();
                log.warn("Rolling back");
            } catch (SQLException ex) {
                throw new RepositoryException(e);
            }
            throw new RepositoryException(e);
        } finally {
            try {
                connection.setAutoCommit(true);
                pool.releaseConnection(connection);
            } catch (SQLException e) {
                log.error("Failed to release a connection");
            }
        }
    }
}
