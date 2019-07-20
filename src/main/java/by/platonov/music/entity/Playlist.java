package by.platonov.music.entity;

import lombok.Builder;
import lombok.Data;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author dzmitryplatonov on 2019-06-04.
 * @version 0.0.1
 */
@Data
@Builder
public class Playlist extends Entity {

    private long id;
    private String name;
    private boolean personal;
    private Set<Track> tracks;

    public String getMostPopularGenre() {
       return tracks.stream()
                .map(Track::getGenre)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .max(Comparator.comparing(Map.Entry::getValue))
                .map(genreLongEntry -> genreLongEntry.getKey().getTitle())
                .orElse("");
    }

    public String getTotalDuration() {
        long duration = tracks.stream().mapToLong(Track::getLength).sum();
        long hours = TimeUnit.SECONDS.toHours(duration);
        duration -= TimeUnit.HOURS.toSeconds(hours);
        long minutes = TimeUnit.SECONDS.toMinutes(duration);
        duration -= TimeUnit.MINUTES.toSeconds(minutes);
        return String.format("%02d:%02d:%02d", hours, minutes, duration);
    }

    public int getSize() {
        return tracks.size();
    }

}
