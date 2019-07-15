package by.platonov.music.command;

import by.platonov.music.MessageManager;
import by.platonov.music.command.constant.PageConstant;
import by.platonov.music.entity.User;
import by.platonov.music.exception.ServiceException;
import by.platonov.music.service.UserService;
import by.platonov.music.util.UnicNameGenerator;
import by.platonov.music.validator.PhotoPartValidator;
import by.platonov.music.validator.Violation;

import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import static by.platonov.music.command.constant.RequestConstant.*;
/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-15.
 * @version 0.0.1
 */
public class UploadImageCommand implements Command {

    private UserService userService;

    public UploadImageCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public CommandResult execute(RequestContent content) {

//        Set<Violation> violations = new PhotoPartValidator(null).apply(content);
//        String result;
//
//        if (violations.isEmpty()) {
//            try {
//                Part filePart = content.getPart(IMG_PATH).get();
//                User user = (User) content.getSessionAttribute(USER);
//                File file = createFile(filePart, UnicNameGenerator.generateUnicName());
//                user.setPhotoPath(file.getName());
//                String locale = content.getSessionAttribute()
//                result = userService.updateUser(user) ? MessageManager.getMessage("")
//            } catch (IOException | ServiceException e) {
//                e.printStackTrace();
//            }
//
//        } else {
//            result = "\u2718";
//            for (Violation violation : violations) {
//                result = result.concat(violation.getMessage());
//            }
//            return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.PROFILE_PAGE,
//                    Map.of(RequestConstant.PROCESS, result));
//        }
        return null;
    }

    private File createFile(Part part, String uuid) throws IOException {
        String filename = Path.of(part.getSubmittedFileName()).getFileName().toString();
        String extension = filename.substring(filename.lastIndexOf('.'));
        File file = new File(ResourceBundle.getBundle("app").getString("app.music.uploads"), uuid + extension);
        Files.copy(part.getInputStream(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        return file;
    }
}
