package by.platonov.music.controller.filter;

import by.platonov.music.command.CommandType;
import by.platonov.music.constant.RequestConstant;
import by.platonov.music.entity.User;
import by.platonov.music.message.MessageManager;
import lombok.extern.log4j.Log4j2;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;

import static by.platonov.music.constant.PageConstant.PAYMENT_PAGE;

/**
 * filter used to forward user to payment page when paid period is over
 *
 * @author Dzmitry Platonov on 2019-08-06.
 * @version 0.0.1
 */
@Log4j2
@WebFilter(urlPatterns = {"/controller", "/jsp/*"},
        initParams = {@WebInitParam(name = "TARGET_PAGE_PATH", value = PAYMENT_PAGE)})
public class UserPaidPeriodFilter implements Filter {

    private String targetPagePath;

    @Override
    public void init(FilterConfig filterConfig) {
        targetPagePath = filterConfig.getInitParameter("TARGET_PAGE_PATH");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        User user = (User) request.getSession().getAttribute(RequestConstant.USER);
        if (isPaidPeriod(user) && isRedirected(request) && !isAllowedCommand(request)) {
            log.info("User redirected, paid period ended for " + user.getLogin() + " " + user.getPaidPeriod());
            request.setAttribute(RequestConstant.PROCESS, MessageManager.getMessage("message.expired",
                    (String) request.getSession().getAttribute(RequestConstant.LOCALE)));
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(targetPagePath);
            requestDispatcher.forward(servletRequest, servletResponse);
        } else {
            if (request.getSession().getAttribute(RequestConstant.REDIRECTED) != null) {
                request.getSession().removeAttribute(RequestConstant.REDIRECTED);
            }
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {
        targetPagePath = null;
    }

    private boolean isPaidPeriod(User user) {
        return user != null
                && !user.isAdmin()
                && user.getPaidPeriod().isBefore(LocalDateTime.now());
    }

    private boolean isRedirected(HttpServletRequest request) {
        return request.getSession().getAttribute(RequestConstant.REDIRECTED) == null
                || !(boolean) request.getSession().getAttribute(RequestConstant.REDIRECTED);
    }

    private boolean isAllowedCommand(HttpServletRequest request) {
        return request.getParameter(RequestConstant.COMMAND).equalsIgnoreCase(CommandType.LOGOUT.name()) ||
                request.getParameter(RequestConstant.COMMAND).equalsIgnoreCase(CommandType.PAY.name()) ||
                request.getParameter(RequestConstant.COMMAND)
                        .equalsIgnoreCase(CommandType.SET_LOCALE.name().replace("_", "-"));
    }
}
