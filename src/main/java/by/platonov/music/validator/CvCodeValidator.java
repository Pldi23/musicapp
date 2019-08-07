package by.platonov.music.validator;

import by.platonov.music.command.RequestContent;
import by.platonov.music.constant.RequestConstant;
import by.platonov.music.message.MessageManager;
import lombok.extern.log4j.Log4j2;

import java.util.HashSet;
import java.util.Set;

import static by.platonov.music.constant.RequestConstant.LOCALE;

/**
 * to validate {@link RequestConstant}.CV_CODE request parameter
 *
 * @author Dzmitry Platonov on 2019-08-06.
 * @version 0.0.1
 */
@Log4j2
public class CvCodeValidator extends AbstractValidator {

    /**
     * exactly 3 digits
     */
    private static final String CV_CODE_REGEX_PATTERN = "\\w{3}";

    public CvCodeValidator(ParameterValidator next) {
        super(next);
    }

    @Override
    public Set<Violation> apply(RequestContent content) {
        Set<Violation> result = new HashSet<>();
        Violation violation = new Violation(MessageManager.getMessage("violation.cvcode",
                (String) content.getSessionAttribute(LOCALE)));

        if (!content.getRequestParameters().containsKey(RequestConstant.CV_CODE)) {
            log.warn("Invalid content parameter: no cv code parameter in request");
            result.add(violation);
        } else if (!content.getRequestParameter(RequestConstant.CV_CODE)[0].matches(CV_CODE_REGEX_PATTERN)) {
            log.warn("Invalid content parameter: " + content.getRequestParameter(RequestConstant.CV_CODE)[0]);
            result.add(violation);
        }

        if (next != null) {
            result.addAll(next.apply(content));
        }
        return result;
    }
}
