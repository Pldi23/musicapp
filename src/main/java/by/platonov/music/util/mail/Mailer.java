package by.platonov.music.util.mail;

import by.platonov.music.exception.ActivationMailException;
import lombok.extern.log4j.Log4j2;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;

/**
 * @author dzmitryplatonov on 2019-06-20.
 * @version 0.0.1
 */
@Log4j2
public class Mailer {

    private static final String MAIL_PROPERTIES_PATH = "/mail.properties";

    private static final String MAIL_SUBJECT = "Music app verification link";
    private static final String MAIL_MESSAGE = "Please complete your registration by activating a link";
    private static final String LINK_MESSAGE =
            "Your verification link :: http://localhost:8080/music-app/controller?command=activation&email=%s&hash=%s";

    private String userEmail;
    private String hash;

    public Mailer(String userEmail, String hash) {
        this.userEmail = userEmail;
        this.hash = hash;
    }

    public void sendMail() throws ActivationMailException {
        Properties properties = new Properties();
        String mailAddress;
        String password;
        String username;
        try {
            properties.load(Mailer.class.getResourceAsStream(MAIL_PROPERTIES_PATH));
            mailAddress = properties.getProperty("mail.smtps.user");
            password = properties.getProperty("mail.smtps.password");

            Session session = Session.getDefaultInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(mailAddress, password);
                }
            });

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(mailAddress));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(userEmail));
            message.setSubject(MAIL_SUBJECT);
            message.setText(MAIL_MESSAGE);
            message.setText(String.format(LINK_MESSAGE, userEmail, hash));

            username = properties.getProperty("mail.smtps.username");
            Transport transport = session.getTransport();
            transport.connect(username, password);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            log.debug("Activation link was successfully sent");
        } catch (IOException | MessagingException e) {
            log.error("E-mail with activation link was not sent");
            throw new ActivationMailException(e);
        }

    }
}
