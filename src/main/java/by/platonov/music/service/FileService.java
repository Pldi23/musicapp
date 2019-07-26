package by.platonov.music.service;

import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-24.
 * @version 0.0.1
 */
public class FileService {

    public File createFile(Part part, String uuid) throws IOException {
        String filename = Path.of(part.getSubmittedFileName()).getFileName().toString();
        String extension = filename.substring(filename.lastIndexOf('.'));
        File file = new File(ResourceBundle.getBundle("app").getString("app.music.uploads"), uuid + extension);
        Files.copy(part.getInputStream(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        return file;
    }

    public void deleteFile(String uuid) throws IOException {
        Files.delete(Path.of(ResourceBundle.getBundle("app").getString("app.music.uploads") + File.separator + uuid));
    }
}
