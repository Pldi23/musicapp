package by.platonov.music.repository;

import by.platonov.music.exception.RepositoryException;
import by.platonov.music.repository.specification.SqlSpecification;

import java.util.List;

/**
 * @author dzmitryplatonov on 2019-06-06.
 * @version 0.0.1
 */
public interface Repository<T> {
    boolean add(T entity) throws RepositoryException;
    boolean remove(T entity) throws RepositoryException;
    boolean update(T entity) throws RepositoryException;

    List<T> query(SqlSpecification<T> specification) throws RepositoryException;
}
