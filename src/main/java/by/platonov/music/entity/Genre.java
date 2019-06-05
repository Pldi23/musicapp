package by.platonov.music.entity;

import lombok.Builder;
import lombok.Value;

/**
 * @author dzmitryplatonov on 2019-06-04.
 * @version 0.0.1
 */
@Value(staticConstructor = "of")
@Builder
public class Genre {

    private long id;
    private String title;
}
