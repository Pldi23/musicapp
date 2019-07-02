package by.platonov.music.repository;

import by.platonov.music.entity.FilePartBean;
import by.platonov.music.exception.RepositoryException;
import by.platonov.music.repository.specification.SqlSpecification;
import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-01.
 * @version 0.0.1
 */
@Log4j2
public class FilePartRepository implements Repository<FilePartBean> {

    private static FilePartRepository instance;
    private static ReentrantLock lock = new ReentrantLock();
    private static AtomicBoolean create = new AtomicBoolean(false);
    private FilePartRepositoryConfiguration configuration;

    private FilePartRepository(FilePartRepositoryConfiguration configuration) {
        this.configuration = configuration;
    }

    public static FilePartRepository getInstance() {
        if (!create.get()) {
            lock.lock();
            try {
                if (instance == null) {
                    instance = new FilePartRepository(FilePartRepositoryConfiguration.getInstance());
                    create.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    @Override
    public boolean add(FilePartBean filePartBean){
        Path path = getFilePartRepositoryPath(filePartBean);
//        Path path = filePartBean.getFilePartPath();
        try(InputStream inputStream = filePartBean.getInputStream()) {
            Files.copy(inputStream, path);
            log.debug(path + " successfully added");
            return true;
        } catch (IOException e) {
            log.warn(path + " file already exist", e);
            return false;
        }
    }

    @Override
    public boolean remove(FilePartBean filePartBean) throws RepositoryException {
        Path path = getFilePartRepositoryPath(filePartBean);
//        Path path = filePartBean.getFilePartPath();
        try {
            log.debug("trying to delete " + path);
            return Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public boolean update(FilePartBean filePartBean) throws RepositoryException {
        Path path = getFilePartRepositoryPath(filePartBean);
        if (Files.exists(path)) {
            try (InputStream inputStream = filePartBean.getInputStream()) {
                Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
//                Files.copy(inputStream, path);
                log.debug(path + " successfully updated");
                return true;
            } catch (IOException e) {
                throw new RepositoryException(e);
            }
        } else {
            log.debug("Could not update " + path + " file not exist");
            return false;
        }
    }

    private Path getFilePartRepositoryPath(FilePartBean filePartBean) {
        return Path.of(configuration.getPath() + filePartBean.getFilePartName());
    }

    @Override
    public List<FilePartBean> query(SqlSpecification specification) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long count(SqlSpecification specification) {
        throw new UnsupportedOperationException();
    }

}
