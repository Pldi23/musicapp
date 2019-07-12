package by.platonov.music.exception;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-12.
 * @version 0.0.1
 */
public class VerificationMailException extends Exception {
    public VerificationMailException() {
    }

    public VerificationMailException(String message) {
        super(message);
    }

    public VerificationMailException(String message, Throwable cause) {
        super(message, cause);
    }

    public VerificationMailException(Throwable cause) {
        super(cause);
    }
}
