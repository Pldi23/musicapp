package by.platonov.music.repository;

import by.platonov.music.db.ConnectionPool;
import by.platonov.music.exception.RepositoryException;
import lombok.extern.log4j.Log4j2;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author dzmitryplatonov on 2019-06-09.
 * @version 0.0.1
 */
@Log4j2
class TransactionHandler {

    private TransactionHandler() {
    }

    public static void runInTransaction(Transaction transaction) throws RepositoryException {
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
            transaction.execute(connection);
            connection.commit();
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
