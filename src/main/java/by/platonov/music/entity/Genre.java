package by.platonov.music.entity;

import lombok.Builder;
import lombok.Data;

/**
 * @author dzmitryplatonov on 2019-06-04.
 * @version 0.0.1
 */
@Data
@Builder
public class Genre extends Entity {

    private long id;
    private String title;
}
