package by.platonov.music.repository.extractor;

import by.platonov.music.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static by.platonov.music.repository.extractor.ExtractConstant.*;

/**
 * to disassemble result set to list of {@link User}
 *
 * @author dzmitryplatonov on 2019-06-13.
 * @version 0.0.1
 */
public class UserResultSetExtractor implements AbstractResultSetExtractor<User> {

    @Override
    public List<User> extract(ResultSet resultSet) throws SQLException {
        List<User> users = new LinkedList<>();
        Map<String, User> table = new LinkedHashMap<>();
        while (resultSet.next()) {
            if (!table.containsKey(resultSet.getString(LOGIN))) {
                User user = User.builder()
                        .login(resultSet.getString(LOGIN))
                        .password(resultSet.getString(PASSWORD))
                        .admin(resultSet.getBoolean(ADMIN))
                        .firstname(resultSet.getString(FIRSTNAME))
                        .lastname(resultSet.getString(LASTNAME))
                        .email(resultSet.getString(EMAIL))
                        .birthDate(resultSet.getDate(DATE_OF_BIRTH).toLocalDate())
                        .gender(resultSet.getBoolean(GENDER) ? User.Gender.MALE : User.Gender.FEMALE)
                        .registrationDate(resultSet.getTimestamp(CREATED_AT).toLocalDateTime().toLocalDate())
                        .playlists(new LinkedHashSet<>())
                        .active(resultSet.getBoolean(ACTIVE_STATUS))
                        .verificationUuid(resultSet.getString(VERIFICATION_UUID))
                        .photoPath(resultSet.getString(PHOTO))
                        .payments(new LinkedHashSet<>())
                        .paidPeriod(resultSet.getTimestamp(PAID_PERIOD).toLocalDateTime())
                        .build();
                table.put(user.getLogin(), user);
            }
        }
        table.forEach((id, entity) -> users.add(entity));
        return users;
    }
}
