package by.platonov.music.tag;

import by.platonov.music.MessageManager;
import by.platonov.music.entity.Playlist;
import by.platonov.music.entity.Track;
import lombok.extern.log4j.Log4j2;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Set;

import static by.platonov.music.command.constant.RequestConstant.LOCALE;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-18.
 * @version 0.0.1
 */
@Log4j2
public class RemoveTrackFromPlaylistTag extends TagSupport {

    private Playlist currentPlaylist;
    private Set<Playlist> userPlaylists;
    private Track track;

    public void setCurrentPlaylist(Playlist currentPlaylist) {
        this.currentPlaylist = currentPlaylist;
    }

    public void setUserPlaylists(Set<Playlist> userPlaylists) {
        this.userPlaylists = userPlaylists;
    }

    public void setTrack(Track track) {
        this.track = track;
    }

    @Override
    public int doStartTag() throws JspException {
        if (userPlaylists != null && !userPlaylists.isEmpty() &&
                userPlaylists.stream().anyMatch(playlist -> playlist.getId() == currentPlaylist.getId())) {
            String locale = pageContext.getSession().getAttribute(LOCALE) != null ?
                    (String) pageContext.getSession().getAttribute(LOCALE) : pageContext.getRequest().getLocale().toString();
            String button = MessageManager.getMessage("button.remove.from.playlist", locale);
            JspWriter out = pageContext.getOut();
            try {
                out.write("<td>\n" +
                        "<form action=\"controller\" method=\"get\">\n" +
                        "<input type=\"hidden\" name=\"command\" value=\"remove-track-from-playlist\">\n" +
                        "<input type=\"hidden\" name=\"playlistid\" value=\"" + currentPlaylist.getId() + "\">\n" +
                        "<input type=\"hidden\" name=\"trackid\" value=\"" + track.getId() + "\">\n" +
                        "<input type=\"submit\" name=\"submit\" class=\"btn btn-outline-dark btn-sm\"\n" +
                        "value=\"" + button + "\">\n" +
                        "</form>\n" +
                        "</td>");
            } catch (IOException e) {
                throw new JspException(e);
            }
        }
        return SKIP_BODY;
    }
}
