package by.platonov.music.validator;

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
public class PasswordValidator extends AbstractValidator {

    private static final String PASSWORD_REGEX_PATTERN = "^(?=.*\\d)(?=.*\\p{Lower})(?=.*\\p{Upper})(?=.*\\p{Punct})" +
            "(?=\\S+$).{8,20}$";
    private static final String INCORRECT_PASS_MESSAGE = "Password must be minimum 8, maximum 20 symbols, and contain at" +
            " least 1 number, 1 latin uppercase letter, 1 latin lowercase letter, 1 punctuation. Only latin letters " +
            "available, spaces are unavailable";

    public PasswordValidator(ParameterValidator next) {
        super(next);
    }

    @Override
    public Set<Violation> apply(RequestContent content) {
        Set<Violation> result = new HashSet<>();
        if (!content.getRequestParameters().containsKey(RequestConstant.PASSWORD)) {
            log.warn("Invalid content parameter: no password parameter in request");
            result.add(new Violation(INCORRECT_PASS_MESSAGE));
        } else if (!content.getRequestParameter(RequestConstant.PASSWORD)[0].matches(PASSWORD_REGEX_PATTERN)) {
            log.warn("Invalid content parameter: " + content.getRequestParameter(RequestConstant.PASSWORD)[0]);
            result.add(new Violation(INCORRECT_PASS_MESSAGE));
        }
        if (next != null) {
            result.addAll(next.apply(content));
        }
        return result;
    }
}
