package by.platonov.music.command.impl;

import by.platonov.music.command.Command;
import by.platonov.music.command.CommandResult;
import by.platonov.music.command.RequestContent;
import by.platonov.music.message.MessageManager;
import by.platonov.music.constant.PageConstant;
import by.platonov.music.constant.RequestConstant;
import by.platonov.music.service.CommonService;
import lombok.extern.log4j.Log4j2;

import java.util.Map;

import static by.platonov.music.constant.RequestConstant.PROCESS;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-21.
 * @version 0.0.1
 */
@Log4j2
public class RemoveCancelCommand implements Command {

    @Override
    public CommandResult execute(RequestContent content) {
        CommandResult commandResult;
        switch (content.getRequestParameter(RequestConstant.ENTITY_TYPE)[0]) {
            case RequestConstant.TRACK:
                commandResult = new TrackDetailCommand(new CommonService()).execute(content);
                break;
            case RequestConstant.MUSICIAN:
                commandResult = new MusicianDetailCommand(new CommonService()).execute(content);
                break;
            case RequestConstant.PLAYLIST:
                commandResult = new PlaylistDetailCommand(new CommonService()).execute(content);
                break;
            default:
                return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.ENTITY_REMOVED_PAGE,
                        Map.of(PROCESS, MessageManager.getMessage("failed",
                                (String) content.getSessionAttribute(RequestConstant.LOCALE))));

        }
        return commandResult;
    }
}
