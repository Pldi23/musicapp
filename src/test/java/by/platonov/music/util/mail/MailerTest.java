package by.platonov.music.util.mail;

import by.platonov.music.exception.ActivationMailException;
import by.platonov.music.util.HashGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author dzmitryplatonov on 2019-06-21.
 * @version 0.0.1
 */
class MailerTest {

    Mailer mailer;

    @Test
    void sendMail() throws ActivationMailException {
        HashGenerator hashGenerator = new HashGenerator();
        String email = "platonovd32@gmail.com";
        String hash = hashGenerator.generateHash();
        mailer = new Mailer(email, hash);
        mailer.sendMail();
    }
}