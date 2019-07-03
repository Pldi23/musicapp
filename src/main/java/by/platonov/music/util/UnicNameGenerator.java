package by.platonov.music.util;

import java.util.UUID;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-03.
 * @version 0.0.1
 */
public class UnicNameGenerator {

    public static String generateUnicName() {
        return UUID.randomUUID().toString();
    }
}
