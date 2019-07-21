package by.platonov.music.command;

import by.platonov.music.command.constant.PageConstant;
import by.platonov.music.entity.Musician;
import by.platonov.music.service.CommonService;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-07.
 * @version 0.0.1
 */
public class ToUpdateMusicianCommand implements Command {

    private CommonService commonService;
    private CommandHandler<Musician> handler;

    public ToUpdateMusicianCommand(CommonService commonService, CommandHandler<Musician> handler) {
        this.commonService = commonService;
        this.handler = handler;
    }

    @Override
    public CommandResult execute(RequestContent content) {
        return handler
                .transfer(content, PageConstant.UPDATE_MUSICIAN_PAGE, commonService::searchMusicianById);
    }
}
