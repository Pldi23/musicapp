package by.platonov.music;

import java.util.ResourceBundle;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-04.
 * @version 0.0.1
 */
public class MessageManager {

    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("pagecontent");

    private MessageManager() { }

    public static String getMessage(String key) {
        return RESOURCE_BUNDLE.getString(key);
    }
}
