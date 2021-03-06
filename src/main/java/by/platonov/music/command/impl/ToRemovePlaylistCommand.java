package by.platonov.music.command.impl;

import by.platonov.music.command.Command;
import by.platonov.music.command.handler.CommandHandler;
import by.platonov.music.command.CommandResult;
import by.platonov.music.command.RequestContent;
import by.platonov.music.constant.PageConstant;
import by.platonov.music.entity.Playlist;
import by.platonov.music.service.CommonService;

/**
 * forwards to {@link PageConstant}.REMOVE_CONFIRMATION_PAGE
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

    /**
     *
     * @param content DTO containing all data received with {@link javax.servlet.http.HttpServletRequest}
     * @return instance of {@link CommandResult} that use {@link CommandHandler} to forward
     * to {@link PageConstant}.REMOVE_CONFIRMATION_PAGE with playlist in attributes
     */
    @Override
    public CommandResult execute(RequestContent content) {
        return handler.transfer(content, PageConstant.REMOVE_CONFIRMATION_PAGE, commonService::searchPlaylistById);
    }
}
