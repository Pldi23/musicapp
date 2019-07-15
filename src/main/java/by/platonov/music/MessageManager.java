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
    private static final String DEFAULT_LOCALE = "ru_RU";

    private MessageManager() { }

    public static String getMessage(String key, String locale) {
        locale = locale != null ? locale : DEFAULT_LOCALE;
        ResourceBundle resourceBundle = ResourceBundle.getBundle(CONTENT, formatLocale(locale));
        return resourceBundle.getString(key);
    }

    private static Locale formatLocale(String locale) {
        switch (locale) {
            case "en_us":return new Locale("en", "US");
            case "ru_by":return new Locale("ru", "BY");
            default:return new Locale("ru", "RU");
        }
    }
}
