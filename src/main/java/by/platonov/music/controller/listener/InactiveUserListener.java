package by.platonov.music.controller.listener;

import by.platonov.music.exception.ServiceException;
import by.platonov.music.service.UserService;
import lombok.extern.log4j.Log4j2;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Listener using for removing users with unconfirmed email address
 * @author dzmitryplatonov on 2019-06-22.
 * @version 0.0.1
 */
@Log4j2
@WebListener
public class InactiveUserListener implements ServletContextListener {

    private ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext context = servletContextEvent.getServletContext();
        String interval = context.getInitParameter("interval");
        scheduledExecutorService.schedule(() -> {
            UserService service = new UserService();
            try {
                log.info("trying to find and remove inactive users");
                service.removeNotActiveUser();
            } catch (ServiceException e) {
                log.error("Could not remove inactive users", e);
            }
        }, Integer.parseInt(interval), TimeUnit.MINUTES);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        scheduledExecutorService.shutdown();
        log.info("listener destroyed");
    }
}
