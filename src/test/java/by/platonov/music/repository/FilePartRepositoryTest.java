package by.platonov.music.repository;

import by.platonov.music.entity.FilePartBean;
import by.platonov.music.exception.FilePartBeanException;
import by.platonov.music.exception.RepositoryException;
import org.junit.jupiter.api.*;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-01.
 * @version 0.0.1
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FilePartRepositoryTest {

    private Repository<FilePartBean> repository;

    @BeforeEach
    void setUp() {
        repository = FilePartRepository.getInstance();
    }

    @Test
    @Order(1)
    void add() throws RepositoryException, FilePartBeanException {
        File file = new File("/users/dzmitryplatonov/Downloads/katy-perry-feat.-nicki-minaj-swish-swish.mp3");
        System.out.println(file.toPath());
        FilePartBean filePartBean = new FilePartBean(file);
        assertTrue(repository.add(filePartBean));
    }

    @Test
    @Order(2)
    void addNegative() throws RepositoryException, FilePartBeanException {
        File file = new File("/users/dzmitryplatonov/Downloads/test.mp3");
        FilePartBean filePartBean = new FilePartBean(file);
        repository.add(filePartBean);
        assertFalse(repository.add(filePartBean));
    }

    @Test
    @Order(3)
    void remove() throws RepositoryException, FilePartBeanException {
        File file = new File("/users/dzmitryplatonov/Dropbox/music/test.mp3");
        FilePartBean filePartBean = new FilePartBean(file);
        assertTrue(repository.remove(filePartBean));
    }

    @Test
    @Order(4)
    void update() throws RepositoryException, FilePartBeanException {
        File file = new File("/users/dzmitryplatonov/Dropbox/music/katy-perry-feat.-nicki-minaj-swish-swish.mp3");
        FilePartBean filePartBean = new FilePartBean(file);
        assertTrue(repository.update(filePartBean));
    }

    @AfterAll
    static void done() throws RepositoryException, FilePartBeanException {
        FilePartRepository.getInstance().remove(new FilePartBean(
                new File("/users/dzmitryplatonov/Dropbox/music/katy-perry-feat.-nicki-minaj-swish-swish.mp3")));
    }
}