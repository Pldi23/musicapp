package by.platonov.music;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-04.
 * @version 0.0.1
 */
public class MessageManager {

    private static final String CONTENT = "pagecontent";

    private MessageManager() { }

    public static String getMessage(String key, String locale) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(CONTENT, new Locale(locale));
        return resourceBundle.getString(key);
    }
}
