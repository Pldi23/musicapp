package by.platonov.music.entity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Representation of playlist
 *
 * @author dzmitryplatonov on 2019-06-04.
 * @version 0.0.1
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class Playlist extends Entity {

    private long id;
    private String name;
    private boolean personal;
    private List<Track> tracks;

    /**
     * @return the most popular genre of songs of this playlist
     */
    public String getMostPopularGenreName() {
       return tracks.stream()
                .map(Track::getGenre)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .max(Comparator.comparing(Map.Entry::getValue))
                .map(genreLongEntry -> genreLongEntry.getKey().getTitle())
                .orElse("");
    }

    /**
     * @return the total duration of playlist
     * to be formatted by {@link by.platonov.music.tag.PlaylistDurationTag}
     */
    public long getDuration() {
        return tracks.stream().mapToLong(Track::getLength).sum();
    }

    /**
     * @return the number of tracks in playlist
     */
    public int getSize() {
        return tracks.size();
    }

}
