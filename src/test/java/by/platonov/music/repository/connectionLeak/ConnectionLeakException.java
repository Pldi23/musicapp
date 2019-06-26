package by.platonov.music.repository.connectionLeak;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-06-26.
 * @version 0.0.1
 */
public class ConnectionLeakException extends RuntimeException {
    public ConnectionLeakException() {
    }

    public ConnectionLeakException(String message) {
        super(message);
    }

    public ConnectionLeakException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConnectionLeakException(Throwable cause) {
        super(cause);
    }
}
