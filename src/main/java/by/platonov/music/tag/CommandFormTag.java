package by.platonov.music.tag;

import by.platonov.music.message.MessageManager;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

import static by.platonov.music.command.constant.RequestConstant.LOCALE;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-11.
 * @version 0.0.1
 */
public class CommandFormTag extends TagSupport {

    private String commandValue;
    private String submitValue;
    private boolean sortOrder;

    public void setCommandValue(String commandValue) {
        this.commandValue = commandValue;
    }

    public void setSubmitValue(String submitValue) {
        this.submitValue = submitValue;
    }

    public void setSortOrder(boolean sortOrder) {
        this.sortOrder = sortOrder;
    }

    @Override
    public int doStartTag() throws JspException {
        String locale = pageContext.getSession().getAttribute(LOCALE) != null ?
                (String) pageContext.getSession().getAttribute(LOCALE) : pageContext.getRequest().getLocale().toString();
        JspWriter out = pageContext.getOut();
        try {
            out.write("<form action=\"" + pageContext.getRequest().getServletContext().getContextPath() + "/controller\" method=\"get\">");
            out.write("<input type=\"hidden\" name=\"command\" value=\"" + commandValue + "\">");
            out.write("<input type=\"hidden\" name=\"offset\" value=\"0\">");
            if (sortOrder) {
                out.write("<input type=\"hidden\" name=\"order\" value=\"marker\">");
            }
            out.write("<input type=\"submit\" class=\"btn btn-outline-dark\" name=\"submit\" value=\"" +
                    MessageManager.getMessage(submitValue, locale) + "\">");
            out.write("</form>");
        } catch (IOException e) {
            throw new JspException(e);
        }
        return SKIP_BODY;
    }
}
