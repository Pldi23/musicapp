package by.platonov.music.repository.specification;

/**
 * @author dzmitryplatonov on 2019-06-13.
 * @version 0.0.1
 */
public class TracksInPlaylistSpecification implements SqlSpecification{

    private long playlistId;

    public TracksInPlaylistSpecification(long playlistId) {
        this.playlistId = playlistId;
    }

    @Override
    public String toSqlClauses() {
        return String.format("join playlist_track on track.id = playlist_track.track_id where playlist_track.playlist_id = %d", playlistId);
    }
}
