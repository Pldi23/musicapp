package by.platonov.music.repository.mapper;

import by.platonov.music.entity.User;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * sets user's fields to prepared statement which insert {@link User} in db
 *
 * @author dzmitryplatonov on 2019-06-15.
 * @version 0.0.1
 */
public class SetUserFieldsMapper implements PreparedStatementMapper<User> {
    @Override
    public void map(PreparedStatement preparedStatement, User user) throws SQLException {
        preparedStatement.setString(1, user.getLogin());
        preparedStatement.setString(2, user.getPassword());
        preparedStatement.setBoolean(3, user.isAdmin());
        preparedStatement.setString(4, user.getFirstname());
        preparedStatement.setString(5, user.getLastname());
        preparedStatement.setString(6, user.getEmail());
        preparedStatement.setBoolean(7, user.getGender() == User.Gender.MALE);
        preparedStatement.setDate(8, Date.valueOf(user.getBirthDate()));
        preparedStatement.setBoolean(9, user.isActive());
        preparedStatement.setString(10, user.getVerificationUuid());
        preparedStatement.setString(11, user.getPhotoPath());
        preparedStatement.setTimestamp(12, Timestamp.valueOf(user.getPaidPeriod()));
    }
}
