package by.platonov.music.tag;

import by.platonov.music.entity.Playlist;
import by.platonov.music.entity.Track;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * tag to represent playlist duration in required string format
 *
 * @author Dzmitry Platonov on 2019-08-09.
 * @version 0.0.1
 */
public class PlaylistDurationTag extends TagSupport {

    /**
     * {@link Playlist}
     */
    private Playlist playlist;

    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
    }

    @Override
    public int doStartTag() throws JspException {
        long duration = playlist.getTracks().stream().mapToLong(Track::getLength).sum();
        long hours = TimeUnit.SECONDS.toHours(duration);
        duration -= TimeUnit.HOURS.toSeconds(hours);
        long minutes = TimeUnit.SECONDS.toMinutes(duration);
        duration -= TimeUnit.MINUTES.toSeconds(minutes);
        String result = String.format("%02d:%02d:%02d", hours, minutes, duration);
        JspWriter out = pageContext.getOut();
        try {
            out.write(result);
        } catch (IOException e) {
            throw new JspException(e);
        }
        return SKIP_BODY;
    }
}
