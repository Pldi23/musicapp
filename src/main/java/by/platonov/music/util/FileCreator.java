package by.platonov.music.util;

import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-24.
 * @version 0.0.1
 */
public class FileCreator {

    private String appFolder;

    public FileCreator(String appFolder) {
        this.appFolder = appFolder;
    }

    public File createFile(Part part, String uuid) throws IOException {
        String filename = Path.of(part.getSubmittedFileName()).getFileName().toString();
        String extension = filename.substring(filename.lastIndexOf('.'));
        File file = new File(appFolder, uuid + extension);
        Files.copy(part.getInputStream(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        return file;
    }
}
