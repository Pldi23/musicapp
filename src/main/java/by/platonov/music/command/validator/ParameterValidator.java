package by.platonov.music.command.validator;

import by.platonov.music.command.RequestContent;

import java.util.Set;
import java.util.function.Function;

/**
 * @author dzmitryplatonov on 2019-06-18.
 * @version 0.0.1
 */
public interface ParameterValidator extends Function<RequestContent, Set<Violation>> {
}
