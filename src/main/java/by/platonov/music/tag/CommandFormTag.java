package by.platonov.music.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

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
        JspWriter out = pageContext.getOut();
        try {
            out.write("<form action=\"controller\" method=\"get\">");
            out.write("<input type=\"hidden\" name=\"command\" value=\"" + commandValue + "\">");
            out.write("<input type=\"hidden\" name=\"offset\" value=\"0\">");
            if (sortOrder) {
                out.write("<input type=\"hidden\" name=\"order\" value=\"marker\">");
            }
            out.write("<input type=\"submit\" name=\"submit\" value=\"" +
                    submitValue + "\">");
            out.write("</form>");
        } catch (IOException e) {
            throw new JspException(e);
        }
        return SKIP_BODY;
    }
}
