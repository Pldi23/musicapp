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
 * @author Dzmitry Platonov on 2019-07-01.
 * @version 0.0.1
 */
@Log4j2
public class FilePartValidator extends AbstractValidator {

//    private static final String FILE_PART_INCORRECT_MESSAGE = "No file part found";
//    private static final String FILE_PART_WRONG_FORMAT_MESSAGE = "Wrong file format";

    public FilePartValidator(ParameterValidator next) {
        super(next);
    }

    @Override
    public Set<Violation> apply(RequestContent content) {
        Set<Violation> result = new HashSet<>();
        if (content.getPart(RequestConstant.MEDIA_PATH).isEmpty()) {
            log.warn("Invalid content part: no media path part in request");
            result.add(new Violation(MessageManager.getMessage("violation.nofile", (String) content.getSessionAttribute(LOCALE))));
        } else if (!content.getPart(RequestConstant.MEDIA_PATH).get().getContentType()
                .equalsIgnoreCase("audio/mp3")) {
            log.warn("Invalid content part: wrong file format");
            result.add(new Violation(MessageManager.getMessage("violation.format", (String) content.getSessionAttribute(LOCALE))));
        }
        if (next != null) {
            result.addAll(next.apply(content));
        }
        return result;
    }
}
