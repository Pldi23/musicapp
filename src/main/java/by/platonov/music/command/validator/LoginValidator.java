package by.platonov.music.command.validator;

import by.platonov.music.command.RequestConstant;
import by.platonov.music.command.RequestContent;
import lombok.extern.log4j.Log4j2;

import java.util.HashSet;
import java.util.Set;

/**
 * @author dzmitryplatonov on 2019-06-18.
 * @version 0.0.1
 */
@Log4j2
public class LoginValidator extends AbstractValidator {

    private static final String LOGIN_REGEX_PATTERN = "^[(\\w)-]{4,20}";
    private static final String LOGIN_INCORRECT_MESSAGE = "Login must be minimum 4, maximum 20 symbols, and contain only " +
            "latin letter, numbers, and punctuation symbols like '-' and '_'";

    public LoginValidator(ParameterValidator next) {
        super(next);
    }

    @Override
    public Set<Violation> apply(RequestContent content) {
        Set<Violation> result = new HashSet<>();
        if (!content.getRequestParameters().containsKey(RequestConstant.LOGIN)) {
            log.warn("Invalid content parameter: no login parameter in request");
            result.add(new Violation(LOGIN_INCORRECT_MESSAGE));
        } else if (!content.getRequestParameter(RequestConstant.LOGIN)[0].matches(LOGIN_REGEX_PATTERN)) {
            log.warn("Invalid content parameter: " + content.getRequestParameter(RequestConstant.LOGIN)[0]);
            result.add(new Violation(LOGIN_INCORRECT_MESSAGE));
        }
        if (next != null) {
            result.addAll(next.apply(content));
        }
        return result;
    }
}
