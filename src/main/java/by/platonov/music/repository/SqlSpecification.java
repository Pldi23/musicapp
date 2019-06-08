package by.platonov.music.repository;

/**
 * @author dzmitryplatonov on 2019-06-08.
 * @version 0.0.1
 */
public interface SqlSpecification<T> {

    String toSqlClauses();
}
