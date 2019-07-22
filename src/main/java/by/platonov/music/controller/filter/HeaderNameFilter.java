package by.platonov.music.controller.filter;

import by.platonov.music.command.constant.RequestConstant;
import lombok.extern.log4j.Log4j2;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-10.
 * @version 0.0.1
 */
@Log4j2
//@WebFilter(urlPatterns = {"/*"})
public class HeaderNameFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        HttpServletResponseWrapper responseWrapper = new HttpServletResponseWrapper(httpServletResponse);

        responseWrapper.setHeader("Referer", (String) httpServletRequest.getSession().getAttribute(RequestConstant.PAGE));
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
