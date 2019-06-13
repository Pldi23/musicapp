package by.platonov.music.repository.specification;

/**
 * @author dzmitryplatonov on 2019-06-14.
 * @version 0.0.1
 */
public class TrackAuthorsSpecification implements SqlSpecification {

    private long trackId;

    public TrackAuthorsSpecification(long trackId) {
        this.trackId = trackId;
    }

    @Override
    public String toSqlClauses() {
        return String.format("join author_track on author_track.track_id = musician.id where author_track.track_id = %d", trackId);
    }
}
