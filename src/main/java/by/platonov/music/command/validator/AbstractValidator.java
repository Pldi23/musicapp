package by.platonov.music.command.validator;

/**
 * @author dzmitryplatonov on 2019-06-18.
 * @version 0.0.1
 */
public abstract class AbstractValidator implements ParameterValidator {

    ParameterValidator next;

    public AbstractValidator(ParameterValidator next) {
        this.next = next;
    }
}
