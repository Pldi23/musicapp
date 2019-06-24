package by.platonov.music.entity;

import lombok.Builder;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.Set;

/**
 * @author dzmitryplatonov on 2019-06-04.
 * @version 0.0.1
 */
@Data
public class Track extends Media {

    private String name;
    private Set<Musician> authors;
    private Set<Musician> singers;
    private Genre genre;
    private LocalDate releaseDate;
    private long length;

    @Builder
    public Track(long id, String name, Set<Musician> authors, Set<Musician> singers, Genre genre, LocalDate releaseDate, long length) {
        super(id);
        this.name = name;
        this.authors = authors;
        this.singers = singers;
        this.genre = genre;
        this.releaseDate = releaseDate;
        this.length = length;
    }

}
