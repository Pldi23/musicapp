package by.platonov.music.repository.mapper;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author dzmitryplatonov on 2019-06-15.
 * @version 0.0.1
 */
public interface PreparedStatementMapper<T> {
    void map(PreparedStatement preparedStatement, T entity) throws SQLException;
}
