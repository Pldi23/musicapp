package by.platonov.music.controller.filter;

import by.platonov.music.command.page.PageConstant;
import by.platonov.music.entity.User;
import lombok.extern.log4j.Log4j2;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author dzmitryplatonov on 2019-06-23.
 * @version 0.0.1
 */
@Log4j2
@WebFilter(urlPatterns = {"/jsp/*"}, initParams = {@WebInitParam(name = "TARGET_PAGE_PATH", value = PageConstant.LOGIN_PAGE)})
public class RoleFilter implements Filter {

    private String targetPagePath;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        targetPagePath = filterConfig.getServletContext().getContextPath() + filterConfig.getInitParameter("TARGET_PAGE_PATH");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        log.debug("role filter");

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (!checkAccess(request, user)) {
            request.getSession().setAttribute("wrongAction", "Please authorise as admin");
            request.getRequestDispatcher(PageConstant.LOGIN_PAGE).forward(request, response);
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }

    }

    private boolean checkAccess(HttpServletRequest request, User user) {
        String uri = request.getRequestURI();
        return !uri.contains(PageConstant.ADMIN_PAGE) || user.isAdmin();
    }

    @Override
    public void destroy() {

    }
}

