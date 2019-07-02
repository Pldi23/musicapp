package by.platonov.music.service;

import by.platonov.music.exception.FilePartBeanException;
import by.platonov.music.exception.RepositoryException;
import by.platonov.music.exception.ServiceException;
import by.platonov.music.entity.FilePartBean;
import by.platonov.music.repository.FilePartRepository;
import by.platonov.music.repository.FilePartRepositoryConfiguration;
import by.platonov.music.repository.Repository;
import lombok.extern.log4j.Log4j2;

import javax.servlet.http.Part;
import java.nio.file.Path;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-01.
 * @version 0.0.1
 */
@Log4j2
public class FilePartService {

    private static FilePartService instance;
    private Repository<FilePartBean> repository;
    private FilePartRepositoryConfiguration configuration;

    private FilePartService(FilePartRepository repository, FilePartRepositoryConfiguration configuration) {
        this.repository = repository;
        this.configuration = configuration;
    }

    public static FilePartService getInstance() {
        if (instance == null) {
            instance = new FilePartService(FilePartRepository.getInstance(), FilePartRepositoryConfiguration.getInstance());
        }
        return instance;
    }

    public boolean addFilePart(FilePartBean filePartBean) throws ServiceException {
        try {
//            FilePartBean filePartBean = new FilePartBean(part);
            return repository.add(filePartBean);
        } catch (RepositoryException e) {
            log.error("could not add part");
            throw new ServiceException(e);
        }
    }

    public boolean removeFilePart(Part part) throws ServiceException {
        try {
            FilePartBean filePartBean = new FilePartBean(part);
            return repository.remove(filePartBean);
        } catch (FilePartBeanException | RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    public Path getFilePartBeanRepositoryPath(FilePartBean filePartBean) {
        return Path.of(configuration.getPath() + filePartBean.getFilePartName());
    }
}
