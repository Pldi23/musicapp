package by.platonov.music.repository.specification;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

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

    private String login;
    private Boolean isAdmin;
    private String firstname;
    private String lastname;
    private String email;
    private LocalDate birthDateFrom;
    private LocalDate birthDateTo;
    private LocalDate registrationDateFrom;
    private LocalDate registrationDateTo;
    private int limit;
    private long offset;

    public UserFilterSpecification(String login, Boolean isAdmin, String firstname, String lastname, String email,
                                   LocalDate birthDateFrom, LocalDate birthDateTo, LocalDate registrationDateFrom,
                                   LocalDate registrationDateTo, int limit, long offset) {
        this.login = login;
        this.isAdmin = isAdmin;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.birthDateFrom = birthDateFrom;
        this.birthDateTo = birthDateTo;
        this.registrationDateFrom = registrationDateFrom;
        this.registrationDateTo = registrationDateTo;
        this.limit = limit;
        this.offset = offset;
    }

    @Override
    public PreparedStatement toPreparedStatement(Connection connection, String parentSql) throws SQLException {
        PreparedStatement statement;
        String specification;
        if (isAdmin == null) {
            specification = parentSql + SPECIFICATION + LIMIT_OFFSET_SPECIFICATION;
            statement = connection.prepareStatement(specification);

            statement.setString(1, "%" + login + "%");
            statement.setString(2, "%" + firstname + "%");
            statement.setString(3, "%" + lastname + "%");
            statement.setString(4, "%" + email + "%");
            statement.setDate(5, Date.valueOf(birthDateFrom));
            statement.setDate(6, Date.valueOf(birthDateTo));
            statement.setDate(7, Date.valueOf(registrationDateFrom));
            statement.setDate(8, Date.valueOf(registrationDateTo));
            statement.setInt(9, limit);
            statement.setLong(10, offset);
        } else {
            specification = parentSql + SPECIFICATION + ADMIN_SPECIFICATION + LIMIT_OFFSET_SPECIFICATION;
            statement = connection.prepareStatement(specification);
            statement.setString(1, "%" + login + "%");
            statement.setString(2, "%" + firstname + "%");
            statement.setString(3, "%" + lastname + "%");
            statement.setString(4, "%" + email + "%");
            statement.setDate(5, Date.valueOf(birthDateFrom));
            statement.setDate(6, Date.valueOf(birthDateTo));
            statement.setDate(7, Date.valueOf(registrationDateFrom));
            statement.setDate(8, Date.valueOf(registrationDateTo));
            statement.setBoolean(9, isAdmin);
            statement.setInt(10, limit);
            statement.setLong(11, offset);
        }
        return statement;
    }
}
