package by.platonov.music.command.handler;

import by.platonov.music.exception.ServiceException;

import java.util.List;

/**
 * interface represents what parameter we want to transfer to another page
 *
 * @author Dzmitry Platonov on 2019-07-07.
 * @version 0.0.1
 */
public interface TransferCommandExecutor<T> {
    List<T> transfer(String parameter) throws ServiceException;
}
