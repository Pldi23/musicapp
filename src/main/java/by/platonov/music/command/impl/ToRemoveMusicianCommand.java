package by.platonov.music.command.impl;

import by.platonov.music.command.Command;
import by.platonov.music.command.handler.CommandHandler;
import by.platonov.music.command.CommandResult;
import by.platonov.music.command.RequestContent;
import by.platonov.music.constant.PageConstant;
import by.platonov.music.entity.Musician;
import by.platonov.music.service.CommonService;
import lombok.extern.log4j.Log4j2;

/**
 * forwards to {@link PageConstant}.REMOVE_CONFIRMATION_PAGE
 *
 * @author Dzmitry Platonov on 2019-07-07.
 * @version 0.0.1
 */
@Log4j2
public class ToRemoveMusicianCommand implements Command {

    private CommonService commonService;
    private CommandHandler<Musician> handler;

    public ToRemoveMusicianCommand(CommonService commonService, CommandHandler<Musician> handler) {
        this.commonService = commonService;
        this.handler = handler;
    }

    /**
     *
     * @param content DTO containing all data received with {@link javax.servlet.http.HttpServletRequest}
     * @return instance of {@link CommandResult} that use {@link CommandHandler} to forward
     * to {@link PageConstant}.REMOVE_CONFIRMATION_PAGE with musician in attributes
     */
    @Override
    public CommandResult execute(RequestContent content) {
        return handler.transfer(content, PageConstant.REMOVE_CONFIRMATION_PAGE, commonService::searchMusicianById);
    }
}
