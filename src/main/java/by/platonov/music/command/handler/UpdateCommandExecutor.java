package by.platonov.music.command.handler;

import by.platonov.music.entity.User;

/**
 * interface represents which user and by what parameter we want to update
 *
 * @author Dzmitry Platonov on 2019-07-16.
 * @version 0.0.1
 */
public interface UpdateCommandExecutor {
    void update(User user, String parameter);
}
