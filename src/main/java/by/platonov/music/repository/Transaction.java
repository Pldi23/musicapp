package by.platonov.music.repository;

import by.platonov.music.exception.RepositoryException;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author dzmitryplatonov on 2019-06-09.
 * @version 0.0.1
 */
interface Transaction {
    void execute(Connection connection) throws RepositoryException, SQLException;
}
