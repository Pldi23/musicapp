package by.platonov.music.repository.specification;

/**
 * @author dzmitryplatonov on 2019-06-06.
 * @version 0.0.1
 */
public interface Specification<T> {
    boolean specify(T t);
}