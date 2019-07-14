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
import java.util.Locale;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-13.
 * @version 0.0.1
 */
public class TopTracksTag extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        CommonService service = new CommonService();
        System.out.println("page contaxt session " + pageContext.getSession().getAttribute(RequestConstant.LOCALE));
        System.out.println("page request locale " + pageContext.getRequest().getLocale());

        Locale locale = pageContext.getSession().getAttribute(RequestConstant.LOCALE) != null ?
                new Locale((String) pageContext.getSession().getAttribute(RequestConstant.LOCALE)) : pageContext.getRequest().getLocale();
//        Locale.setDefault(new Locale((String) pageContext.getSession().getAttribute(RequestConstant.LOCALE)));
        System.out.println(locale);
//        Locale.setDefault(locale);
//        Locale.setDefault(new Locale("en_US"));
        System.out.println();
        JspWriter out = pageContext.getOut();
        try {
            List<Track> tracks = service.getRandomTen();
            out.write("<h3>" + MessageManager.getMessage("label.topten") + "</h3>");
            for (Track track : tracks) {
                out.write(track.getName());
                for (Musician singer:track.getSingers()) {
                    out.write(singer.getName());
                }
                out.write("<audio controls><source src=\"music/" + track.getUuid() + "\" type=\"audio/mpeg\"></audio>");
                out.write("<form action=\"controller\" method=\"get\"><input type=\"hidden\" name=\"command\" value=\"track-detail\">\n" +
                        "<input type=\"hidden\" name=\"id\" value=\"" + track.getId() + "\">\n" +
                        "<input type=\"submit\" name=\"submit\" value=\"" + MessageManager.getMessage("button.details") + "\"/>\n" +
                        "</form>");
            }
        } catch (ServiceException | IOException e) {
            throw new JspException(e);
        }
        return SKIP_BODY;
    }
}
