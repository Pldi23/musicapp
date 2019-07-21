package by.platonov.music.command;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-21.
 * @version 0.0.1
 */
public class EntityParameterNotFoundException extends Exception {

    public EntityParameterNotFoundException() {
    }

    public EntityParameterNotFoundException(String message) {
        super(message);
    }

    public EntityParameterNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityParameterNotFoundException(Throwable cause) {
        super(cause);
    }
}
