package by.platonov.music.validator;

import by.platonov.music.message.MessageManager;
import by.platonov.music.command.RequestContent;
import by.platonov.music.constant.RequestConstant;
import lombok.extern.log4j.Log4j2;

import java.util.HashSet;
import java.util.Set;

import static by.platonov.music.constant.RequestConstant.LOCALE;

/**
 * to validate {@link RequestConstant}.GENRE request parameter
 *
 * @author Dzmitry Platonov on 2019-07-02.
 * @version 0.0.1
 */
@Log4j2
public class GenreValidator extends AbstractValidator {

    /**
     * 3-30 non-digit symbols
     */
    private static final String GENRE_REGEX_PATTERN = "\\D{3,30}";

    public GenreValidator(ParameterValidator next) {
        super(next);
    }

    @Override
    public Set<Violation> apply(RequestContent content) {
        Set<Violation> result = new HashSet<>();
        if (!content.getRequestParameters().containsKey(RequestConstant.GENRE)) {
            log.warn("no parameter genre found");
            result.add(new Violation(MessageManager.getMessage("violation.genre", (String) content.getSessionAttribute(LOCALE))));
        } else if (!content.getRequestParameter(RequestConstant.GENRE)[0].matches(GENRE_REGEX_PATTERN)) {
            log.warn("genre does not match regex pattern");
            result.add(new Violation(MessageManager.getMessage("violation.genre", (String) content.getSessionAttribute(LOCALE))));
        }
        if (next != null) {
            result.addAll(next.apply(content));
        }
        return result;
    }
}
