package by.platonov.music.command;

import by.platonov.music.command.constant.PageConstant;
import by.platonov.music.entity.Playlist;
import by.platonov.music.service.CommonService;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-07.
 * @version 0.0.1
 */
public class ToRemovePlaylistCommand implements Command {

    private CommonService commonService;
    private CommandHandler<Playlist> handler;

    public ToRemovePlaylistCommand(CommonService commonService, CommandHandler<Playlist> handler) {
        this.commonService = commonService;
        this.handler = handler;
    }

    @Override
    public CommandResult execute(RequestContent content) {
        return handler.transfer(content, PageConstant.ARE_YOU_SURE_PAGE, commonService::searchPlaylistById);
    }
}
