package by.platonov.music.validator;

import by.platonov.music.message.MessageManager;
import by.platonov.music.command.RequestContent;
import by.platonov.music.constant.RequestConstant;
import lombok.extern.log4j.Log4j2;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashSet;
import java.util.Set;

import static by.platonov.music.constant.RequestConstant.LOCALE;

/**
 * to validate {@link RequestConstant}.RELEASE_DATE request parameter
 *
 * @author Dzmitry Platonov on 2019-07-02.
 * @version 0.0.1
 */
@Log4j2
public class ReleaseDateValidator extends AbstractValidator {

    public ReleaseDateValidator(ParameterValidator next) {
        super(next);
    }

    /**
     * no release in future allowed
     * @param content {@link RequestContent}
     * @return empty set if no violations detected, and violation.incorrectrelease or violation.futurerelease message
     * if violation found
     */
    @Override
    public Set<Violation> apply(RequestContent content) {
        Set<Violation> result = new HashSet<>();
        if (!content.getRequestParameters().containsKey(RequestConstant.RELEASE_DATE)
                || content.getRequestParameter(RequestConstant.RELEASE_DATE)[0].isBlank()) {
            log.warn("Invalid content parameter: no release date parameter found in request");
            result.add(new Violation(MessageManager.getMessage("violation.incorrectrelease", (String) content.getSessionAttribute(LOCALE))));
        } else {
            Period period = Period.between(LocalDate.parse(content.getRequestParameter(RequestConstant.RELEASE_DATE)[0]),
                    LocalDate.now());
            if (period.isNegative()) {
                log.warn("invalid release date: parameter is future");
                result.add(new Violation(MessageManager.getMessage("violation.futurerelease", (String) content.getSessionAttribute(LOCALE))));
            }
        }
        if (next != null) {
            result.addAll(next.apply(content));
        }
        return result;
    }
}
