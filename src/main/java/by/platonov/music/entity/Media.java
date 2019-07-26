package by.platonov.music.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author dzmitryplatonov on 2019-06-05.
 * @version 0.0.1
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class Media extends Entity {

    private long id;
    private String uuid;

}
