package by.platonov.music.command.impl;

import by.platonov.music.command.Command;
import by.platonov.music.command.CommandResult;
import by.platonov.music.command.RequestContent;
import by.platonov.music.constant.PageConstant;
import by.platonov.music.constant.RequestConstant;
import by.platonov.music.entity.Track;
import by.platonov.music.exception.ServiceException;
import by.platonov.music.service.CommonService;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * to forward to {@link PageConstant}.TRACKS_HISTORY_PAGE
 * {@link javax.servlet.http.Cookie} used to analyze last 10 listened {@link Track}s
 *
 * @author Dzmitry Platonov on 2019-08-04.
 * @version 0.0.1
 */
@Log4j2
public class ToTracksHistoryCommand implements Command {

    private CommonService commonService;

    public ToTracksHistoryCommand(CommonService commonService) {
        this.commonService = commonService;
    }

    /**
     *
     * @param content DTO containing all data received with {@link javax.servlet.http.HttpServletRequest}
     * @return instance of {@link CommandResult} that forward to {@link PageConstant}.TRACKS_HISTORY_PAGE with
     * 10 {@link Track}s from {@link javax.servlet.http.Cookie} located in attributes,
     * executes {@link ErrorCommand} if {@link ServiceException} was caught
     */
    @Override
    public CommandResult execute(RequestContent content) {
        List<String> ids = content.getCookies().entrySet().stream()
                .filter(c -> c.getKey().contains(RequestConstant.LAST_PLAYED))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
        List<Track> tracks = new ArrayList<>();
        for (String string : ids) {
            try {
                tracks.addAll(commonService.searchTrackById(string));
            } catch (ServiceException e) {
                log.error("couldn't provide tracks", e);
                return new ErrorCommand(e).execute(content);
            }
        }
        return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.TRACKS_HISTORY_PAGE,
                Map.of(RequestConstant.TRACKS, tracks));
    }
}
