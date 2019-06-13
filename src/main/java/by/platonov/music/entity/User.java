package by.platonov.music.entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

/**
 * @author dzmitryplatonov on 2019-06-04.
 * @version 0.0.1
 */
@Data
@Builder
public class User extends Guest{

    private String login;
    private String password;
    private boolean admin;
    private String firstname;
    private String lastname;
    private Gender gender;
    private LocalDate birthDate;
    private String email;
    private Set<Playlist> playlists;

}
