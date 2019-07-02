package by.platonov.music.util;

import org.junit.jupiter.api.Test;

/**
 * @author dzmitryplatonov on 2019-06-21.
 * @version 0.0.1
 */
class VerificationMailSenderTest {

    @Test
    void sendMail(){

        String server = "localhost";
        int port = 8080;
        String path = "music-app";
        HashGenerator hashGenerator = new HashGenerator();
        String email = "platonovd32@gmail.com";
        String hash = hashGenerator.generateHash();
        VerificationMailSender verificationMailSender = new VerificationMailSender(server, port, path, email, hash);
        verificationMailSender.sendMail();
    }
}