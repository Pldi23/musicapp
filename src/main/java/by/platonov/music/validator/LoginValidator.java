package by.platonov.music.validator;

import by.platonov.music.message.MessageManager;
import by.platonov.music.command.constant.RequestConstant;
import by.platonov.music.command.RequestContent;
import lombok.extern.log4j.Log4j2;

import java.util.HashSet;
import java.util.Set;

import static by.platonov.music.command.constant.RequestConstant.LOCALE;

/**
 * @author dzmitryplatonov on 2019-06-18.
 * @version 0.0.1
 */
@Log4j2
public class LoginValidator extends AbstractValidator {

    private static final String LOGIN_REGEX_PATTERN = "^[(\\w)-]{4,20}";

    private boolean filter;

    public LoginValidator(boolean filter, ParameterValidator next) {
        super(next);
        this.filter = filter;
    }

    public LoginValidator(ParameterValidator next) {
        super(next);
    }

    @Override
    public Set<Violation> apply(RequestContent content) {
        Set<Violation> result = new HashSet<>();
        Violation violation = new Violation(MessageManager.getMessage("violation.login", (String) content.getSessionAttribute(LOCALE)));
        if (filter) {
            if (content.getRequestParameters().containsKey(RequestConstant.LOGIN)
                    && !content.getRequestParameter(RequestConstant.LOGIN)[0].isBlank()
                    && !content.getRequestParameter(RequestConstant.LOGIN)[0].matches(LOGIN_REGEX_PATTERN)) {
                log.warn("login doesn't match login regex pattern");
                result.add(violation);
            }
        } else {
            if (!content.getRequestParameters().containsKey(RequestConstant.LOGIN)) {
                log.warn("Invalid content parameter: no login parameter in request");
                result.add(violation);
            } else if (!content.getRequestParameter(RequestConstant.LOGIN)[0].matches(LOGIN_REGEX_PATTERN)) {
                log.warn("Invalid content parameter: " + content.getRequestParameter(RequestConstant.LOGIN)[0]);
                result.add(violation);
            }
        }
        if (next != null) {
            result.addAll(next.apply(content));
        }
        return result;
    }
}
