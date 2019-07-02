package by.platonov.music.repository.extractor;

import by.platonov.music.exception.FilePartBeanException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author dzmitryplatonov on 2019-06-13.
 * @version 0.0.1
 */
public interface AbstractResultSetExtractor<T> {

    List<T> extract(ResultSet resultSet) throws SQLException, FilePartBeanException;
}
