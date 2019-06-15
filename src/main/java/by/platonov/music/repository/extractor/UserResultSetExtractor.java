package by.platonov.music.repository.extractor;

import by.platonov.music.entity.Gender;
import by.platonov.music.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * @author dzmitryplatonov on 2019-06-13.
 * @version 0.0.1
 */
public class UserResultSetExtractor implements AbstractResultSetExtractor<User> {

    @Override
    public List<User> map(ResultSet resultSet) throws SQLException {
        List<User> users = new ArrayList<>();
        Map<String, User> table = new HashMap<>();
        while (resultSet.next()) {
            if (!table.containsKey(resultSet.getString("login"))) {
                User user = User.builder()
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
                table.put(user.getLogin(), user);
            }
        }
        table.forEach((id, entity) -> users.add(entity));
        return users;
    }
}
