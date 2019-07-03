package by.platonov.music.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.nio.file.Path;

/**
 * @author dzmitryplatonov on 2019-06-05.
 * @version 0.0.1
 */
@Data
@AllArgsConstructor
public class Media extends Entity {

    private long id;
    private String uuid;
//    private Path path;
//    private FilePartBean filePartBean;
}
