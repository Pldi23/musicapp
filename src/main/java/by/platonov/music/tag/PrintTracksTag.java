package by.platonov.music.tag;

import by.platonov.music.entity.Musician;
import by.platonov.music.entity.Track;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.List;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-13.
 * @version 0.0.1
 */
public class PrintTracksTag extends TagSupport {

    private boolean head;
    private String label;
    private List<Track> tracks;

    public void setHead(boolean head) {
        this.head = head;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        if (tracks != null && !tracks.isEmpty()) {
            try {
                if (head) {
                    out.write("<div class=\"container container-fluid bg-light\">");
                    out.write("<div class=\"row\">");
                    out.write("<div class=\"col-8\">");
                    out.write("<h3 class=\"text-info\">" + label + "</h3>");
                    out.write("</div></div></div>");
                }
                out.write("<div class=\"container container-fluid bg-light\">");
                out.write("<div class=\"row\">");
                out.write("<div class=\"col-11\">");
                out.write("<table>");
                out.write("<tbody>");
                for (Track track : tracks) {
                    out.write("<tr class=\"table-bg-light\">");
                    out.write("<td><form action=\"" + pageContext.getRequest().getServletContext().getContextPath() +
                            "/controller\" method=\"get\"><input type=\"hidden\" name=\"command\" value=\"track-detail\">\n" +
                            "<input type=\"hidden\" name=\"id\" value=\"" + track.getId() + "\">\n" +
                            "<input type=\"submit\" class=\"btn btn-light\" name=\"submit\" value=\"" + track.getName() + "\"/>\n" +
                            "</form></td>");
                    out.write("<td>");
                    out.write("<div class=\"btn-group\" role=\"group\" aria-label=\"Basic example\">");
                    for (Musician musician : track.getSingers()) {
                        printMusicianButton(out, musician);
                    }
                    out.write("</div>");
                    out.write("</td>");
                    out.write("<td><audio controls preload=\"metadata\"><source src=\"music/" + track.getUuid() + "\" type=\"audio/mpeg\"></audio></td>");
                    out.write("</tr>");
                }
                out.write("</tbody>");
                out.write("</table>");
                out.write("</div></div></div>");
            } catch (IOException e) {
                throw new JspException(e);
            }
        }
        return SKIP_BODY;
    }

    private void printMusicianButton(JspWriter out, Musician musician) throws IOException {

        out.write("<form action=\"" + pageContext.getRequest().getServletContext().getContextPath() + "/controller\" method=\"get\"><input type=\"hidden\" name=\"command\" value=\"musician-detail\">\n" +
                "<input type=\"hidden\" name=\"id\" value=\"" + musician.getId() + "\">\n" +
                "<input type=\"submit\" class=\"btn btn-light btn-sm\" name=\"submit\" value=\"" + musician.getName() + "\"/>\n" +
                "</form>");
    }
}
