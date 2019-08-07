package by.platonov.music.exception;

/**
 * The exception will be thrown when E-mail could not be sent correctly
 * @author Dzmitry Platonov on 2019-07-12.
 * @version 0.0.1
 */
public class VerificationMailException extends Exception {

    public VerificationMailException(Throwable cause) {
        super(cause);
    }
}
