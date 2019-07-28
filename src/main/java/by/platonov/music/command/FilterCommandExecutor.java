package by.platonov.music.command;

import by.platonov.music.exception.ServiceException;

import java.util.List;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-26.
 * @version 0.0.1
 */
public interface FilterCommandExecutor<T> {

    List<T> filter(int limit, long offset) throws ServiceException;
}
