package by.platonov.music.entity;

import by.platonov.music.exception.FilePartBeanException;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.extern.log4j.Log4j2;

import javax.servlet.http.Part;
import java.io.*;
import java.nio.file.Path;
import java.util.ResourceBundle;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-01.
 * @version 0.0.1
 */
@Log4j2
@EqualsAndHashCode
public class FilePartBean {

    private InputStream inputStream;
    private String filePartName;

    public FilePartBean(Part part) throws FilePartBeanException {
        try {
            this.inputStream = part.getInputStream();
            this.filePartName = part.getSubmittedFileName();
        } catch (IOException e) {
            log.error("File Part could not be initialized", e);
            throw new FilePartBeanException(e);
        }
    }

    public FilePartBean(File file) throws FilePartBeanException {
        try  {
            this.inputStream = new FileInputStream(file);
            this.filePartName = file.getName();
        } catch (IOException e) {
            log.error("File Part could not be initialized", e);
            throw new FilePartBeanException(e);
        }
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public String getFilePartName() {
        return filePartName;
    }

    public void setFilePartName(String filePartName) {
        this.filePartName = filePartName;
    }
}
