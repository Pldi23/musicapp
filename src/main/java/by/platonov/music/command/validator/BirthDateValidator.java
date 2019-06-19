package by.platonov.music.command.validator;

import by.platonov.music.command.RequestConstant;
import by.platonov.music.command.RequestContent;
import lombok.extern.log4j.Log4j2;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashSet;
import java.util.Set;

/**
 * @author dzmitryplatonov on 2019-06-19.
 * @version 0.0.1
 */
@Log4j2
public class BirthDateValidator extends AbstractValidator {

    private static final int MINIMUM_AGE_ALLOWED_BY_APPLICATION = 6;

    private static final String USER_AGE_BEYOND_MINIMAL_LEVEL_MESSAGE = "User of the application must be older then 6 years";

    public BirthDateValidator(ParameterValidator next) {
        super(next);
    }

    @Override
    public Set<Violation> apply(RequestContent content) {
        Set<Violation> result = new HashSet<>();
        Period period = Period.between(LocalDate.parse(content.getRequestParameter(RequestConstant.BIRTHDATE)[0]), LocalDate.now());
        if (period.getYears() < MINIMUM_AGE_ALLOWED_BY_APPLICATION) {
            log.warn("Invalid content parameter: " + content.getRequestParameter(RequestConstant.BIRTHDATE)[0]);
            result.add(new Violation(USER_AGE_BEYOND_MINIMAL_LEVEL_MESSAGE));
        }
        if (next != null) {
            result.addAll(next.apply(content));
        }
        return result;
    }
}
