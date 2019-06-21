package by.platonov.music.controller.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import java.io.IOException;

/**
 * Filter using for setting encoding of request and response to UTF-8
 * @author dzmitryplatonov on 2019-06-20.
 * @version 0.0.1
 */
@WebFilter(urlPatterns = "/*", initParams = {@WebInitParam(name = "encoding", value = "UTF-8", description = "Encoding Param")})
public class RequestEncodingFilter implements Filter {

    private static final String ENCODING = "encoding";
    private String code;


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        code = filterConfig.getInitParameter(ENCODING);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String codeRequest = request.getCharacterEncoding();
        if (code != null && !code.equalsIgnoreCase(codeRequest)) {
            request.setCharacterEncoding(code);
        }
        filterChain.doFilter(request, response);
    }


    @Override
    public void destroy() {
        code = null;
    }
}
