package by.platonov.music.command.impl;

import by.platonov.music.command.Command;
import by.platonov.music.command.handler.CommandHandler;
import by.platonov.music.command.CommandResult;
import by.platonov.music.command.RequestContent;
import by.platonov.music.constant.PageConstant;
import by.platonov.music.constant.RequestConstant;
import by.platonov.music.entity.Musician;
import by.platonov.music.service.CommonService;
import lombok.extern.log4j.Log4j2;

/**
 * to sort musicians by name
 *
 * @author Dzmitry Platonov on 2019-07-07.
 * @version 0.0.1
 */
@Log4j2
public class SortMusicianNameCommand implements Command {

    private CommonService commonService;
    private CommandHandler<Musician> handler;

    public SortMusicianNameCommand(CommonService commonService, CommandHandler<Musician> handler) {
        this.commonService = commonService;
        this.handler = handler;
    }

    /**
     *
     * @param content DTO containing all data received with {@link javax.servlet.http.HttpServletRequest}
     * @return instance of {@link CommandResult} that forward to {@link PageConstant}.SORT_NAME_MUSICIAN_LIBRARY_PAGE
     * with sorted list of musicians
     */
    @Override
    public CommandResult execute(RequestContent content) {
        return handler.sorted(content, RequestConstant.SORT_MUSICIAN_NAME_ORDER,
                PageConstant.SORT_NAME_MUSICIAN_LIBRARY_PAGE, commonService::countMusicians,
                commonService::sortedMusiciansName);
    }
}
