package by.platonov.music.repository.mapper;

import by.platonov.music.entity.Gender;
import by.platonov.music.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

/**
 * @author dzmitryplatonov on 2019-06-13.
 * @version 0.0.1
 */
public class UserRowMapper implements AbstractRowMapper<User> {
    @Override
    public User map(ResultSet resultSet) throws SQLException {
        return User.builder()
                .login(resultSet.getString("login"))
                .password(resultSet.getString("password"))
                .admin(resultSet.getBoolean("is_admin"))
                .firstname(resultSet.getString("first_name"))
                .lastname(resultSet.getString("last_name"))
                .email(resultSet.getString("e_mail"))
                .birthDate(resultSet.getDate("date_of_birth").toLocalDate())
                .gender(resultSet.getBoolean("gender") ? Gender.MALE : Gender.FEMALE)
                .playlists(new HashSet<>())
                .build();
    }
}
