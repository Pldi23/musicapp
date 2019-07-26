package by.platonov.music.validator;

import by.platonov.music.message.MessageManager;
import by.platonov.music.constant.RequestConstant;
import by.platonov.music.command.RequestContent;
import lombok.extern.log4j.Log4j2;

import java.util.HashSet;
import java.util.Set;

import static by.platonov.music.constant.RequestConstant.LOCALE;

/**
 * @author dzmitryplatonov on 2019-06-18.
 * @version 0.0.1
 */
@Log4j2
public class PasswordValidator extends AbstractValidator {

    private static final String PASSWORD_REGEX_PATTERN = "^(?=.*\\d)(?=.*\\p{Lower})(?=.*\\p{Upper})(?=.*\\p{Punct})" +
            "(?=\\S+$).{8,20}$";

    private String parameter;

    public PasswordValidator(String parameter, ParameterValidator next) {
        super(next);
        this.parameter = parameter;
    }

    public PasswordValidator(ParameterValidator next) {
        super(next);
    }

    @Override
    public Set<Violation> apply(RequestContent content) {
        parameter = parameter != null ? parameter : RequestConstant.PASSWORD;
        Set<Violation> result = new HashSet<>();
        if (!content.getRequestParameters().containsKey(parameter)) {
            log.warn("Invalid content parameter: no password parameter in request");
            result.add(new Violation(MessageManager.getMessage("violation.password", (String) content.getSessionAttribute(LOCALE))));
        } else if (!content.getRequestParameter(parameter)[0].matches(PASSWORD_REGEX_PATTERN)) {
            log.warn("Invalid content parameter: " + content.getRequestParameter(parameter)[0]);
            result.add(new Violation(MessageManager.getMessage("violation.password", (String) content.getSessionAttribute(LOCALE))));
        }
        if (next != null) {
            result.addAll(next.apply(content));
        }
        return result;
    }
}
