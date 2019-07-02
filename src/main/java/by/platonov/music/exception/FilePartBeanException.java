package by.platonov.music.exception;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-02.
 * @version 0.0.1
 */
public class FilePartBeanException extends Exception {
    public FilePartBeanException() {
    }

    public FilePartBeanException(String message) {
        super(message);
    }

    public FilePartBeanException(String message, Throwable cause) {
        super(message, cause);
    }

    public FilePartBeanException(Throwable cause) {
        super(cause);
    }
}
