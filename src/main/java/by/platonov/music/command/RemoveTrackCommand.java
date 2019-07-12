package by.platonov.music.command;

import by.platonov.music.command.constant.CommandMessage;
import by.platonov.music.command.constant.PageConstant;
import by.platonov.music.entity.Track;
import by.platonov.music.exception.ServiceException;
import by.platonov.music.service.AdminService;
import by.platonov.music.service.CommonService;
import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.ResourceBundle;

import static by.platonov.music.command.constant.RequestConstant.*;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-03.
 * @version 0.0.1
 */
@Log4j2
public class RemoveTrackCommand implements Command {

    private AdminService adminService;
    private CommonService commonService;

    public RemoveTrackCommand(AdminService adminService, CommonService commonService) {
        this.adminService = adminService;
        this.commonService = commonService;
    }

    @Override
    public CommandResult execute(RequestContent content) {
        CommandResult commandResult;
        try {
            String id = content.getRequestParameter(ID)[0];
            Track track = commonService.searchTrackById(id);

            if (adminService.removeTrack(track)) {
                deleteFile(track.getUuid());
                log.debug(track + " removed successfully");
                commandResult = new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.TRACK_LIBRARY_PAGE,
                        Map.of(REMOVE_RESULT, CommandMessage.SUCCESSFULLY_MESSAGE));
            } else {
                log.debug("could not remove track");
                commandResult = new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.TRACK_LIBRARY_PAGE,
                        Map.of(REMOVE_RESULT, CommandMessage.FAILED_MESSAGE));
            }
        } catch (ServiceException | IOException e) {
            log.error(e);
            commandResult = new CommandResult(CommandResult.ResponseType.REDIRECT, PageConstant.ERROR_REDIRECT_PAGE);
        }
        return commandResult;
    }

    private void deleteFile(String uuid) throws IOException {
        Files.delete(Path.of(ResourceBundle.getBundle("app").getString("app.music.uploads") + File.separator + uuid));
    }
}
