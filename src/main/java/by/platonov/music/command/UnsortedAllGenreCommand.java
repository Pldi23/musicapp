package by.platonov.music.command;

import by.platonov.music.command.constant.PageConstant;
import by.platonov.music.command.constant.RequestConstant;
import by.platonov.music.entity.Genre;
import by.platonov.music.service.CommonService;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-08.
 * @version 0.0.1
 */
public class UnsortedAllGenreCommand implements Command {

    private CommonService commonService;
    private CommandHandler<Genre> handler;

    public UnsortedAllGenreCommand(CommonService commonService, CommandHandler<Genre> handler) {
        this.commonService = commonService;
        this.handler = handler;
    }

    @Override
    public CommandResult execute(RequestContent content) {
        return handler.sorted(content, RequestConstant.NO_ORDER, PageConstant.GENRE_LIBRARY_PAGE,
                commonService::countGenres, (b, l, o) -> commonService.searchGenres(l, o));
    }
}
