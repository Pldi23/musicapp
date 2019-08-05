package by.platonov.music.repository.specification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * interface is used to specify data from corresponding Repository
 *
 * @author dzmitryplatonov on 2019-06-08.
 * @version 0.0.1
 */
public interface SqlSpecification {

    PreparedStatement toPreparedStatement(Connection connection, String parentSql) throws SQLException;
}
