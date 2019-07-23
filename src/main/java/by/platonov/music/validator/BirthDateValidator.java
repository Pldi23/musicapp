package by.platonov.music.validator;

import by.platonov.music.message.MessageManager;
import by.platonov.music.command.constant.RequestConstant;
import by.platonov.music.command.RequestContent;
import lombok.extern.log4j.Log4j2;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashSet;
import java.util.Set;

import static by.platonov.music.command.constant.RequestConstant.LOCALE;

/**
 * @author dzmitryplatonov on 2019-06-19.
 * @version 0.0.1
 */
@Log4j2
public class BirthDateValidator extends AbstractValidator {

    private static final int MINIMUM_AGE_ALLOWED_BY_APPLICATION = 6;

    public BirthDateValidator(ParameterValidator next) {
        super(next);
    }

    @Override
    public Set<Violation> apply(RequestContent content) {
        Set<Violation> result = new HashSet<>();
        if (!content.getRequestParameters().containsKey(RequestConstant.BIRTHDATE)) {
            log.warn("Invalid content parameter: no birthdate parameter in request");
            result.add(new Violation(MessageManager.getMessage("violation.birthdate", (String) content.getSessionAttribute(LOCALE))));
        } else {
            Period period = Period.between(LocalDate.parse(content.getRequestParameter(RequestConstant.BIRTHDATE)[0]), LocalDate.now());
            if (period.getYears() < MINIMUM_AGE_ALLOWED_BY_APPLICATION) {
                log.warn("Invalid content parameter: " + content.getRequestParameter(RequestConstant.BIRTHDATE)[0]);
                result.add(new Violation(MessageManager.getMessage("violation.birthdate", (String) content.getSessionAttribute(LOCALE))));
            }
        }
        if (next != null) {
            result.addAll(next.apply(content));
        }
        return result;
    }
}
