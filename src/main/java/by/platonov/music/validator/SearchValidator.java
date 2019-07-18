package by.platonov.music.validator;

import by.platonov.music.MessageManager;
import by.platonov.music.command.RequestContent;
import by.platonov.music.command.constant.RequestConstant;
import lombok.extern.log4j.Log4j2;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static by.platonov.music.command.constant.RequestConstant.LOCALE;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-18.
 * @version 0.0.1
 */
@Log4j2
public class SearchValidator extends AbstractValidator{

    private static final String SEARCH_REGEX_PATTERN = ".{1,10}";

    public SearchValidator(ParameterValidator next) {
        super(next);
    }

    @Override
    public Set<Violation> apply(RequestContent content) {
        Set<Violation> result = new HashSet<>();
        if (!content.getRequestParameters().containsKey(RequestConstant.SEARCH_REQUEST)) {
            log.warn("Invalid content parameter: no searchrequest parameter in request");
            result.add(new Violation(MessageManager.getMessage("violation.search.request", (String) content.getSessionAttribute(LOCALE))));
        } else if (!content.getRequestParameter(RequestConstant.SEARCH_REQUEST)[0].matches(SEARCH_REGEX_PATTERN)) {
            log.warn("Invalid content parameter: " + content.getRequestParameter(RequestConstant.SEARCH_REQUEST)[0]);
            result.add(new Violation(MessageManager.getMessage("violation.search.request", (String) content.getSessionAttribute(LOCALE))));
        }
        if (next != null) {
            result.addAll(next.apply(content));
        }
        return result;
    }
}
