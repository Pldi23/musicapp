package by.platonov.music.command.impl;

import by.platonov.music.command.Command;
import by.platonov.music.command.handler.CommandHandler;
import by.platonov.music.command.CommandResult;
import by.platonov.music.command.RequestContent;
import by.platonov.music.constant.PageConstant;
import by.platonov.music.constant.RequestConstant;
import by.platonov.music.entity.Track;
import by.platonov.music.entity.filter.EntityFilter;
import by.platonov.music.entity.filter.TrackFilter;
import by.platonov.music.service.CommonService;
import by.platonov.music.validator.*;
import lombok.extern.log4j.Log4j2;


import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

import static by.platonov.music.constant.RequestConstant.*;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-12.
 * @version 0.0.1
 */
@Log4j2
public class FilterTrackCommand implements Command {

    private CommonService commonService;
    private CommandHandler<Track> handler;

    public FilterTrackCommand(CommonService commonService, CommandHandler<Track> handler) {
        this.commonService = commonService;
        this.handler = handler;
    }

    @Override
    public CommandResult execute(RequestContent content) {

        Set<Violation> violations =
                new TrackNameValidator(true,
                        new SingerValidator(true,
                                new FilterDateValidator(RELEASE_FROM, RELEASE_TO, null))).apply(content);

        if (violations.isEmpty()) {

            EntityFilter entityFilter = TrackFilter.builder()
                    .trackName(content.getRequestParameter(TRACKNAME)[0])
                    .genreName(!content.getRequestParameter(GENRE)[0].isBlank() ? content.getRequestParameter(GENRE)[0] : "")
                    .fromDate(!content.getRequestParameter(RELEASE_FROM)[0].isBlank() ?
                LocalDate.parse(content.getRequestParameter(RELEASE_FROM)[0]) : LocalDate.EPOCH)
                    .toDate(!content.getRequestParameter(RELEASE_TO)[0].isBlank() ?
                LocalDate.parse(content.getRequestParameter(RELEASE_TO)[0]) : LocalDate.now())
                    .singerName(content.getRequestParameter(SINGER)[0])
                    .build();


            return handler.filter(content, PageConstant.FILTER_PAGE,
                    (limit, offset) -> commonService.searchTrackByFilter(entityFilter, limit, offset),
                    Map.of(RequestConstant.FILTER, entityFilter));
        } else {
            log.info("filter failed because of validator violation");
            return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.FILTER_PAGE,
                    Map.of(VALIDATOR_RESULT, violations));
        }
    }
}
