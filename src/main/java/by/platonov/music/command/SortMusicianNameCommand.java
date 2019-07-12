package by.platonov.music.command;

import by.platonov.music.command.constant.PageConstant;
import by.platonov.music.command.constant.RequestConstant;
import by.platonov.music.entity.Musician;
import by.platonov.music.exception.ServiceException;
import by.platonov.music.service.CommonService;
import lombok.extern.log4j.Log4j2;

/**
 * music-app
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

    @Override
    public CommandResult execute(RequestContent content) {
        return handler.sorted(content, RequestConstant.SORT_MUSICIAN_NAME_ORDER,
                PageConstant.SORT_NAME_MUSICIAN_LIBRARY_PAGE, commonService::countMusicians,
                commonService::sortedMusiciansName);
    }
}
