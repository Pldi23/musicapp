package by.platonov.music.controller.listener;

import by.platonov.music.exception.ServiceException;
import by.platonov.music.service.AdminService;
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
 * Listener used to remove users with unconfirmed email address, and unused playlists
 * @author dzmitryplatonov on 2019-06-22.
 * @version 0.0.1
 */
@Log4j2
@WebListener
public class ExpiredDataListener implements ServletContextListener {

    private static final String INTERVAL = "interval";

    private ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext context = servletContextEvent.getServletContext();
        String interval = context.getInitParameter(INTERVAL);
        scheduledExecutorService.schedule(() -> {
            UserService userService = new UserService();
            AdminService adminService = new AdminService();
            try {
                log.info("trying to find and remove inactive users");
                userService.removeNotActiveUser();
                log.info("trying to find and remove unused private playlists");
                adminService.removeUnusedPlaylists();
            } catch (ServiceException e) {
                log.error("Could not remove inactive/unused entities", e);
            }
        }, Integer.parseInt(interval), TimeUnit.MINUTES);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        scheduledExecutorService.shutdown();
        log.debug("listener destroyed");
    }
}
