package by.platonov.music.validator;

import by.platonov.music.message.MessageManager;
import by.platonov.music.command.RequestContent;
import by.platonov.music.constant.RequestConstant;
import lombok.extern.log4j.Log4j2;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static by.platonov.music.constant.RequestConstant.LOCALE;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-02.
 * @version 0.0.1
 */
@Log4j2
public class SingerValidator extends AbstractValidator {

    private static final String SINGER_REGEX_PATTERN = "(?U).{1,30}";
    private static final int MINIMUM_QUANTITY_SINGERS = 1;

    private boolean filter;

    public SingerValidator(boolean filter, ParameterValidator next) {
        super(next);
        this.filter = filter;
    }

    public SingerValidator(ParameterValidator next) {
        super(next);
    }

    @Override
    public Set<Violation> apply(RequestContent content) {
        Set<Violation> result = new HashSet<>();
        Violation violation = new Violation(MessageManager.getMessage("violation.singer",
                (String) content.getSessionAttribute(LOCALE)));
        if (filter) {
            if (content.getRequestParameters().containsKey(RequestConstant.SINGER)
                    && !content.getRequestParameter(RequestConstant.SINGER)[0].isBlank()
                    && !content.getRequestParameter(RequestConstant.SINGER)[0].matches(SINGER_REGEX_PATTERN)) {
                log.warn("One of specified singers doesn't match singer regex pattern");
                result.add(violation);
            }
        } else {
            if (!content.getRequestParameters().containsKey(RequestConstant.SINGER)) {
                log.warn("no singer parameter found");
                result.add(violation);
            } else if (content.getRequestParameter(RequestConstant.SINGER).length >= MINIMUM_QUANTITY_SINGERS
                    && Arrays.stream(content.getRequestParameter(RequestConstant.SINGER)).noneMatch(s -> s.matches(SINGER_REGEX_PATTERN))) {
                log.warn("One of specified singers doesn't match singer regex pattern");
                result.add(violation);
            }
        }
        if (next != null) {
            result.addAll(next.apply(content));
        }
        return result;
    }
}
