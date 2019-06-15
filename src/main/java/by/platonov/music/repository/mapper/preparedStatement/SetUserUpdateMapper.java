package by.platonov.music.repository.mapper.preparedStatement;

import by.platonov.music.entity.Gender;
import by.platonov.music.entity.User;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author dzmitryplatonov on 2019-06-15.
 * @version 0.0.1
 */
public class SetUserUpdateMapper implements PreparedStatementMapper<User> {
    @Override
    public void map(PreparedStatement preparedStatement, User user) throws SQLException {
        preparedStatement.setString(1, user.getPassword());
        preparedStatement.setBoolean(2, user.isAdmin());
        preparedStatement.setString(3, user.getFirstname());
        preparedStatement.setString(4, user.getLastname());
        preparedStatement.setString(5, user.getEmail());
        preparedStatement.setBoolean(6, user.getGender() == Gender.MALE);
        preparedStatement.setDate(7, Date.valueOf(user.getBirthDate()));
        preparedStatement.setString(8, user.getLogin());
    }
}
