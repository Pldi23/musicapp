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
 * @author Dzmitry Platonov on 2019-07-02.
 * @version 0.0.1
 */
@Log4j2
public class SingerValidator extends AbstractValidator {

    private static final String SINGER_REGEX_PATTERN = "(?U).{1,30}";
    private static final int MINIMUN_QUANTITY_SINGERS = 1;

//    private static final String INCORRECT_SINGER =
//            "You should enter at least 1 singer, and his name should contain at least one symbol";

    public SingerValidator(ParameterValidator next) {
        super(next);
    }

    @Override
    public Set<Violation> apply(RequestContent content) {
        Set<Violation> result = new HashSet<>();
        if (!content.getRequestParameters().containsKey(RequestConstant.SINGER)) {
            log.warn("no singer parameter found");
            result.add(new Violation(MessageManager.getMessage("violation.singer", (String) content.getSessionAttribute(LOCALE))));
        } else if (content.getRequestParameter(RequestConstant.SINGER).length >= MINIMUN_QUANTITY_SINGERS
                && Arrays.stream(content.getRequestParameter(RequestConstant.SINGER)).noneMatch(s -> s.matches(SINGER_REGEX_PATTERN))) {
            //Arrays.stream(content.getRequestParameter(RequestConstant.SINGER)).anyMatch(s -> !s.matches(SINGER_REGEX_PATTERN))
            log.warn("One of specified singers doesn't match singer regex pattern");
            result.add(new Violation(MessageManager.getMessage("violation.singer", (String) content.getSessionAttribute(LOCALE))));
        }
        if (next != null) {
            result.addAll(next.apply(content));
        }
        return result;
    }
}
