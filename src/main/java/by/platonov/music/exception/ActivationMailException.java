package by.platonov.music.exception;

/**
 * @author dzmitryplatonov on 2019-06-21.
 * @version 0.0.1
 */
public class ActivationMailException extends Exception {

    public ActivationMailException() {
    }

    public ActivationMailException(String message) {
        super(message);
    }

    public ActivationMailException(String message, Throwable cause) {
        super(message, cause);
    }

    public ActivationMailException(Throwable cause) {
        super(cause);
    }
}
