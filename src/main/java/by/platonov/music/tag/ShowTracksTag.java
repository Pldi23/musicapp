package by.platonov.music.tag;

import by.platonov.music.MessageManager;
import by.platonov.music.command.constant.RequestConstant;
import by.platonov.music.entity.Musician;
import by.platonov.music.entity.Track;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static by.platonov.music.command.constant.RequestConstant.*;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-11.
 * @version 0.0.1
 */
public class ShowTracksTag extends TagSupport {

    private List<Track> tracks;
    private String commandValue;//logic operation for example sort/filter/show all
    private String removeCommandValue;//visible for admin
    private String updateCommandValue;//visible for admin
    private String moreCommandValue;//visible for user
    private boolean nextUnavailable;
    private boolean previousUnavailable;
    private boolean admin;

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }

    public void setCommandValue(String commandValue) {
        this.commandValue = commandValue;
    }

    public void setPreviousUnavailable(boolean previousUnavailable) {
        this.previousUnavailable = previousUnavailable;
    }

    public void setRemoveCommandValue(String removeCommandValue) {
        this.removeCommandValue = removeCommandValue;
    }

    public void setMoreCommandValue(String moreCommandValue) {
        this.moreCommandValue = moreCommandValue;
    }

    public void setUpdateCommandValue(String updateCommandValue) {
        this.updateCommandValue = updateCommandValue;
    }

    public void setNextUnavailable(boolean nextUnavailable) {
        this.nextUnavailable = nextUnavailable;
    }

    @Override
    public int doStartTag() throws JspException {
        if (tracks != null && !tracks.isEmpty()) {
            JspWriter out = pageContext.getOut();
            try {
                out.write(MessageManager.getMessage("label.tracks", (String) pageContext.getSession().getAttribute(RequestConstant.LOCALE)));
                out.write("<table>");
                for (Track track : tracks) {
                    out.write("<tr>");
                    out.write("<td>" + track.getId() + "</c:out></td>");
                    out.write("<td>" + track.getName() + "</c:out></td>");
                    for (Musician singer : track.getSingers()) {
                        out.write("<td>" + singer.getName() + "</c:out></td>");
                    }
                    for (Musician author : track.getAuthors()) {
                        out.write("<td>" + author.getName() + "</td>");
                    }
                    out.write("<td>" + track.getGenre().getTitle() + "</td>");
                    out.write("<td>");
                    out.write("<audio controls preload=\"metadata\">");
                    out.write("<source src=\"music/" + track.getUuid() + "\" type=\"audio/mpeg\">");
                    out.write("</audio>");
                    out.write("</td>");
                    if (admin) {
                        printAdditionalForm(out, track, removeCommandValue, "button.remove");
                        printAdditionalForm(out, track, updateCommandValue, "button.update");
                    }
                    printAdditionalForm(out, track, moreCommandValue, "button.details");
                    out.write("</tr>");

                }
                out.write("</table>");

                if (!nextUnavailable) {
                    out.write("<form action=\"controller\" method=\"get\">");
                    out.write("<input type=\"hidden\" name=\"command\" value=\"" + commandValue + "\">");
                    out.write("<input type=\"hidden\" name=\"direction\" value=\"next\">");
                    printHiddenFilter(out);
                    out.write("<input type=\"submit\" name=\"submit\" value=\"" +
                            MessageManager.getMessage("button.next", (String) pageContext.getSession().getAttribute(RequestConstant.LOCALE)) + "\">");
                    out.write("</form>");
                }
                if (!previousUnavailable) {
                    out.write("<form action=\"controller\" method=\"get\">");
                    out.write("<input type=\"hidden\" name=\"command\" value=\"" + commandValue + "\">");
                    out.write("<input type=\"hidden\" name=\"direction\" value=\"previous\">");
                    printHiddenFilter(out);
                    out.write("<input type=\"submit\" name=\"submit\" value=\"" +
                            MessageManager.getMessage("button.previous", (String) pageContext.getSession().getAttribute(RequestConstant.LOCALE)) + "\">");
                    out.write("</form>");
                }
            } catch (IOException e) {
                throw new JspException(e);
            }

        }
        return SKIP_BODY;
    }

    private void printHiddenFilter(JspWriter out) throws IOException {
        out.write("<input type=\"hidden\" name=\"trackname\" value=\"" + pageContext.getRequest().getAttribute(TRACKNAME) + "\">");
        out.write("<input type=\"hidden\" name=\"singer\" value=\"" + pageContext.getRequest().getAttribute(SINGER) + "\">");
        out.write("<input type=\"hidden\" name=\"genre\" value=\"" + pageContext.getRequest().getAttribute(GENRE) + "\">");
        out.write("<input type=\"hidden\" name=\"releaseFrom\" value=\"" + pageContext.getRequest().getAttribute(RELEASE_FROM) + "\">");
        out.write("<input type=\"hidden\" name=\"releaseTo\" value=\"" + pageContext.getRequest().getAttribute(RELEASE_TO) + "\">");
    }

    private void printAdditionalForm(JspWriter out, Track track, String additionalCommandValue, String buttonKey) throws IOException {
        out.write("<td>");
        out.write("<form method=\"get\" action=\"controller\">");
        out.write("<input type=\"hidden\" name=\"command\" value=\"" + additionalCommandValue + "\">");
        out.write("<input type=\"hidden\" name=\"id\" value=\"" + track.getId() + "\">");
        out.write("<input type=\"submit\" name=\"submit\" value=\"" +
                MessageManager.getMessage(buttonKey, (String) pageContext.getSession().getAttribute(RequestConstant.LOCALE)) + "\">");
        out.write("</form>");
        out.write("</td>");
    }
}
