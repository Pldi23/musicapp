package by.platonov.music.validator;

import by.platonov.music.command.RequestContent;
import by.platonov.music.command.constant.RequestConstant;
import lombok.extern.log4j.Log4j2;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashSet;
import java.util.Set;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-02.
 * @version 0.0.1
 */
@Log4j2
public class ReleaseDateValidator extends AbstractValidator {

    private static final String RELEASE_DATE_CANNOT_BE_FUTURE_MESSAGE = "Release date could not be in future";
    private static final String INCORRECT_RELEASE_DATE_MESSAGE = "Incorrect release date parameter";

    public ReleaseDateValidator(ParameterValidator next) {
        super(next);
    }

    @Override
    public Set<Violation> apply(RequestContent content) {
        Set<Violation> result = new HashSet<>();
        if (!content.getRequestParameters().containsKey(RequestConstant.RELEASE_DATE)) {
            log.warn("Invalid content parameter: no release date parameter found in request");
            result.add(new Violation(INCORRECT_RELEASE_DATE_MESSAGE));
        } else {
            Period period = Period.between(LocalDate.parse(content.getRequestParameter(RequestConstant.RELEASE_DATE)[0]),
                    LocalDate.now());
            if (period.isNegative()) {
                log.warn("invalid release date: parameter is future");
                result.add(new Violation(RELEASE_DATE_CANNOT_BE_FUTURE_MESSAGE));
            }
        }
        if (next != null) {
            result.addAll(next.apply(content));
        }
        return result;
    }
}
