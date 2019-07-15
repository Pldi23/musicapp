package by.platonov.music.command;

import by.platonov.music.command.constant.PageConstant;
import by.platonov.music.entity.Track;
import by.platonov.music.service.CommonService;
import lombok.extern.log4j.Log4j2;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-03.
 * @version 0.0.1
 */
@Log4j2
public class ToRemoveTrackCommand implements Command {

    private CommonService commonService;
    private CommandHandler<Track> handler;

    public ToRemoveTrackCommand(CommonService commonService, CommandHandler<Track> handler) {
        this.commonService = commonService;
        this.handler = handler;
    }

    @Override
    public CommandResult execute(RequestContent content) {
        return handler.transfer(content, PageConstant.REMOVE_CONFIRMATION_PAGE, commonService::searchTrackById);
    }
}
