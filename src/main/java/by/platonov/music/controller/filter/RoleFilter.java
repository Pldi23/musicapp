package by.platonov.music.controller.filter;

import by.platonov.music.constant.RequestConstant;
import by.platonov.music.entity.User;
import lombok.extern.log4j.Log4j2;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static by.platonov.music.constant.PageConstant.*;

/**
 * @author dzmitryplatonov on 2019-06-23.
 * @version 0.0.1
 */
@Log4j2
@WebFilter(urlPatterns = {"/jsp/*"},
        initParams = {@WebInitParam(name = "TARGET_PAGE_PATH", value = NOT_AUTHORIZED_PAGE)})
public class RoleFilter implements Filter {

    private String targetPagePath;

    @Override
    public void init(FilterConfig filterConfig) {
        targetPagePath = filterConfig.getServletContext().getContextPath() + filterConfig.getInitParameter("TARGET_PAGE_PATH");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(RequestConstant.USER);

        if (!checkAccess(request, user)) {
            log.warn("non-authorized access blocked");
            response.sendRedirect(targetPagePath);
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }

    }

    private boolean checkAccess(HttpServletRequest request, User user) {
        boolean result = true;
        String uri = request.getRequestURI();
        if (user == null && uri.contains(ADMIN_DIRECTORY) || uri.contains(USER_DIRECTORY) || uri.contains(MUSIC_LIB_DIRECTORY)) {
            result = false;
        }
        if(user != null && !user.isAdmin() && uri.contains(ADMIN_DIRECTORY)) {
            result = false;
        }
        return result;
    }

    @Override
    public void destroy() {
        targetPagePath = null;
    }
}

