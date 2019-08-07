package by.platonov.music.validator;

import by.platonov.music.command.RequestContent;
import by.platonov.music.constant.RequestConstant;
import by.platonov.music.message.MessageManager;
import lombok.extern.log4j.Log4j2;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.HashSet;
import java.util.Set;

import static by.platonov.music.constant.RequestConstant.LOCALE;

/**
 * to validate {@link RequestConstant}.EXPIRY_DATE request parameter
 *
 * @author Dzmitry Platonov on 2019-08-06.
 * @version 0.0.1
 */
@Log4j2
public class ExpirationDateValidator extends AbstractValidator {

    /**
     * no longer than 5 years until now
     */
    private static final int MAX_YEARS_CARD_VALIDITY = 5;

    public ExpirationDateValidator(ParameterValidator next) {
        super(next);
    }

    @Override
    public Set<Violation> apply(RequestContent content) {

        Set<Violation> result = new HashSet<>();
        Violation violation = new Violation(MessageManager.getMessage("violation.expirydate",
                (String) content.getSessionAttribute(LOCALE)));
        DateTimeFormatter dateFormat = new DateTimeFormatterBuilder()
                .appendPattern("yyyy-MM")
                .parseDefaulting(ChronoField.DAY_OF_MONTH, LocalDate.now().getMonth().maxLength())
                .toFormatter();

        if (!content.getRequestParameters().containsKey(RequestConstant.EXPIRY_DATE)) {
            log.warn("Invalid content parameter: no expiry date parameter in request");
            result.add(violation);
        } else if (Period.between(LocalDate.now(), LocalDate.parse(content.getRequestParameter(RequestConstant.EXPIRY_DATE)[0],
                dateFormat)).isNegative()
                || Period.between(LocalDate.now(), LocalDate.parse(content.getRequestParameter(RequestConstant.EXPIRY_DATE)[0],
                dateFormat)).getYears() > MAX_YEARS_CARD_VALIDITY) {
            log.warn("Invalid content parameter: " + content.getRequestParameter(RequestConstant.EXPIRY_DATE)[0]);
            result.add(violation);
        }

        if (next != null) {
            result.addAll(next.apply(content));
        }
        return result;
    }
}
