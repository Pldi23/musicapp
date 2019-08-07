package by.platonov.music.validator;

import by.platonov.music.command.RequestContent;
import by.platonov.music.constant.RequestConstant;
import by.platonov.music.message.MessageManager;
import lombok.extern.log4j.Log4j2;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static by.platonov.music.constant.RequestConstant.*;

/**
 * to validate {@link RequestConstant}.AUTHOR request parameter
 *
 * @author Dzmitry Platonov on 2019-07-30.
 * @version 0.0.1
 */
@Log4j2
public class AuthorValidator extends AbstractValidator {

    /**
     * 1-30 symbols
     */
    private static final String AUTHOR_REGEX_PATTERN = "(?U).{1,30}";
    /**
     * no more than 6 authors in request
     */
    private static final int MAXIMUM_QUANTITY_AUTHORS = 6;

    public AuthorValidator(ParameterValidator next) {
        super(next);
    }

    @Override
    public Set<Violation> apply(RequestContent content) {
        Set<Violation> result = new HashSet<>();
        Violation violation = new Violation(MessageManager.getMessage("violation.author",
                (String) content.getSessionAttribute(LOCALE)));

        if (content.getRequestParameters().containsKey(AUTHOR)
                && Arrays.stream(content.getRequestParameter(AUTHOR)).anyMatch(s -> !s.isBlank())
                && (content.getRequestParameter(AUTHOR).length > MAXIMUM_QUANTITY_AUTHORS
                || Arrays.stream(content.getRequestParameter(AUTHOR))
                .noneMatch(s -> s.matches(AUTHOR_REGEX_PATTERN)))) {
            log.warn("One of specified authors doesn't match author regex pattern");
            result.add(violation);
        }

        if (next != null) {
            result.addAll(next.apply(content));
        }
        return result;
    }
}
