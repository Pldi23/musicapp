package by.platonov.music.repository;


import by.platonov.music.exception.RepositoryException;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * interface represents what we want to run in one transaction
 *
 * @author dzmitryplatonov on 2019-06-09.
 * @version 0.0.1
 *
 * execute-around method pattern
 * @link https://www.voxxed.com/2015/02/transactions-using-execute-around-methodpattern-and-lambdas/
 */
public interface Transaction<T> {
    T execute(Connection connection) throws SQLException, RepositoryException;
}
