package by.platonov.music.entity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Set;

/**
 * @author dzmitryplatonov on 2019-06-04.
 * @version 0.0.1
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@ToString(exclude = "password")
public class User extends Entity{

    private String login;
    private String password;
    private boolean admin;
    private String firstname;
    private String lastname;
    private Gender gender;
    private LocalDate birthDate;
    private String email;
    private LocalDate registrationDate;
    private Set<Playlist> playlists;
    private boolean active;
    private String verificationUuid;
    private String photoPath;

    /**
     * @author dzmitryplatonov on 2019-06-05.
     * @version 0.0.1
     */
    public enum Gender {
        FEMALE,
        MALE
    }

    public int getPlaylistsQuantity() {
        return playlists.size();
    }
}
