package by.platonov.music.command;

import by.platonov.music.exception.ServiceException;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-07.
 * @version 0.0.1
 */
interface TransferCommandExecutor<T> {
    T transfer(String parameter) throws ServiceException;
}