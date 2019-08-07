package by.platonov.music.command.impl;

import by.platonov.music.command.Command;
import by.platonov.music.command.CommandResult;
import by.platonov.music.command.RequestContent;
import by.platonov.music.message.MessageManager;
import by.platonov.music.constant.PageConstant;
import by.platonov.music.entity.User;
import by.platonov.music.exception.ServiceException;
import by.platonov.music.service.UserService;
import by.platonov.music.service.FileService;
import by.platonov.music.util.HashGenerator;
import by.platonov.music.validator.PhotoPartValidator;
import by.platonov.music.validator.Violation;
import lombok.extern.log4j.Log4j2;

import javax.servlet.http.Part;
import java.io.File;
import java.util.Map;
import java.util.Set;

import static by.platonov.music.constant.RequestConstant.*;
/**
 * to upload image to application file storage
 *
 * @author Dzmitry Platonov on 2019-07-15.
 * @version 0.0.1
 */
@Log4j2
public class UploadImageCommand implements Command {

    private UserService userService;
    private FileService fileService;

    public UploadImageCommand(UserService userService, FileService fileService) {
        this.userService = userService;
        this.fileService = fileService;
    }

    /**
     *
     * @param content DTO containing all data received with {@link javax.servlet.http.HttpServletRequest}
     * @return instance of {@link CommandResult} that:
     * forward to {@link PageConstant}.PROFILE_PAGE with violations if it was found
     * forward to {@link PageConstant}.PROFILE_PAGE with result message if execution is complete
     * executes {@link ErrorCommand} if {@link ServiceException} was caught
     */
    @Override
    public CommandResult execute(RequestContent content) {

        Set<Violation> violations = new PhotoPartValidator(null).apply(content);
        String result;

        if (violations.isEmpty()) {
            try {
                Part filePart = content.getPart(IMG_PATH).get();
                User user = (User) content.getSessionAttribute(USER);
                HashGenerator generator = new HashGenerator();
                File file = fileService.createFile(filePart, generator.generateHash());
                user.setPhotoPath(file.getName());
                String locale = (String) content.getSessionAttribute(LOCALE);
                result = userService.updateUser(user) ? MessageManager.getMessage("label.updated", locale) :
                      MessageManager.getMessage("failed", locale);
                return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.PROFILE_PAGE,
                        Map.of(PROCESS, result), Map.of(USER, user));
            } catch (ServiceException e) {
                log.error("couldn't upload file", e);
                return new ErrorCommand(e).execute(content);
            }

        } else {
            return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.PROFILE_PAGE,
                    Map.of(VALIDATOR_RESULT, violations));
        }
    }
}
