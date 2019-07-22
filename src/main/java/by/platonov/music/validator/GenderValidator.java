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
 * @author Dzmitry Platonov on 2019-07-16.
 * @version 0.0.1
 */
@Log4j2
public class GenderValidator extends AbstractValidator {

    private static final String GENDER_REGEX_PATTERN = "(?i)male|female";


    public GenderValidator(ParameterValidator next) {
        super(next);
    }

    @Override
    public Set<Violation> apply(RequestContent content) {
        Set<Violation> result = new HashSet<>();
        if (!content.getRequestParameters().containsKey(RequestConstant.GENDER)) {
            log.warn("no parameter gender found");
            result.add(new Violation(MessageManager.getMessage("violation.gender", (String) content.getSessionAttribute(LOCALE))));
        } else if (!content.getRequestParameter(RequestConstant.GENDER)[0].matches(GENDER_REGEX_PATTERN)) {
            log.warn("gender does not match regex pattern");
            result.add(new Violation(MessageManager.getMessage("violation.gender", (String) content.getSessionAttribute(LOCALE))));
        }
        if (next != null) {
            result.addAll(next.apply(content));
        }
        return result;
    }
}
