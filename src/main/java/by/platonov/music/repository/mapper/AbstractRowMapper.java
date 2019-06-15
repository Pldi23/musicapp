package by.platonov.music.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author dzmitryplatonov on 2019-06-13.
 * @version 0.0.1
 */
public interface AbstractRowMapper<T> {

    T map(ResultSet resultSet) throws SQLException;
}
