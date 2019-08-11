package by.platonov.music.controller.filter;

import by.platonov.music.command.CommandType;
import by.platonov.music.constant.RequestConstant;
import lombok.extern.log4j.Log4j2;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static by.platonov.music.constant.PageConstant.INDEX_PAGE;

/**
 * filter is used to redirect users to index page when session timed out
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
        if (request.getSession().isNew()
                && request.getParameterMap().containsKey(RequestConstant.COMMAND)
                && !request.getParameter(RequestConstant.COMMAND).equalsIgnoreCase(CommandType.LOGIN.toString())) {
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
