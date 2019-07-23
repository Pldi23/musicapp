package by.platonov.music.entity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author dzmitryplatonov on 2019-06-05.
 * @version 0.0.1
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class Musician extends Entity {

    private long id;
    private String name;

}
