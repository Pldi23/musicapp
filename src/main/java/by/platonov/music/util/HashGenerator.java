package by.platonov.music.util;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.UUID;

/**
 * @author dzmitryplatonov on 2019-06-21.
 * @version 0.0.1
 */
public class HashGenerator {

    public String generateHash() {
        UUID random = UUID.randomUUID();
        return DigestUtils.md5Hex(random.toString());
    }
}
