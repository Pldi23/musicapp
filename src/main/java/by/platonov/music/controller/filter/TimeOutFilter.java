package by.platonov.music.controller.filter;

import lombok.extern.log4j.Log4j2;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import java.io.IOException;

import static by.platonov.music.command.constant.PageConstant.*;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-23.
 * @version 0.0.1
 */
@Log4j2
@WebFilter(urlPatterns = {"/*"},
        initParams = {@WebInitParam(name = "TARGET_PAGE_PATH", value = SESSION_TIMEOUT_PAGE)})
public class TimeOutFilter implements Filter {

    private String targetPath;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        targetPath = filterConfig.getServletContext().getContextPath() + filterConfig.getInitParameter("TARGET_PAGE_PATH");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession(false);
        if (session != null && !session.isNew()) {
            filterChain.doFilter(request, response);
        } else {
            log.info("session was timed out");
            response.sendRedirect(targetPath);
        }
    }

    @Override
    public void destroy() {

    }
}
