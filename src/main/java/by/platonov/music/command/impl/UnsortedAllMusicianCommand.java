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
 * to show all {@link Musician} from application database
 *
 * @author Dzmitry Platonov on 2019-07-07.
 * @version 0.0.1
 */
@Log4j2
public class UnsortedAllMusicianCommand implements Command {

    private CommonService commonService;
    private CommandHandler<Musician> handler;

    public UnsortedAllMusicianCommand(CommonService commonService, CommandHandler<Musician> handler) {
        this.commonService = commonService;
        this.handler = handler;
    }

    /**
     *
     * @param content DTO containing all data received with {@link javax.servlet.http.HttpServletRequest}
     * @return instance of {@link CommandResult} that forward to {@link PageConstant}.MUSICIAN_LIBRARY_PAGE
     * with list of musicians
     */
    @Override
    public CommandResult execute(RequestContent content) {
        return handler.sorted(content, RequestConstant.NO_ORDER, PageConstant.MUSICIAN_LIBRARY_PAGE,
                commonService::countMusicians, (b, l, o) -> commonService.searchMusicians(l, o));
    }
}
