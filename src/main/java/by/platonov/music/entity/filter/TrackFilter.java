package by.platonov.music.entity.filter;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-26.
 * @version 0.0.1
 */
@Data
@Builder
public class TrackFilter extends EntityFilter {

    String trackName;
    String genreName;
    LocalDate fromDate;
    LocalDate toDate;
    String singerName;
}
