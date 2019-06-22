package by.platonov.music.controller.listener;

import by.platonov.music.db.ConnectionPool;
import lombok.extern.log4j.Log4j2;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.SQLException;

/**
 * Listener using for initialising connections at the start of application
 * @author dzmitryplatonov on 2019-06-22.
 * @version 0.0.1
 */
@Log4j2
@WebListener
public class ConnectionPoolListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ConnectionPool.getInstance();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        try {
            ConnectionPool.getInstance().tierDown();
        } catch (SQLException e) {
            log.error("ConnectionPool could not be destroyed", e);
        }
    }
}
