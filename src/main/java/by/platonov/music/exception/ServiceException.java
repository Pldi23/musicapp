package by.platonov.music.exception;

/**
 * The exception will be thrown from service layer
 * @author dzmitryplatonov on 2019-06-24.
 * @version 0.0.1
 */
public class ServiceException extends Exception {
    public ServiceException() {
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }
}
