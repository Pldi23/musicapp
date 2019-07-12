package by.platonov.music.command;

import by.platonov.music.exception.ServiceException;

import java.util.List;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-06.
 * @version 0.0.1
 */
public interface SortCommandExecutor<T> {
    List<T> sort(boolean isAsc, int limit, long offset) throws ServiceException;
}
