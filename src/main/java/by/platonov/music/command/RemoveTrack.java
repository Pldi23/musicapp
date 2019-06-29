package by.platonov.music.command;

import by.platonov.music.command.constant.RequestConstant;
import by.platonov.music.service.TrackService;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-06-28.
 * @version 0.0.1
 */
public class RemoveTrack implements Command {
    private TrackService trackService;

    public RemoveTrack(TrackService trackService) {
        this.trackService = trackService;
    }

    @Override
    public CommandResult execute(RequestContent content) {
        long id = Long.parseLong(content.getRequestParameter(RequestConstant.ID)[0]);
        return null;
    }
}
