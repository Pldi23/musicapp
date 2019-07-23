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
 * @author Dzmitry Platonov on 2019-07-14.
 * @version 0.0.1
 */
@Log4j2
public class PlaylistNameValidator extends AbstractValidator {

    private static final String PLAYLIST_NAME_REGEX_PATTERN = ".{2,50}";

    public PlaylistNameValidator(ParameterValidator next) {
        super(next);
    }

    @Override
    public Set<Violation> apply(RequestContent content) {
        Set<Violation> result = new HashSet<>();
        if (!content.getRequestParameters().containsKey(RequestConstant.NAME)) {
            log.warn("Invalid content parameter: no playlist name parameter in request");
            result.add(new Violation(MessageManager.getMessage("violation.playlist.name", (String) content.getSessionAttribute(LOCALE))));
        } else if (!content.getRequestParameter(RequestConstant.NAME)[0].matches(PLAYLIST_NAME_REGEX_PATTERN)) {
            log.warn("Invalid content parameter: " + content.getRequestParameter(RequestConstant.NAME)[0]);
            result.add(new Violation(MessageManager.getMessage("violation.playlist.name", (String) content.getSessionAttribute(LOCALE))));
        }
        if (next != null) {
            result.addAll(next.apply(content));
        }
        return result;
    }
}
