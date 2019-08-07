package by.platonov.music.validator;

/**
 * This abstract class is the superclass of all classes representing application validator
 * constructor of the class could be used to implement a chain of responsibility
 *
 * @author dzmitryplatonov on 2019-06-18.
 * @version 0.0.1
 */
public abstract class AbstractValidator implements ParameterValidator {

    ParameterValidator next;

    /**
     * @param next AbstractValidator to continue a chain
     */
    public AbstractValidator(ParameterValidator next) {
        this.next = next;
    }
}
