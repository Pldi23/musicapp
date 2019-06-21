package by.platonov.music.util.mail;

import by.platonov.music.exception.ActivationMailException;
import by.platonov.music.util.HashGenerator;
import org.junit.jupiter.api.Test;

/**
 * @author dzmitryplatonov on 2019-06-21.
 * @version 0.0.1
 */
class MailerTest {

    @Test
    void sendMail() throws ActivationMailException {
        HashGenerator hashGenerator = new HashGenerator();
        String email = "platonovd32@gmail.com";
        String hash = hashGenerator.generateHash();
        Mailer mailer = new Mailer(email, hash);
        mailer.sendMail();
    }
}