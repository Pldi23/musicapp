package by.platonov.music.util;

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
public class VerificationMailSender extends Thread {

    private static final String MAIL_SUBJECT = "Music app verification link";
    private static final String MAIL_MESSAGE = "Please complete your registration by activating a link";
    private static final String LINK_MESSAGE =
            "Your verification link :: http://%s:%d/%s/controller?command=activation&email=%s&verificationUuid=%s";

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

    @Override
    public void run() {
        sendMail();
    }

    public void sendMail() {
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
        } catch (IOException | MessagingException e) {
            log.error("Could not sent e-mail with activation link");
        }

    }
}
