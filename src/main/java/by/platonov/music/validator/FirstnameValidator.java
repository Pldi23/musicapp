package by.platonov.music.validator;

import by.platonov.music.message.MessageManager;
import by.platonov.music.constant.RequestConstant;
import by.platonov.music.command.RequestContent;
import lombok.extern.log4j.Log4j2;

import java.util.HashSet;
import java.util.Set;

import static by.platonov.music.constant.RequestConstant.LOCALE;

/**
 * to validate {@link RequestConstant}.FIRSTNAME request parameter
 *
 * @author dzmitryplatonov on 2019-06-18.
 * @version 0.0.1
 */
@Log4j2
public class FirstnameValidator extends AbstractValidator {

    /**
     * 2-30 letters
     */
    private static final String FIRSTNAME_REGEX_PATTERN = "^[^\\p{Punct}\\p{Blank}][\\p{L} '-]{0,28}[^\\p{Punct}\\p{Blank}]$";

    public FirstnameValidator(ParameterValidator next) {
        super(next);
    }

    @Override
    public Set<Violation> apply(RequestContent content) {
        Set<Violation> result = new HashSet<>();
        Violation violation = new Violation(MessageManager.getMessage("violation.firstname",
                (String) content.getSessionAttribute(LOCALE)));

        if (!content.getRequestParameters().containsKey(RequestConstant.FIRSTNAME)) {
            log.warn("Invalid content parameter: no firstname parameter in request");
            result.add(violation);
        } else if (!content.getRequestParameter(RequestConstant.FIRSTNAME)[0].matches(FIRSTNAME_REGEX_PATTERN)) {
            log.warn("Invalid content parameter: " + content.getRequestParameter(RequestConstant.FIRSTNAME)[0]);
            result.add(violation);
        }

        if (next != null) {
            result.addAll(next.apply(content));
        }
        return result;
    }
}
