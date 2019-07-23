package by.platonov.music.command;

import by.platonov.music.message.MessageManager;
import by.platonov.music.command.constant.PageConstant;
import by.platonov.music.command.constant.RequestConstant;
import by.platonov.music.entity.Musician;
import by.platonov.music.entity.Track;
import by.platonov.music.exception.ServiceException;
import by.platonov.music.service.CommonService;
import lombok.extern.log4j.Log4j2;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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
        int size;
        String genre;
        try {
            List<Musician> musicians = commonService.searchMusicianById(id);
            if (musicians.isEmpty()) {
                return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.ENTITY_REMOVED_PAGE,
                        Map.of(RequestConstant.PROCESS,
                                MessageManager.getMessage("message.entity.not.available",
                                        (String) content.getSessionAttribute(RequestConstant.LOCALE))));
            }
            musician = musicians.get(0);
            tracks = commonService.searchTracksByMusician(Long.parseLong(id));
            size = tracks.size();
            genre = tracks.stream()
                    .map(Track::getGenre)
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                    .entrySet()
                    .stream()
                    .max(Comparator.comparing(Map.Entry::getValue))
                    .map(genreLongEntry -> genreLongEntry.getKey().getTitle())
                    .orElse("");

        } catch (ServiceException e) {
            log.error("command couldn't provide tracks", e);
            return new ErrorCommand(e).execute(content);
        }
        return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.MUSICIAN_PAGE,
                Map.of(RequestConstant.ENTITIES, tracks, RequestConstant.MUSICIAN, musician,
                        RequestConstant.SIZE, size, RequestConstant.GENRE, genre));
    }
}
