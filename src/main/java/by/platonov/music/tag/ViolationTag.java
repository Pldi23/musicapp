package by.platonov.music.tag;

import by.platonov.music.validator.Violation;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * tag to print validator violations
 *
 * @author Dzmitry Platonov on 2019-07-11.
 * @version 0.0.1
 */
public class ViolationTag extends TagSupport {

    /**
     * set of violations
     */
    private Set<Violation> violations;

    public void setViolations(Set<Violation> violations) {
        this.violations = violations;
    }

    @Override
    public int doStartTag() throws JspException {
        if (violations != null && !violations.isEmpty()) {
            String result = "\u2718" + violations.stream().map(Violation::getMessage).collect(Collectors.joining("\n\u2718"));
            JspWriter out = pageContext.getOut();
            try {
                out.write(result);
            } catch (IOException e) {
                throw new JspException(e);
            }
        }
        return SKIP_BODY;
    }
}
