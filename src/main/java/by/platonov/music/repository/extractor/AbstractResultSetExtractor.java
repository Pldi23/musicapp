package by.platonov.music.repository.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * interface is used to disassemble the result set to the list of entities {@link by.platonov.music.entity.Entity}
 *
 * @author dzmitryplatonov on 2019-06-13.
 * @version 0.0.1
 */
public interface AbstractResultSetExtractor<T> {

    List<T> extract(ResultSet resultSet) throws SQLException;
}
