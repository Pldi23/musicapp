package by.platonov.music.tag;

import by.platonov.music.validator.Violation;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Set;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-11.
 * @version 0.0.1
 */
public class ViolationTag extends TagSupport {

    private Set<Violation> violations;

    public void setViolations(Set<Violation> violations) {
        this.violations = violations;
    }

    @Override
    public int doStartTag() throws JspException {
        if (violations != null && !violations.isEmpty()) {
            JspWriter out = pageContext.getOut();
            try {
                for (Violation violation : violations) {
                    out.write(violation.getMessage() + "\n");
                }
            } catch (IOException e) {
                throw new JspException(e);
            }
        }
        return SKIP_BODY;
    }
}
