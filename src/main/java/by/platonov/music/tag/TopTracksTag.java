package by.platonov.music.tag;

import by.platonov.music.MessageManager;
import by.platonov.music.command.constant.RequestConstant;
import by.platonov.music.entity.Musician;
import by.platonov.music.entity.Track;
import by.platonov.music.exception.ServiceException;
import by.platonov.music.service.CommonService;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-13.
 * @version 0.0.1
 */
public class TopTracksTag extends TagSupport {

    private boolean head;

    public void setHead(boolean head) {
        this.head = head;
    }

    @Override
    public int doStartTag() throws JspException {
        CommonService service = new CommonService();
        String locale = pageContext.getSession().getAttribute(RequestConstant.LOCALE) != null ?
                (String) pageContext.getSession().getAttribute(RequestConstant.LOCALE) : pageContext.getRequest().getLocale().toString();
        JspWriter out = pageContext.getOut();
        try {
            List<Track> tracks = service.getRandomTen();
            if (head) {
                out.write("<div class=\"container container-fluid bg-light\">");
                out.write("<div class=\"row\">");
                out.write("<div class=\"col-8\">");
                out.write("<h3>" + MessageManager.getMessage("label.topten", locale) + "</h3>");
                out.write("</div>");
                out.write("</div>");
                out.write("</div>");
            }
            out.write("<div class=\"container container-fluid bg-light\">");
            out.write("<div class=\"row\">");
            out.write("<div class=\"col-11\">");
            out.write("<table>");
            out.write("<tbody>");
            for (Track track : tracks) {
                out.write("<tr class=\"table-bg-light\">");
                out.write("<td><form action=\"controller\" method=\"get\"><input type=\"hidden\" name=\"command\" value=\"track-detail\">\n" +
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
                out.write("<td><audio controls><source src=\"music/" + track.getUuid() + "\" type=\"audio/mpeg\"></audio></td>");
                out.write("</tr>");
            }
            out.write("</tbody>");
            out.write("</table>");
            out.write("</div>");
            out.write("</div>");
            out.write("</div>");
        } catch (ServiceException | IOException e) {
            throw new JspException(e);
        }
        return SKIP_BODY;
    }

    private void printMusicianButton(JspWriter out, Musician musician) throws IOException {

        out.write("<form action=\"controller\" method=\"get\"><input type=\"hidden\" name=\"command\" value=\"musician-detail\">\n" +
                "<input type=\"hidden\" name=\"id\" value=\"" + musician.getId() + "\">\n" +
                "<input type=\"submit\" class=\"btn btn-light btn-sm\" name=\"submit\" value=\"" + musician.getName() + "\"/>\n" +
                "</form>");
    }
}
