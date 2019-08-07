package by.platonov.music.tag;

import by.platonov.music.message.MessageManager;
import by.platonov.music.entity.Musician;
import by.platonov.music.entity.Track;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.List;

import static by.platonov.music.constant.RequestConstant.*;

/**
 * to print a table from the list of tracks
 *
 * @author Dzmitry Platonov on 2019-07-11.
 * @version 0.0.1
 */
public class TrackTableTag extends TagSupport {

    /**
     * tracks list
     */
    private List<Track> tracks;
    /**
     * command for logic operation for example logic operation like sort/filter/show all
     */
    private String commandValue;

    /**
     * request parameter of current page
     */
    private int current;

    /**
     * is next page unavailable
     */
    private boolean nextUnavailable;

    /**
     * is previous page unavailable
     */
    private boolean previousUnavailable;

    /**
     * is user admin
     */
    private boolean admin;

    /**
     * list of pages
     */
    private List<Integer> pages;

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }

    public void setCommandValue(String commandValue) {
        this.commandValue = commandValue;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public void setPages(List<Integer> pages) {
        this.pages = pages;
    }

    public void setPreviousUnavailable(boolean previousUnavailable) {
        this.previousUnavailable = previousUnavailable;
    }

    public void setNextUnavailable(boolean nextUnavailable) {
        this.nextUnavailable = nextUnavailable;
    }

    @Override
    public int doStartTag() throws JspException {
        String locale = pageContext.getSession().getAttribute(LOCALE) != null ?
                (String) pageContext.getSession().getAttribute(LOCALE) : pageContext.getRequest().getLocale().toString();
        if (tracks != null && !tracks.isEmpty()) {
            JspWriter out = pageContext.getOut();
            try {
                out.write(MessageManager.getMessage("label.tracks", locale));
                out.write("<table>");
                out.write("<tbody>");
                printTableTracks(out);
                out.write("</tbody>");
                out.write("</table>");
                out.write("<nav aria-label=\"Page navigation example\"><ul class=\"pagination\"><li class=\"page-item\">");
                if (!nextUnavailable) {
                    printListingForm(out, "next", "button.next", locale);
                }
                out.write("</li>" +
                        "<li class=\"page-item\">");
                if (!previousUnavailable) {
                    printListingForm(out, "previous", "button.previous", locale);
                }
                out.write("</li></ul></nav>");
                out.write("<nav aria-label=\"Page navigation example\"><ul class=\"pagination\">");
                for (int digit : pages) {
                    if (digit == current) {
                        out.write("<li class=\"page-item active\" aria-current=\"page\">");
                        printListingForm(out, "direct", String.valueOf(digit), locale);
                    } else {
                        out.write("<li class=\"page-item\">");
                        printListingForm(out, "direct", String.valueOf(digit), locale);
                    }
                    out.write("</li>");
                }
                out.write("</ul></nav>");

            } catch (IOException e) {
                throw new JspException(e);
            }

        }
        return SKIP_BODY;
    }

    private void printTableTracks(JspWriter out) throws IOException {
        for (Track track : tracks) {
            out.write("<tr class=\"table-bg-light\">");
            if (admin) {
                out.write("<td>" + track.getId() + "</c:out></td>");
            }
            out.write("<td><form action=\"" + pageContext.getRequest().getServletContext().getContextPath() + "/controller\" method=\"post\"><input type=\"hidden\" name=\"command\" value=\"track-detail\">\n" +
                    "<input type=\"hidden\" name=\"id\" value=\"" + track.getId() + "\">\n" +
                    "<input type=\"submit\" class=\"btn btn-light\" name=\"submit\" value=\"" + track.getName() + "\"/>\n" +
                    "</form></td>");
            out.write("<td>");
            out.write("<div class=\"btn-group\" role=\"group\" aria-label=\"Basic example\">");
            for (Musician singer : track.getSingers()) {
                printMusicianButton(out, singer);
            }
            out.write("</div>");
            out.write("</td>");
//            for (Musician author : track.getAuthors()) {
//                out.write("<td>" + author.getName() + "</td>");
//            }
            out.write("<td><span class=\"badge badge-info\">" + track.getGenre().getTitle() + "</span></td>");
            out.write("<td>");
            out.write("<audio controls preload=\"metadata\">");
            out.write("<source src=\"music/" + track.getUuid() + "\" type=\"audio/mpeg\">");
            out.write("</audio>");
            out.write("</td>");
            out.write("</tr>");
        }
    }

    private void printHiddenFilter(JspWriter out) throws IOException {
        out.write("<input type=\"hidden\" name=\"trackname\" value=\"" + pageContext.getRequest().getAttribute(TRACKNAME) + "\">");
        out.write("<input type=\"hidden\" name=\"singer\" value=\"" + pageContext.getRequest().getAttribute(SINGER) + "\">");
        out.write("<input type=\"hidden\" name=\"genre\" value=\"" + pageContext.getRequest().getAttribute(GENRE) + "\">");
        out.write("<input type=\"hidden\" name=\"releaseFrom\" value=\"" + pageContext.getRequest().getAttribute(RELEASE_FROM) + "\">");
        out.write("<input type=\"hidden\" name=\"releaseTo\" value=\"" + pageContext.getRequest().getAttribute(RELEASE_TO) + "\">");
    }

    private void printListingForm(JspWriter out, String direction, String button, String locale) throws IOException {
        out.write("<form action=\"" + pageContext.getRequest().getServletContext().getContextPath() + "/controller\" method=\"post\">");
        out.write("<input type=\"hidden\" name=\"command\" value=\"" + commandValue + "\">");
        out.write("<input type=\"hidden\" name=\"direction\" value=\"" + direction + "\">");
        out.write("<input type=\"hidden\" name=\"current\" value=\"" + current + "\">");
        printHiddenFilter(out);
        out.write("<input type=\"submit\" class=\"btn btn-outline-dark\" name=\"submit\" value=\"" +
                MessageManager.getMessage(button, locale) + "\">");
        out.write("</form>");
    }

    private void printMusicianButton(JspWriter out, Musician musician) throws IOException {
        out.write("<form action=\"" + pageContext.getRequest().getServletContext().getContextPath() + "/controller\" method=\"post\"><input type=\"hidden\" name=\"command\" value=\"musician-detail\">\n" +
                "<input type=\"hidden\" name=\"id\" value=\"" + musician.getId() + "\">\n" +
                "<input type=\"submit\" class=\"btn btn-light btn-sm\" name=\"submit\" value=\"" + musician.getName() + "\"/>\n" +
                "</form>");
    }
}
