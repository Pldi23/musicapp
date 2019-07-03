package by.platonov.music.command;

import by.platonov.music.command.constant.PageConstant;
import by.platonov.music.command.constant.RequestConstant;
import by.platonov.music.entity.Track;
import by.platonov.music.exception.ServiceException;
import by.platonov.music.service.TrackService;
import by.platonov.music.validator.IdValidator;
import by.platonov.music.validator.Violation;

import java.util.Set;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-03.
 * @version 0.0.1
 */
public class RemoveTrackCommand implements Command {

    TrackService trackService;

    public RemoveTrackCommand(TrackService trackService) {
        this.trackService = trackService;
    }

    @Override
    public CommandResult execute(RequestContent content) {

        Set<Violation> violations = new IdValidator(null).apply(content);

        if (violations.isEmpty()) {
            try {
                long id = Long.parseLong(content.getRequestParameter(RequestConstant.ID)[0]);
                Track track = trackService.searchid(id).get(0);
            } catch (ServiceException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
