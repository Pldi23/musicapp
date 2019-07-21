package by.platonov.music.repository.specification;

import by.platonov.music.entity.User;

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
    private boolean isAdmin;
    private String firstname;
    private String lastname;
    private String email;
    private LocalDate birthDateFrom;
    private LocalDate birthDateTo;
    private LocalDate registrationDateFrom;
    private LocalDate registrationDateTo;
    private int limit;
    private long offset;

    public UserFilterSpecification(String login, boolean isAdmin, String firstname, String lastname, String email,
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
        if (isAdmin == null)

            PreparedStatement statement = connection.prepareStatement();

        statement.setString(1, "%" + login + "%");
        statement.setBoolean(2, isAdmin);
        statement.setString(3, "%" + firstname + "%");
        statement.setString(4, "%" + lastname + "%");
        statement.setString(5, "%" + email + "%");

        statement.setBoolean(6, gender == User.Gender.MALE);
        statement.setDate(7, Date.valueOf(birthDateFrom));
        statement.setDate(8, Date.valueOf(birthDateTo));
        statement.setDate(9, Date.valueOf(registrationDateFrom));
        statement.setDate(10, Date.valueOf(registrationDateTo));
        statement.setInt(11, limit);
        statement.setLong(12, offset);

        return statement;
    }
}
