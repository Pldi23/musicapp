package by.platonov.music.repository.specification;

/**
 * @author dzmitryplatonov on 2019-06-14.
 * @version 0.0.1
 */
public class PlaylistUserSpecification implements SqlSpecification {

    private String login;

    public PlaylistUserSpecification(String login) {
        this.login = login;
    }

    @Override
    public String toSqlClauses() {
        return String.format("join user_playlist on user_playlist.playlist_id = playlist.id where user_playlist.user_login = '%s';", login);
    }
}
