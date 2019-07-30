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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-04.
 * @version 0.0.1
 */
@Log4j2
public class ToLibraryCommand implements Command {

    private CommonService commonService;

    public ToLibraryCommand(CommonService commonService) {
        this.commonService = commonService;
    }

    @Override
    public CommandResult execute(RequestContent content) {
        List<Track> lastAddedTracks;
        try {
            lastAddedTracks = commonService.getTracksLastAdded();
            Map<String, Object> attributes = new HashMap<>();
            attributes.put(RequestConstant.TRACKS, lastAddedTracks);
            return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.LIBRARY_PAGE, attributes);
        } catch (ServiceException e) {
            log.error("command could't provide track list", e);
            return new ErrorCommand(e).execute(content);
        }
    }
}
