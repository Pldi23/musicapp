package by.platonov.music.repository;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.File;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-02.
 * @version 0.0.1
 */
@AllArgsConstructor
@Getter
public class FilePartRepositoryConfiguration {

    private static final String APP_PROPERTIES_PATH = "app";

    private static FilePartRepositoryConfiguration instance;
    private String path;

    private static ReentrantLock lock = new ReentrantLock();
    private static AtomicBoolean create = new AtomicBoolean(false);

    private FilePartRepositoryConfiguration() {
    }

    public static FilePartRepositoryConfiguration getInstance() {
        if (!create.get()) {
            lock.lock();
            try {
                if (instance == null) {
                    instance = init();
                    create.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    private static FilePartRepositoryConfiguration init() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(APP_PROPERTIES_PATH);
        String path = resourceBundle.getString("app.music.uploads") + File.separator;
        return new FilePartRepositoryConfiguration(path);
    }


}
