package by.platonov.music.validator;

import by.platonov.music.message.MessageManager;
import by.platonov.music.constant.RequestConstant;
import by.platonov.music.command.RequestContent;
import lombok.extern.log4j.Log4j2;

import java.util.HashSet;
import java.util.Set;

import static by.platonov.music.constant.RequestConstant.LOCALE;

/**
 * to validate {@link RequestConstant}.EMAIL request parameter
 *
 * @author dzmitryplatonov on 2019-06-19.
 * @version 0.0.1
 */
@Log4j2
public class EmailValidator extends AbstractValidator {

    /**
     * 1-30symbols @ 1-30symbols . 1-30symbols
     */
    private static final String EMAIL_REGEX_PATTERN =
            "^[\\p{Alpha}\\p{Digit}\\p{Punct}]{1,30}@(?=.*\\.)[\\p{Alpha}\\p{Digit}\\p{Punct}]{1,30}." +
                    "[\\p{Alpha}\\p{Digit}\\p{Punct}]{1,30}[^.@]$";


    public EmailValidator(ParameterValidator next) {
        super(next);
    }

    @Override
    public Set<Violation> apply(RequestContent content) {
        Set<Violation> result = new HashSet<>();
        Violation violation = new Violation(MessageManager.getMessage("violation.email",
                (String) content.getSessionAttribute(LOCALE)));

        if (!content.getRequestParameters().containsKey(RequestConstant.EMAIL)) {
            log.warn("Invalid content parameter: no email parameter in request");
            result.add(violation);
        } else if (!content.getRequestParameter(RequestConstant.EMAIL)[0].matches(EMAIL_REGEX_PATTERN)) {
            log.warn("Invalid content parameter: " + content.getRequestParameter(RequestConstant.EMAIL)[0]);
            result.add(violation);
        }

        if (next != null) {
            result.addAll(next.apply(content));
        }
        return result;
    }
}
