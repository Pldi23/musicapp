package by.platonov.music.validator;

import by.platonov.music.MessageManager;
import by.platonov.music.command.constant.RequestConstant;
import by.platonov.music.command.RequestContent;
import lombok.extern.log4j.Log4j2;

import java.util.HashSet;
import java.util.Set;

/**
 * @author dzmitryplatonov on 2019-06-18.
 * @version 0.0.1
 */
@Log4j2
public class FirstnameValidator extends AbstractValidator {

    private static final String FIRSTNAME_REGEX_PATTERN = "^[^\\p{Punct}\\p{Blank}][\\p{L} '-]{0,28}[^\\p{Punct}\\p{Blank}]$";
//    private static final String FIRSTNAME_INCORRECT_MESSAGE = "First name must contain minimum 2 and maximum 30 letters";

    public FirstnameValidator(ParameterValidator next) {
        super(next);
    }

    @Override
    public Set<Violation> apply(RequestContent content) {
        Set<Violation> result = new HashSet<>();
        if (!content.getRequestParameters().containsKey(RequestConstant.FIRSTNAME)) {
            log.warn("Invalid content parameter: no firstname parameter in request");
            result.add(new Violation(MessageManager.getMessage("violation.firstname")));
        } else if (!content.getRequestParameter(RequestConstant.FIRSTNAME)[0].matches(FIRSTNAME_REGEX_PATTERN)) {
            log.warn("Invalid content parameter: " + content.getRequestParameter(RequestConstant.FIRSTNAME)[0]);
            result.add(new Violation(MessageManager.getMessage("violation.firstname")));
        }
        if (next != null) {
            result.addAll(next.apply(content));
        }
        return result;
    }
}
