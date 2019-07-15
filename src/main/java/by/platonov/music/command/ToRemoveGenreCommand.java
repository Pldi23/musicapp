package by.platonov.music.command;

import by.platonov.music.command.constant.PageConstant;
import by.platonov.music.entity.Genre;
import by.platonov.music.service.CommonService;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-07.
 * @version 0.0.1
 */
public class ToRemoveGenreCommand implements Command {

    private CommonService commonService;
    private CommandHandler<Genre> handler;

    public ToRemoveGenreCommand(CommonService commonService, CommandHandler<Genre> handler) {
        this.commonService = commonService;
        this.handler = handler;
    }

    @Override
    public CommandResult execute(RequestContent content) {
        return handler.transfer(content, PageConstant.REMOVE_CONFIRMATION_PAGE, commonService::searchGenreById);
    }
}
