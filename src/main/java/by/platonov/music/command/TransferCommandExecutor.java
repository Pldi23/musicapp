package by.platonov.music.command;

import by.platonov.music.exception.ServiceException;

import java.util.List;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-07.
 * @version 0.0.1
 */
public interface TransferCommandExecutor<T> {
    List<T> transfer(String parameter) throws ServiceException;
}
