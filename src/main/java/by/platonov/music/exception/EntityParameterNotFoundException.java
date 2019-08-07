package by.platonov.music.exception;

/**
 * The exception will be thrown when parameter from request could not be found
 * @author Dzmitry Platonov on 2019-07-21.
 * @version 0.0.1
 */
public class EntityParameterNotFoundException extends Exception {

    public EntityParameterNotFoundException(String message) {
        super(message);
    }
}
