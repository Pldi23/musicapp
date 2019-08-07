package by.platonov.music.service;

import by.platonov.music.exception.ServiceException;
import lombok.NonNull;

import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;

/**
 * encapsulates service methods for adding and deleting files
 *
 * @author Dzmitry Platonov on 2019-07-24.
 * @version 0.0.1
 */
public class FileService {

    private static final String FOLDER = "app.music.uploads";
    private static final String APPLICATION_PROPERTIES_PATH = "app";

    /**
     * to create file in the application folder
     * @param part file part from request
     * @param uuid unique file uuid
     * @return created file
     * @throws ServiceException if IOException was thrown
     */
    public File createFile(@NonNull Part part,@NonNull String uuid) throws ServiceException {
        String filename = Path.of(part.getSubmittedFileName()).getFileName().toString();
        String extension = filename.substring(filename.lastIndexOf('.'));
        File file = new File(ResourceBundle.getBundle(APPLICATION_PROPERTIES_PATH).getString(FOLDER), uuid + extension);
        try {
            Files.copy(part.getInputStream(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new ServiceException(e);
        }
        return file;
    }

    /**
     * to delete file from app folder
     * @param uuid of the file
     * @throws ServiceException if IOException was thrown
     */
    public void deleteFile(@NonNull String uuid) throws ServiceException {
        try {
            Files.delete(Path.of(ResourceBundle.getBundle(APPLICATION_PROPERTIES_PATH).getString(FOLDER) + File.separator + uuid));
        } catch (IOException e) {
            throw new ServiceException(e);
        }
    }
}
