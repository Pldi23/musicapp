package by.platonov.music.exception;

/**
 * The exception will be thrown from repository layer
 * @author dzmitryplatonov on 2019-06-08.
 * @version 0.0.1
 */
public class RepositoryException extends Exception {
    public RepositoryException() {
    }

    public RepositoryException(String message) {
        super(message);
    }

    public RepositoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public RepositoryException(Throwable cause) {
        super(cause);
    }
}
