package by.platonov.music.command;

/**
 * @author dzmitryplatonov on 2019-06-15.
 * @version 0.0.1
 */
@FunctionalInterface
public interface Command {
    CommandResult execute(RequestContent content);
}