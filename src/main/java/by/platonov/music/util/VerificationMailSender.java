package by.platonov.music.util;

import by.platonov.music.exception.VerificationMailException;
import lombok.extern.log4j.Log4j2;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.Callable;

/**
 * @author dzmitryplatonov on 2019-06-20.
 * @version 0.0.1
 */
@Log4j2
public class VerificationMailSender implements Callable<Boolean> {

    private static final String MAIL_SUBJECT = "Music app verification link";
    private static final String MAIL_MESSAGE = "Please complete your registration by activating a link";
    private static final String LINK_MESSAGE =
            "Your verification link :: http://%s:%d%s/controller?command=activation&email=%s&verificationUuid=%s";

    private String serverName;
    private int serverPort;
    private String contextPath;
    private String userEmail;
    private String hash;

    public VerificationMailSender(String serverName, int serverPort, String contextPath, String userEmail, String hash) {
        this.serverName = serverName;
        this.serverPort = serverPort;
        this.contextPath = contextPath;
        this.userEmail = userEmail;
        this.hash = hash;
    }

     boolean sendMail() throws VerificationMailException {
        Properties properties = new Properties();
        String mailAddress;
        String password;
        String username;
        try {
            properties.load(VerificationMailSender.class.getResourceAsStream("/mail.properties"));
            mailAddress = properties.getProperty("mail.smtp.user");
            password = properties.getProperty("mail.smtp.password");

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
            message.setText(String.format(LINK_MESSAGE, serverName, serverPort, contextPath, userEmail, hash));

            username = properties.getProperty("mail.smtp.username");
            Transport transport = session.getTransport();
            transport.connect(username, password);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            log.debug("Activation link was successfully sent");
            return true;
        } catch (IOException | MessagingException e) {
            log.error("Could not sent e-mail with activation link");
            throw new VerificationMailException(e);
        }
    }

    @Override
    public Boolean call() throws VerificationMailException {
        return sendMail();
    }
}
