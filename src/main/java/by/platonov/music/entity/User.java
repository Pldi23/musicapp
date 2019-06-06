package by.platonov.music.entity;

import lombok.Data;

import java.time.LocalDate;

/**
 * @author dzmitryplatonov on 2019-06-04.
 * @version 0.0.1
 */
@Data
public class User extends Guest{

    private long id;
    private String login;
    private String password;
    private boolean admin;
    private String firstname;
    private String secondname;
    private Gender gender;
    private LocalDate birthDate;
    private String email;

}
