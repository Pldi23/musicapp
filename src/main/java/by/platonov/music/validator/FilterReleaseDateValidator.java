package by.platonov.music.validator;

import by.platonov.music.MessageManager;
import by.platonov.music.command.RequestContent;
import by.platonov.music.command.constant.RequestConstant;
import lombok.extern.log4j.Log4j2;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashSet;
import java.util.Set;

import static by.platonov.music.command.constant.RequestConstant.*;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-18.
 * @version 0.0.1
 */
@Log4j2
public class FilterReleaseDateValidator extends AbstractValidator {

    public FilterReleaseDateValidator(ParameterValidator next) {
        super(next);
    }

    @Override
    public Set<Violation> apply(RequestContent content) {
        Set<Violation> result = new HashSet<>();
        String locale = (String) content.getSessionAttribute(LOCALE);
        Violation violation = new Violation(MessageManager.getMessage("violation.release.filter", locale));
        LocalDate from;
        LocalDate to;
        if (!content.getRequestParameter(RELEASE_FROM)[0].isBlank()
                && !content.getRequestParameter(RELEASE_TO)[0].isBlank()) {
            from = LocalDate.parse(content.getRequestParameter(RequestConstant.RELEASE_FROM)[0]);
            to = LocalDate.parse(content.getRequestParameter(RequestConstant.RELEASE_TO)[0]);
            if (Period.between(from, to).isNegative() || Period.between(from, LocalDate.now()).isNegative()
            || Period.between(to, LocalDate.now()).isNegative()) {
                log.warn("negative period between from-release date and to-release date ");
                result.add(violation);
            }
        } else if (!content.getRequestParameter(RELEASE_FROM)[0].isBlank()) {
            from = LocalDate.parse(content.getRequestParameter(RequestConstant.RELEASE_FROM)[0]);
            if (Period.between(from, LocalDate.now()).isNegative()) {
                log.warn("from release date in future");
                result.add(violation);
            }
        } else if (!content.getRequestParameter(RELEASE_TO)[0].isBlank()) {
            to = LocalDate.parse(content.getRequestParameter(RequestConstant.RELEASE_TO)[0]);
            if (Period.between(to, LocalDate.now()).isNegative()) {
                log.warn("to-release date in future");
                result.add(violation);
            }
        }
        if (next != null) {
            result.addAll(next.apply(content));
        }
        return result;
    }
}
