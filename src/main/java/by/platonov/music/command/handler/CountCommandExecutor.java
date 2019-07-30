package by.platonov.music.command.handler;

import by.platonov.music.exception.ServiceException;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-08.
 * @version 0.0.1
 */
public interface CountCommandExecutor {
    long count() throws ServiceException;
}
