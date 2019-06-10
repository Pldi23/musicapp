package by.platonov.music.repository;

import by.platonov.music.exception.RepositoryException;

import java.sql.Connection;

/**
 * @author dzmitryplatonov on 2019-06-09.
 * @version 0.0.1
 *
 * execute-around method pattern
 * @link https://www.voxxed.com/2015/02/transactions-using-execute-around-methodpattern-and-lambdas/
 */
interface Transaction<T> {
    T execute(Connection connection) throws RepositoryException;
}
