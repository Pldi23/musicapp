package by.platonov.music.command;

import by.platonov.music.MessageManager;
import by.platonov.music.command.constant.PageConstant;
import by.platonov.music.command.constant.RequestConstant;
import by.platonov.music.entity.Entity;
import by.platonov.music.exception.ServiceException;
import by.platonov.music.service.CommonService;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.Map;

import static by.platonov.music.command.constant.RequestConstant.PROCESS;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-21.
 * @version 0.0.1
 */
@Log4j2
public class RemoveCancelCommand implements Command {

    private CommonService commonService;

    public RemoveCancelCommand(CommonService commonService) {
        this.commonService = commonService;
    }

    @Override
    public CommandResult execute(RequestContent content) {
        String attributeKey = content.getRequestParameter(RequestConstant.ENTITY_TYPE)[0];
        try {
            if (detectEntity(content).isEmpty()) {
                return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.ENTITY_REMOVED_PAGE,
                        Map.of(PROCESS, MessageManager.getMessage("failed",
                                (String) content.getSessionAttribute(RequestConstant.LOCALE))));
            }

            return new CommandResult(CommandResult.ResponseType.FORWARD, detectPage(content),
                    Map.of(attributeKey, detectEntity(content).get(0)));
        } catch (EntityParameterNotFoundException | ServiceException e) {
            log.error("command couldn't provide entity to cancel removing", e);
            return new CommandResult(CommandResult.ResponseType.REDIRECT, PageConstant.ERROR_REDIRECT_PAGE);
        }
    }

    private List<? extends Entity> detectEntity(RequestContent content) throws EntityParameterNotFoundException, ServiceException {
        List<? extends Entity> result;
        String entityId = content.getRequestParameter(RequestConstant.ID)[0];
        String entityType = content.getRequestParameter(RequestConstant.ENTITY_TYPE)[0];

        switch (entityType) {
            case RequestConstant.TRACK:
                result = commonService.searchTrackById(entityId);
                break;
            case RequestConstant.PLAYLIST:
                result = commonService.searchPlaylistById(entityId);
                break;
            case RequestConstant.MUSICIAN:
                result = commonService.searchMusicianById(entityId);
                break;
            case RequestConstant.GENRE:
                result = commonService.searchGenreById(entityId);
                break;
            default:
                throw new EntityParameterNotFoundException("could not detect entity");
        }
        return result;
    }

    private String detectPage(RequestContent content) throws EntityParameterNotFoundException {
        String result;
        String entityType = content.getRequestParameter(RequestConstant.ENTITY_TYPE)[0];
        switch (entityType) {
            case RequestConstant.TRACK:
                result = PageConstant.TRACK_PAGE;
                break;
            case RequestConstant.PLAYLIST:
                result = PageConstant.PLAYLIST_PAGE;
                break;
            case RequestConstant.MUSICIAN:
                result = PageConstant.MUSICIAN_PAGE;
                break;
            case RequestConstant.GENRE:
                result = PageConstant.GENRE_PAGE;
                break;
            default:
                throw new EntityParameterNotFoundException("Entity type not specified correctly, could not detect page. " +
                        "Request doesn't contain parameter or parameter is invalid");
        }
        return result;
    }
}
