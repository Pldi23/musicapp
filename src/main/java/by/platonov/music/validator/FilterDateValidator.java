package by.platonov.music.validator;

import by.platonov.music.message.MessageManager;
import by.platonov.music.command.RequestContent;
import lombok.extern.log4j.Log4j2;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashSet;
import java.util.Set;

import static by.platonov.music.constant.RequestConstant.LOCALE;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-18.
 * @version 0.0.1
 */
@Log4j2
public class FilterDateValidator extends AbstractValidator {

    private String parameterFrom;
    private String parameterTo;

    public FilterDateValidator(String parameterFrom, String parameterTo, ParameterValidator next) {
        super(next);
        this.parameterFrom = parameterFrom;
        this.parameterTo = parameterTo;
    }

    @Override
    public Set<Violation> apply(RequestContent content) {
        Set<Violation> result = new HashSet<>();
        String locale = (String) content.getSessionAttribute(LOCALE);
        Violation violation = new Violation(MessageManager.getMessage("violation.release.filter", locale));
        LocalDate from;
        LocalDate to;
        if (!content.getRequestParameter(parameterFrom)[0].isBlank()
                && !content.getRequestParameter(parameterTo)[0].isBlank()) {
            from = LocalDate.parse(content.getRequestParameter(parameterFrom)[0]);
            to = LocalDate.parse(content.getRequestParameter(parameterTo)[0]);
            if (Period.between(from, to).isNegative() || Period.between(from, LocalDate.now()).isNegative()
            || Period.between(to, LocalDate.now()).isNegative()) {
                log.warn("negative period between from-date and to-date ");
                result.add(violation);
            }
        } else if (!content.getRequestParameter(parameterFrom)[0].isBlank()) {
            from = LocalDate.parse(content.getRequestParameter(parameterFrom)[0]);
            if (Period.between(from, LocalDate.now()).isNegative()) {
                log.warn("from-date in future");
                result.add(violation);
            }
        } else if (!content.getRequestParameter(parameterTo)[0].isBlank()) {
            to = LocalDate.parse(content.getRequestParameter(parameterTo)[0]);
            if (Period.between(to, LocalDate.now()).isNegative()) {
                log.warn("to-date in future");
                result.add(violation);
            }
        }
        if (next != null) {
            result.addAll(next.apply(content));
        }
        return result;
    }
}
