package by.platonov.music.command;

import by.platonov.music.service.CommonService;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-14.
 * @version 0.0.1
 */
public class PlaylistCreateCommand implements Command {

    private CommonService commonService;

    public PlaylistCreateCommand(CommonService commonService) {
        this.commonService = commonService;
    }

    @Override
    public CommandResult execute(RequestContent content) {
        return null;
    }
}
