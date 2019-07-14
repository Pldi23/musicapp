package by.platonov.music.command;

import by.platonov.music.command.constant.PageConstant;
import by.platonov.music.command.constant.RequestConstant;
import by.platonov.music.entity.Musician;
import by.platonov.music.entity.Track;
import by.platonov.music.exception.ServiceException;
import by.platonov.music.service.CommonService;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.Map;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-13.
 * @version 0.0.1
 */
@Log4j2
public class MusicianDetailCommand implements Command {

    private CommonService commonService;

    public MusicianDetailCommand(CommonService commonService) {
        this.commonService = commonService;
    }

    @Override
    public CommandResult execute(RequestContent content) {
        String id = content.getRequestParameter(RequestConstant.ID)[0];
        Musician musician;
        List<Track> tracks;
        try {
            musician = commonService.searchMusicianById(id);
            tracks = commonService.searchTracksByMusician(Long.parseLong(id));
        } catch (ServiceException e) {
            log.error("command couldn't provide tracks", e);
            return new CommandResult(CommandResult.ResponseType.REDIRECT, PageConstant.ERROR_REDIRECT_PAGE);
        }
        return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.MUSICIAN_PAGE,
                Map.of(RequestConstant.ENTITIES, tracks, RequestConstant.MUSICIAN, musician));
    }
}
