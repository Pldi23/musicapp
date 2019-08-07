package by.platonov.music.validator;

import by.platonov.music.command.RequestContent;

import java.util.Set;
import java.util.function.Function;

/**
 * interface represents a function that accepts {@link RequestContent} and produces a set of {@link Violation} as a result
 *
 * @author dzmitryplatonov on 2019-06-18.
 * @version 0.0.1
 */
public interface ParameterValidator extends Function<RequestContent, Set<Violation>> {
}
