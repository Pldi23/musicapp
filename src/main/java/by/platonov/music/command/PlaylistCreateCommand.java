package by.platonov.music.command;

import by.platonov.music.MessageManager;
import by.platonov.music.command.constant.PageConstant;
import by.platonov.music.command.constant.RequestConstant;
import by.platonov.music.entity.User;
import by.platonov.music.exception.ServiceException;
import by.platonov.music.service.CommonService;
import by.platonov.music.validator.PlaylistNameValidator;
import by.platonov.music.validator.Violation;
import lombok.extern.log4j.Log4j2;

import java.util.Map;
import java.util.Set;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-14.
 * @version 0.0.1
 */
@Log4j2
public class PlaylistCreateCommand implements Command {

    private CommonService commonService;

    public PlaylistCreateCommand(CommonService commonService) {
        this.commonService = commonService;
    }

    @Override
    public CommandResult execute(RequestContent content) {
        CommandResult commandResult;
        Set<Violation> violations = new PlaylistNameValidator(null).apply(content);
        String result;
        if (violations.isEmpty()) {
            String name = content.getRequestParameter(RequestConstant.NAME)[0];
            User user = (User) content.getSessionAttribute(RequestConstant.USER);
            try {
                result = commonService.createPlaylist(user, name) ? MessageManager.getMessage("added", (String) content.getSessionAttribute(RequestConstant.LOCALE)) :
                        MessageManager.getMessage("failed", (String) content.getSessionAttribute(RequestConstant.LOCALE));
                commandResult = new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.PLAYLIST_CREATE_PAGE,
                        Map.of(RequestConstant.PROCESS, result));
            } catch (ServiceException e) {
                log.error("command can't add playlist ", e);
                return new CommandResult(CommandResult.ResponseType.REDIRECT, PageConstant.ERROR_REDIRECT_PAGE);
            }

        } else {
            result = "\u2718";
            for (Violation violation : violations) {
                result = result.concat(violation.getMessage());
            }
            commandResult = new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.PLAYLIST_CREATE_PAGE,
                    Map.of(RequestConstant.PROCESS, result));
        }
        return commandResult;
    }
}
