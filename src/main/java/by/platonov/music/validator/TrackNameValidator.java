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
 * @author Dzmitry Platonov on 2019-07-02.
 * @version 0.0.1
 */
@Log4j2
public class TrackNameValidator extends AbstractValidator {

    private static final String TRACKNAME_REGEX_PATTERN = "(?U).{1,30}(?<!(.mp3)|(.wav)|(.audio)|(.format))$";

    private boolean filter;

    public TrackNameValidator(boolean filter, ParameterValidator next) {
        super(next);
        this.filter = filter;
    }

    public TrackNameValidator(ParameterValidator next) {
        super(next);
    }

    @Override
    public Set<Violation> apply(RequestContent content) {
        Set<Violation> result = new HashSet<>();
        String locale = (String) content.getSessionAttribute(LOCALE);
        Violation violation = new Violation(MessageManager.getMessage("violation.trackname", locale));

        if (filter) {
            if (content.getRequestParameters().containsKey(RequestConstant.TRACKNAME)
                    && !content.getRequestParameter(RequestConstant.TRACKNAME)[0].isBlank()
                    && !content.getRequestParameter(RequestConstant.TRACKNAME)[0].matches(TRACKNAME_REGEX_PATTERN)) {
                log.warn("track name parameter not matched appropriate regex pattern");
                result.add(violation);
            }
        } else {
            if (!content.getRequestParameters().containsKey(RequestConstant.TRACKNAME)) {
                log.warn("No track name parameter found");
                result.add(violation);
            } else if (!content.getRequestParameter(RequestConstant.TRACKNAME)[0].matches(TRACKNAME_REGEX_PATTERN)) {
                log.warn("track name parameter not matched appropriate regex pattern");
                result.add(violation);
            }
        }
        if (next != null) {
            result.addAll(next.apply(content));
        }
        return result;
    }
}
