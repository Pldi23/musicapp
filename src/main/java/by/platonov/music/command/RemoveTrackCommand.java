package by.platonov.music.command;

import by.platonov.music.command.constant.PageConstant;
import by.platonov.music.command.constant.RequestConstant;
import by.platonov.music.exception.ServiceException;
import by.platonov.music.service.TrackService;
import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-03.
 * @version 0.0.1
 */
@Log4j2
public class RemoveTrackCommand implements Command {

    private TrackService trackService;

    public RemoveTrackCommand(TrackService trackService) {
        this.trackService = trackService;
    }

    @Override
    public CommandResult execute(RequestContent content) {
        CommandResult commandResult;
        try {
            String uuid = content.getRequestParameter(RequestConstant.UUID)[0];

            if (trackService.remove(uuid)) {
                log.debug("track with uuid " + uuid + " removed successfully");
                deleteFile(uuid);
                commandResult = new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.ADMIN_PAGE,
                        Map.of("removeresult", "successfull"));
            } else {
                log.debug("could not remove track");
                commandResult = new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.ADMIN_PAGE,
                        Map.of("removeresult", "not successfull"));
            }
        } catch (ServiceException | IOException e) {
            log.error(e);
            commandResult = new CommandResult(CommandResult.ResponseType.REDIRECT, PageConstant.ERROR_REDIRECT_PAGE);
        }
        return commandResult;
    }

    private void deleteFile(String uuid) throws IOException {
        Files.delete(Path.of("/users/dzmitryplatonov/Dropbox/music" + File.separator + uuid));
    }
}
