package by.platonov.music.controller.filter;

import lombok.extern.log4j.Log4j2;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static by.platonov.music.constant.PageConstant.INDEX_PAGE;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-31.
 * @version 0.0.1
 */
@Log4j2
@WebFilter(urlPatterns = {"/*"},
        initParams = {@WebInitParam(name = "TARGET_PAGE_PATH", value = INDEX_PAGE)})
public class SessionTimeOutFilter implements Filter {

    private String targetPagePath;

    @Override
    public void init(FilterConfig filterConfig) {
        targetPagePath = filterConfig.getServletContext().getContextPath() + filterConfig.getInitParameter("TARGET_PAGE_PATH");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        if (request.getSession(false) == null || request.getSession().isNew()) {
            log.info("session was timed out, user was redirected");
            request.getSession(true);
            response.sendRedirect(targetPagePath);
        } else {
            filterChain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        targetPagePath = null;
    }
}
