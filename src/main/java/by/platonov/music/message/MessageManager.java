package by.platonov.music.message;

import lombok.extern.log4j.Log4j2;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-04.
 * @version 0.0.1
 */
@Log4j2
public class MessageManager {

    private static final String CONTENT = "pagecontent";
    private static final Locale DEFAULT_LOCALE = new Locale("ru", "RU");

    private MessageManager() {
    }

    public static String getMessage(String key, String locale) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(CONTENT, formatLocale(locale));
        return resourceBundle.getString(key);
    }

    private static Locale formatLocale(String locale) {
        if (locale != null) {
            String[] languageCountry = locale.split("_");
            return new Locale(languageCountry[0], languageCountry[1]);
        } else {
            Locale.setDefault(DEFAULT_LOCALE);
            return DEFAULT_LOCALE;
        }
    }
}
