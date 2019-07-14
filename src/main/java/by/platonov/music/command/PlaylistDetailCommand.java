package by.platonov.music.command;

import by.platonov.music.command.constant.PageConstant;
import by.platonov.music.service.CommonService;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-13.
 * @version 0.0.1
 */
public class PlaylistDetailCommand implements Command {

    private CommonService commonService;

    public PlaylistDetailCommand(CommonService commonService) {
        this.commonService = commonService;
    }

    @Override
    public CommandResult execute(RequestContent content) {
        return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.PLAYLIST_PAGE);
    }
}
