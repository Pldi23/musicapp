package by.platonov.music.command.validator;

import by.platonov.music.command.RequestConstant;
import by.platonov.music.command.RequestContent;
import lombok.extern.log4j.Log4j2;

import java.util.HashSet;
import java.util.Set;

/**
 * @author dzmitryplatonov on 2019-06-19.
 * @version 0.0.1
 */
@Log4j2
public class EmailValidator extends AbstractValidator {

    private static final String EMAIL_REGEX_PATTERN =
            "^[\\p{Alpha}\\p{Digit}\\p{Punct}]{1,30}@(?=.*\\.)[\\p{Alpha}\\p{Digit}\\p{Punct}]{1,30}." +
                    "[\\p{Alpha}\\p{Digit}\\p{Punct}]{1,30}[^.@]$";
    private static final String EMAIL_INCORRECT_MESSAGE = "E-mail example johndoe@domainsample.com";

    public EmailValidator(ParameterValidator next) {
        super(next);
    }

    @Override
    public Set<Violation> apply(RequestContent content) {
        Set<Violation> result = new HashSet<>();
        if (!content.getRequestParameters().containsKey(RequestConstant.EMAIL)) {
            log.warn("Invalid content parameter: no email parameter in request");
            result.add(new Violation(EMAIL_INCORRECT_MESSAGE));
        } else if (!content.getRequestParameter(RequestConstant.EMAIL)[0].matches(EMAIL_REGEX_PATTERN)) {
            log.warn("Invalid content parameter: " + content.getRequestParameter(RequestConstant.EMAIL)[0]);
            result.add(new Violation(EMAIL_INCORRECT_MESSAGE));
        }
        if (next != null) {
            result.addAll(next.apply(content));
        }
        return result;
    }
}
