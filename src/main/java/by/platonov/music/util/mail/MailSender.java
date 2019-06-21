package by.platonov.music.util.mail;


import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;

/**
 * @author dzmitryplatonov on 2019-06-20.
 * @version 0.0.1
 */
public class MailSender {

    private static final String MAIL_PROPERTIES_PATH = "/mail.properties";
    private static final String MY_EMAIL_ADDRESS = "platonovd32";
    private static final String USER = "platonovd32";
    private static final String PASSWORD = "Zvezda-BGU1986";

    public static void sendMail(String email) throws IOException, MessagingException {
        final Properties properties = new Properties();
        properties.load(MailSender.class.getResourceAsStream(MAIL_PROPERTIES_PATH));

        String recipientEmailAddress;
        String subject;
        String textMessage;
        {
            recipientEmailAddress = email;
            subject = "Verification link from music app";
            textMessage = "need to generate a link";
        }

        Session mailSession = Session.getDefaultInstance(properties);
        MimeMessage message = new MimeMessage(mailSession);
        message.setFrom(new InternetAddress(MY_EMAIL_ADDRESS));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmailAddress));
        message.setSubject(subject);
        message.setText(textMessage);

        Transport transport = mailSession.getTransport();
        transport.connect(USER, PASSWORD);
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
    }
}
