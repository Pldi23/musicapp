package by.platonov.music.validator;

import by.platonov.music.message.MessageManager;
import by.platonov.music.command.RequestContent;
import by.platonov.music.command.constant.RequestConstant;
import lombok.extern.log4j.Log4j2;

import java.util.HashSet;
import java.util.Set;

import static by.platonov.music.command.constant.RequestConstant.LOCALE;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-03.
 * @version 0.0.1
 */
@Log4j2
public class IdValidator extends AbstractValidator {

//    private static final String ID_INVALID_MESSAGE = "Id should be positive";
//    private static final String ID_PARAMETER_ABSENT_MESSAGE = "Id parameter not found in request";

    public IdValidator(ParameterValidator next) {
        super(next);
    }

    @Override
    public Set<Violation> apply(RequestContent content) {
        Set<Violation> result = new HashSet<>();
        if (!content.getRequestParameters().containsKey(RequestConstant.ID)) {
            log.warn("no parameter id found");
            result.add(new Violation(MessageManager.getMessage("violation.noid", (String) content.getSessionAttribute(LOCALE))));
        } else if (Long.parseLong(content.getRequestParameter(RequestConstant.ID)[0]) <= 0) {
            log.warn("entered id value not positive");
            result.add(new Violation(MessageManager.getMessage("violation.id", (String) content.getSessionAttribute(LOCALE))));
        }
        if (next != null) {
            result.addAll(next.apply(content));
        }
        return result;
    }
}
