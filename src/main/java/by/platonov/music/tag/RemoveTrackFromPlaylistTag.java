package by.platonov.music.tag;

import by.platonov.music.message.MessageManager;
import by.platonov.music.entity.Playlist;
import by.platonov.music.entity.Track;
import lombok.extern.log4j.Log4j2;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Set;

import static by.platonov.music.constant.RequestConstant.LOCALE;

/**
 * tag to write remove-track form
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
            String locale = (String) pageContext.getSession().getAttribute(LOCALE);
            String button = MessageManager.getMessage("button.remove.from.playlist", locale);
            JspWriter out = pageContext.getOut();
            try {
                out.write("<td style=\"padding-top: 15px\">\n" +
                        "<form action=\"" + pageContext.getRequest().getServletContext().getContextPath() + "/controller\" method=\"post\">\n" +
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
