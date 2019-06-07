package by.platonov.music.entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * @author dzmitryplatonov on 2019-06-04.
 * @version 0.0.1
 */
@Data
@Builder
public class Track extends Media {

    private String name;
    private List<Musician> authors;
    private List<Musician> singers;
    private Genre genre;
    private LocalDate releaseDate;
    private long length;

}
