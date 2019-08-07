package by.platonov.music.command;

/**
 * interface represents application logic to handle {@link javax.servlet.http.HttpServletRequest}
 * implementation of Command design pattern
 * @author dzmitryplatonov on 2019-06-15.
 * @version 0.0.1
 */
@FunctionalInterface
public interface Command {
    CommandResult execute(RequestContent content);
}
