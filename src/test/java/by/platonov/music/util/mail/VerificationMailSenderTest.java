package by.platonov.music.util.mail;

import by.platonov.music.util.HashGenerator;
import by.platonov.music.util.VerificationMailSender;
import org.junit.jupiter.api.Test;

/**
 * @author dzmitryplatonov on 2019-06-21.
 * @version 0.0.1
 */
class VerificationMailSenderTest {

    @Test
    void sendMail(){
        HashGenerator hashGenerator = new HashGenerator();
        String email = "platonovd32@gmail.com";
        String hash = hashGenerator.generateHash();
        VerificationMailSender verificationMailSender = new VerificationMailSender(email, hash);
        verificationMailSender.sendMail();
    }
}