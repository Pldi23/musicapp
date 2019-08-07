package by.platonov.music.command.handler;

import by.platonov.music.exception.ServiceException;

import java.util.List;

/**
 * interface represents what entity and by what criteria we want to sort
 *
 * @author Dzmitry Platonov on 2019-07-06.
 * @version 0.0.1
 */
public interface SortCommandExecutor<T> {
    List<T> sort(boolean isAsc, int limit, long offset) throws ServiceException;
}
