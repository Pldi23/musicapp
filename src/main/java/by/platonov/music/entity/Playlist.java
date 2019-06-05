package by.platonov.music.entity;

import lombok.Builder;
import lombok.Value;

import java.util.List;

/**
 * @author dzmitryplatonov on 2019-06-04.
 * @version 0.0.1
 */
@Value(staticConstructor = "of")
@Builder
public class Playlist {

    private long id;
    private String name;
    private List<Track> tracks;

}
