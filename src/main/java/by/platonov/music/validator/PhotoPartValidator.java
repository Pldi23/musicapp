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
 * @author Dzmitry Platonov on 2019-07-15.
 * @version 0.0.1
 */
@Log4j2
public class PhotoPartValidator extends AbstractValidator {

    public PhotoPartValidator(ParameterValidator next) {
        super(next);
    }

    @Override
    public Set<Violation> apply(RequestContent content) {
        Set<Violation> result = new HashSet<>();
        if (content.getPart(RequestConstant.IMG_PATH).isEmpty()) {
            log.warn("Invalid content part: no media path part in request");
            result.add(new Violation(MessageManager.getMessage("violation.nofile", (String) content.getSessionAttribute(LOCALE))));
        } else if (!content.getPart(RequestConstant.IMG_PATH).get().getContentType()
                .equalsIgnoreCase("image/jpeg")) {
            log.warn("Invalid content part: wrong file format");
            result.add(new Violation(MessageManager.getMessage("violation.format", (String) content.getSessionAttribute(LOCALE))));
        }
        if (next != null) {
            result.addAll(next.apply(content));
        }
        return result;
    }
}
