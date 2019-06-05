package by.platonov.music.entity;

import lombok.Builder;
import lombok.Value;

/**
 * @author dzmitryplatonov on 2019-06-05.
 * @version 0.0.1
 */
@Value(staticConstructor = "of")
@Builder
public class Musician {

    private long id;
    private String name;
    private boolean singer;
    private boolean author;

}
