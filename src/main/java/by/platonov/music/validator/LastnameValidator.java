package by.platonov.music.validator;

import by.platonov.music.command.constant.RequestConstant;
import by.platonov.music.command.RequestContent;
import lombok.extern.log4j.Log4j2;

import java.util.HashSet;
import java.util.Set;

/**
 * @author dzmitryplatonov on 2019-06-19.
 * @version 0.0.1
 */
@Log4j2
public class LastnameValidator extends AbstractValidator {

    private static final String LASTNAME_REGEX_PATTERN = "^[^\\p{Punct}\\p{Blank}][\\p{L} '-]{0,28}[^\\p{Punct}\\p{Blank}]$";
    private static final String LASTNAME_INCORRECT_MESSAGE = "Last name must contain minimum 2 and maximum 30 letters";

    public LastnameValidator(ParameterValidator next) {
        super(next);
    }

    @Override
    public Set<Violation> apply(RequestContent content) {
        Set<Violation> result = new HashSet<>();
        if (!content.getRequestParameters().containsKey(RequestConstant.LASTNAME)) {
            log.warn("Invalid content parameter: no lastname parameter in request");
            result.add(new Violation(LASTNAME_INCORRECT_MESSAGE));
        } else if (!content.getRequestParameter(RequestConstant.LASTNAME)[0].matches(LASTNAME_REGEX_PATTERN)) {
            log.warn("Invalid content parameter: " + content.getRequestParameter(RequestConstant.LASTNAME)[0]);
            result.add(new Violation(LASTNAME_INCORRECT_MESSAGE));
        }
        if (next != null) {
            result.addAll(next.apply(content));
        }
        return result;
    }
}
