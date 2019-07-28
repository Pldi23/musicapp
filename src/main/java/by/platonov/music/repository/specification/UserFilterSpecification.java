package by.platonov.music.repository.specification;

import by.platonov.music.entity.filter.EntityFilter;
import by.platonov.music.entity.filter.UserFilter;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-21.
 * @version 0.0.1
 */
public class UserFilterSpecification implements SqlSpecification {

    private static final String SPECIFICATION = "where LOWER(login) LIKE LOWER(?) " +
            "and LOWER(first_name) LIKE LOWER(?) " +
            "and LOWER(last_name) LIKE LOWER(?) " +
            "and LOWER(e_mail) LIKE LOWER(?) " +
            "and date_of_birth between ? and ? " +
            "and created_at between ? and ? ";
    private static final String ADMIN_SPECIFICATION = "and is_admin = ? ";
    private static final String LIMIT_OFFSET_SPECIFICATION = "limit ? offset ?;";

    private UserFilter userFilter;
    private int limit;
    private long offset;

    public UserFilterSpecification(EntityFilter userFilter, int limit, long offset) {
        this.userFilter = (UserFilter) userFilter;
        this.limit = limit;
        this.offset = offset;
    }

    @Override
    public PreparedStatement toPreparedStatement(Connection connection, String parentSql) throws SQLException {
        PreparedStatement statement;
        String specification;
        if (userFilter.getRole() == null) {
            specification = parentSql + SPECIFICATION + LIMIT_OFFSET_SPECIFICATION;
            statement = connection.prepareStatement(specification);
            prepare(statement);
            statement.setInt(9, limit);
            statement.setLong(10, offset);
        } else {
            specification = parentSql + SPECIFICATION + ADMIN_SPECIFICATION + LIMIT_OFFSET_SPECIFICATION;
            statement = connection.prepareStatement(specification);
            prepare(statement);
            statement.setBoolean(9, userFilter.getRole());
            statement.setInt(10, limit);
            statement.setLong(11, offset);
        }
        return statement;
    }

    private void prepare(PreparedStatement statement) throws SQLException {
        statement.setString(1, "%" + userFilter.getLogin() + "%");
        statement.setString(2, "%" + userFilter.getFirstname() + "%");
        statement.setString(3, "%" + userFilter.getLastname() + "%");
        statement.setString(4, "%" + userFilter.getEmail() + "%");
        statement.setDate(5, Date.valueOf(userFilter.getBirthdateFrom()));
        statement.setDate(6, Date.valueOf(userFilter.getBirthdateTo()));
        statement.setDate(7, Date.valueOf(userFilter.getRegistrationFrom()));
        statement.setDate(8, Date.valueOf(userFilter.getRegisrationTo()));
    }
}
