package by.platonov.music.repository;

import java.util.List;

/**
 * @author dzmitryplatonov on 2019-06-06.
 * @version 0.0.1
 */
public interface Repository<T> {
    boolean add(T entity);
    boolean remove(T entity);
    boolean update(T entity);

    List<T> query(Specification<T> specification);
}
