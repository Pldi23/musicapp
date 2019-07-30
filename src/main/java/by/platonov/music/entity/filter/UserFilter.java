package by.platonov.music.entity.filter;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-26.
 * @version 0.0.1
 */
@Data
@Builder
public class UserFilter implements EntityFilter {

    String login;
    Boolean role;
    String firstname;
    String lastname;
    String email;
    LocalDate birthdateFrom;
    LocalDate birthdateTo;
    LocalDate registrationFrom;
    LocalDate regisrationTo;
}
