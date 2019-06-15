package by.platonov.music.command;

import javax.servlet.http.HttpServletRequest;

/**
 * @author dzmitryplatonov on 2019-06-15.
 * @version 0.0.1
 */
@FunctionalInterface
public interface Command {
    CommandResult execute(HttpServletRequest request);
}
