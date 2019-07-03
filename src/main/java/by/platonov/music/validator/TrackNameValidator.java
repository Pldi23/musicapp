package by.platonov.music.validator;

import by.platonov.music.command.RequestContent;
import by.platonov.music.command.constant.RequestConstant;
import lombok.extern.log4j.Log4j2;

import java.util.HashSet;
import java.util.Set;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-02.
 * @version 0.0.1
 */
@Log4j2
public class TrackNameValidator extends AbstractValidator {

    private static final String TRACKNAME_REGEX_PATTERN = "(?U).{1,30}(?<!(.mp3)|(.wav)|(.audio)|(.format))$";

    private static final String INCORRECT_TRACKNAME = "Track name must contain at least one symbol and doesn't have " +
            "format suffix";

    public TrackNameValidator(ParameterValidator next) {
        super(next);
    }

    @Override
    public Set<Violation> apply(RequestContent content) {
        Set<Violation> result = new HashSet<>();
        if (!content.getRequestParameters().containsKey(RequestConstant.TRACKNAME)) {
            log.warn("No track name parameter found");
            result.add(new Violation(INCORRECT_TRACKNAME));
        } else if (!content.getRequestParameter(RequestConstant.TRACKNAME)[0].matches(TRACKNAME_REGEX_PATTERN)) {
            log.warn("track name parameter not matched appropriate regex pattern");
            result.add(new Violation(INCORRECT_TRACKNAME));
        }
        if (next != null) {
            result.addAll(next.apply(content));
        }
        return result;
    }
}