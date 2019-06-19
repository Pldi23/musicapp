package by.platonov.music.repository.jdbchelper;

import by.platonov.music.repository.mapper.PreparedStatementMapper;
import by.platonov.music.repository.extractor.AbstractResultSetExtractor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author dzmitryplatonov on 2019-06-15.
 * @version 0.0.1
 */
public class JdbcHelper {

    public long count(Connection connection, String sql) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            resultSet.next();
            return resultSet.getLong(1);
        }
    }

    public <T> List<T> query(Connection connection, String sql, AbstractResultSetExtractor<T> rowMapper) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            return rowMapper.extract(resultSet);
        }
    }

    public <T> boolean execute(Connection connection, String sql, T entity, PreparedStatementMapper<T> preparedStatementMapper) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            preparedStatementMapper.map(statement, entity);
            statement.execute();
        }
        return true;
    }

    public <T> long insert(Connection connection, String sql, T entity, PreparedStatementMapper<T> preparedStatementMapper) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            preparedStatementMapper.map(statement, entity);
            try (ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                return resultSet.getLong(1);
            }
        }
    }
}
