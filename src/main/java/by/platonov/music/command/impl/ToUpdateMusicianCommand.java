package by.platonov.music.command.impl;

import by.platonov.music.command.Command;
import by.platonov.music.command.handler.CommandHandler;
import by.platonov.music.command.CommandResult;
import by.platonov.music.command.RequestContent;
import by.platonov.music.constant.PageConstant;
import by.platonov.music.entity.Musician;
import by.platonov.music.service.CommonService;

/**
 * forwards to {@link PageConstant}.UPDATE_MUSICIAN_PAGE using {@link CommandHandler}
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
