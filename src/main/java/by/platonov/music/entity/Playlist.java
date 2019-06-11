package by.platonov.music.entity;

import lombok.Data;

import java.util.Set;

/**
 * @author dzmitryplatonov on 2019-06-04.
 * @version 0.0.1
 */
@Data
public class Playlist extends Entity {

    private long id;
    private String name;
    private Set<Track> tracks;

}
