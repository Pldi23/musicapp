package by.platonov.music.entity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.Set;

/**
 * Representation of Track
 *
 * @author dzmitryplatonov on 2019-06-04.
 * @version 0.0.1
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Track extends Media {

    private String name;
    private Set<Musician> authors;
    private Set<Musician> singers;
    private Genre genre;
    private LocalDate releaseDate;
    private LocalDate createDate;
    private long length;

    @Builder
    public Track(long id, String uuid, String name, Set<Musician> authors, Set<Musician> singers, Genre genre,
                 LocalDate releaseDate, LocalDate createDate, long length) {
        super(id, uuid);
        this.name = name;
        this.authors = authors;
        this.singers = singers;
        this.genre = genre;
        this.releaseDate = releaseDate;
        this.createDate = createDate;
        this.length = length;
    }
}
