package by.platonov.music.entity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Representation of User
 *
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

    private LocalDateTime paidPeriod;
    private Set<Payment> payments;

    /**
     * representation of gender
     * @author dzmitryplatonov on 2019-06-05.
     * @version 0.0.1
     */
    public enum Gender {
        FEMALE,
        MALE
    }

    /**
     *
     * @return the number of playlists this user has
     */
    public int getPlaylistsQuantity() {
        return playlists.size();
    }
}
