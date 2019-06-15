package by.platonov.music.repository.mapper;

import by.platonov.music.entity.User;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author dzmitryplatonov on 2019-06-15.
 * @version 0.0.1
 */
public class SetUserIdMapper implements PreparedStatementMapper<User> {
    @Override
    public void map(PreparedStatement preparedStatement, User user) throws SQLException {
        preparedStatement.setString(1, user.getLogin());
    }
}
