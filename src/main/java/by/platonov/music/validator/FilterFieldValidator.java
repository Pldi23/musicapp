package by.platonov.music.validator;

import by.platonov.music.message.MessageManager;
import by.platonov.music.command.RequestContent;
import lombok.extern.log4j.Log4j2;

import java.util.HashSet;
import java.util.Set;

import static by.platonov.music.constant.RequestConstant.LOCALE;

/**
 * to validate input parameter from request before filter command
 *
 * @author Dzmitry Platonov on 2019-07-22.
 * @version 0.0.1
 */
@Log4j2
public class FilterFieldValidator extends AbstractValidator {

    private static final String FILTER_REGEX_PATTERN = ".{1,20}";

    private String parameter;

    public FilterFieldValidator(String parameter, ParameterValidator next) {
        super(next);
        this.parameter = parameter;
    }

    @Override
    public Set<Violation> apply(RequestContent content) {
        Set<Violation> result = new HashSet<>();
        Violation violation = new Violation(MessageManager.getMessage("violation.filter", (String) content.getSessionAttribute(LOCALE)));

        if (content.getRequestParameters().containsKey(parameter)
                && !content.getRequestParameter(parameter)[0].isBlank()
                && !content.getRequestParameter(parameter)[0].matches(FILTER_REGEX_PATTERN)) {
            log.warn(parameter + " doesn't match regex pattern");
            result.add(violation);
        }
        if (next != null) {
            result.addAll(next.apply(content));
        }
        return result;
    }
}
