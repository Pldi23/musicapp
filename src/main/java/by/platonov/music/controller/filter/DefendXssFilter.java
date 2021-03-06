package by.platonov.music.controller.filter;

import lombok.extern.log4j.Log4j2;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * filter used to prevent xss attacks
 *
 * @author Dzmitry Platonov on 2019-07-10.
 * @version 0.0.1
 */
@Log4j2
@WebFilter(urlPatterns = {"/*"})
public class DefendXssFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig){
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        chain.doFilter(new XssRequestWrapper((HttpServletRequest) request), response);
    }

}
