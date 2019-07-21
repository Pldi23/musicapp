package by.platonov.music.validator;

import by.platonov.music.MessageManager;
import by.platonov.music.command.RequestContent;
import by.platonov.music.command.constant.RequestConstant;
import lombok.extern.log4j.Log4j2;

import java.util.HashSet;
import java.util.Set;

import static by.platonov.music.command.constant.RequestConstant.LOCALE;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-21.
 * @version 0.0.1
 */
@Log4j2
public class MusicianValidator extends AbstractValidator {

    private static final String MUSICIAN_REGEX_PATTERN = "(?U).{1,30}";

    public MusicianValidator(ParameterValidator next) {
        super(next);
    }

    @Override
    public Set<Violation> apply(RequestContent content) {
        Set<Violation> result = new HashSet<>();
        Violation violation = new Violation(MessageManager.getMessage("violation.singer",
                (String) content.getSessionAttribute(LOCALE)));
        if (!content.getRequestParameters().containsKey(RequestConstant.MUSICIAN)) {
            log.warn("No musician parameter found");
            result.add(violation);
        } else if (!content.getRequestParameter(RequestConstant.MUSICIAN)[0].matches(MUSICIAN_REGEX_PATTERN)) {
            log.warn("Musician doesn't match regex pattern");
            result.add(violation);
        }
        if (next != null) {
            result.addAll(next.apply(content));
        }
        return result;
    }
}
