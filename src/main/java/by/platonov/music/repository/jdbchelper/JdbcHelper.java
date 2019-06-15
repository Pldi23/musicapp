package by.platonov.music.repository.jdbchelper;

import by.platonov.music.entity.Entity;
import by.platonov.music.entity.Track;
import by.platonov.music.repository.mapper.preparedStatement.PreparedStatementMapper;
import by.platonov.music.repository.mapper.resultSet.AbstractRowMapper;
import by.platonov.music.repository.mapper.resultSet.TrackRowMapper;
import lombok.extern.log4j.Log4j2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dzmitryplatonov on 2019-06-15.
 * @version 0.0.1
 */
@Log4j2
public class JdbcHelper {
    public long count(Connection connection, String sql) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            resultSet.next();
            return resultSet.getLong(1);
        }
    }

    public <T> List<T> query(Connection connection, String sql, AbstractRowMapper<T> rowMapper) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            List<T> entities = new ArrayList<>();
            Map<Long, T> table = new HashMap<>();
            while (resultSet.next()) {
                
            }
//                if (!table.containsKey(resultSet.getLong(rowMapper.getKey()))) {
//                    T entity = rowMapper.map(resultSet);
//                    table.put(resultSet.getLong(rowMapper.getKey()), entity);
//                }
//                    else {
//                        Musician singer = mapper.mapSinger(resultSet);
//                        Musician author = mapper.mapAuthor(resultSet);
//                        table.get(resultSet.getLong("id")).getSingers().add(singer);
//                        table.get(resultSet.getLong("id")).getAuthors().add(author);
//                    }
//            }
//            table.forEach((id, entity) -> entities.add(entity));
            return entities;

//            List<T> result = new ArrayList<>();
//            while (resultSet.next()) {
//                T entity = rowMapper.map(resultSet);
//                result.add(entity);
//            }
//            return result;
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
                long trackId = resultSet.getLong(1);
                log.debug("Track: " + trackId + " added");
                return trackId;
            }
        }
    }
}
