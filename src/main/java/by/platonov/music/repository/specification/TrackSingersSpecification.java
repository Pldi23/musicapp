package by.platonov.music.repository.specification;

/**
 * @author dzmitryplatonov on 2019-06-14.
 * @version 0.0.1
 */
public class TrackSingersSpecification implements SqlSpecification {

    private long trackId;

    public TrackSingersSpecification(long trackId) {
        this.trackId = trackId;
    }

    @Override
    public String toSqlClauses() {
        return String.format("join singer_track on singer_track.track_id = musician.id where singer_track.track_id = %d", trackId);
    }
}
