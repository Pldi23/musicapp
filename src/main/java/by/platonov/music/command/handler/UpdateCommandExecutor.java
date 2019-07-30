package by.platonov.music.command.handler;

import by.platonov.music.entity.User;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-16.
 * @version 0.0.1
 */
public interface UpdateCommandExecutor {
    void update(User user, String parameter);
}
