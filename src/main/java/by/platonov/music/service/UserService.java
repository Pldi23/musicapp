package by.platonov.music.service;

import by.platonov.music.entity.Playlist;
import by.platonov.music.entity.User;
import by.platonov.music.exception.RepositoryException;
import by.platonov.music.exception.ServiceException;
import by.platonov.music.repository.PlaylistRepository;
import by.platonov.music.repository.Repository;
import by.platonov.music.repository.UserRepository;
import by.platonov.music.repository.specification.*;
import lombok.EqualsAndHashCode;
import lombok.extern.log4j.Log4j2;

import java.util.List;

/**
 * @author dzmitryplatonov on 2019-06-17.
 * @version 0.0.1
 */
@Log4j2
@EqualsAndHashCode
public class UserService {

    private static final String EXCEPTION_MESSAGE = "Repository provide an exception for user service";

    public List<User> login(String login) throws ServiceException {
        SqlSpecification specification = new UserLoginSpecification(login);
        List<User> users;
        try {
            log.debug("searching " + login + " in repository");
            users = UserRepository.getInstance().query(specification);
            for (User user : users) {
                getUserWithPlaylists(user);
            }
            return users;
        } catch (RepositoryException e) {
            throw new ServiceException(EXCEPTION_MESSAGE, e);
        }
    }

    public boolean register(User user) throws ServiceException {
        try {
            log.debug("registering " + user + " in repository");
            return UserRepository.getInstance().add(user);
        } catch (RepositoryException e) {
            throw new ServiceException(EXCEPTION_MESSAGE, e);
        }
    }

    public boolean activate(String email, String hash) throws ServiceException {
        boolean result = false;
        SqlSpecification specification = new UserEmailHashSpecification(email, hash);
        List<User> users;
        try {
            users = UserRepository.getInstance().query(specification);
            if (!users.isEmpty()) {
                User user = users.get(0);
                user.setActive(true);
                user.setVerificationUuid(null);
                log.debug("activating " + email);
                result = UserRepository.getInstance().update(user);
            }
        } catch (RepositoryException e) {
            throw new ServiceException(EXCEPTION_MESSAGE, e);
        }
        return result;
    }

    public void removeNotActiveUser() throws ServiceException {
        SqlSpecification specification = new NotConfirmedRegistrationUserSpecification();
        List<User> users;
        try {
            users = UserRepository.getInstance().query(specification);
            if (!users.isEmpty()) {
                for (User user : users) {
                    log.debug("removing not active " + users);
                    UserRepository.getInstance().remove(user);
                }
            }
        } catch (RepositoryException e) {
            throw new ServiceException(EXCEPTION_MESSAGE, e);
        }
    }

    public boolean updateUser(User user) throws ServiceException {
        try {
            return UserRepository.getInstance().update(user);
        } catch (RepositoryException e) {
            throw new ServiceException(EXCEPTION_MESSAGE, e);
        }
    }

    public boolean updatePlaylistAccess(String playlistId) throws ServiceException {
        try {
            Playlist playlist = PlaylistRepository.getInstance()
                    .query(new PlaylistIdSpecification(Long.parseLong(playlistId))).get(0);
            boolean isPersonal = !playlist.isPersonal();
            playlist.setPersonal(isPersonal);
            return PlaylistRepository.getInstance().update(playlist);
        } catch (RepositoryException e) {
            throw new ServiceException(EXCEPTION_MESSAGE, e);
        }
    }

    private User getUserWithPlaylists(User user) throws RepositoryException {
        List<Playlist> playlists = PlaylistRepository.getInstance().query(new PlaylistUserSpecification(user.getLogin()));
        user.getPlaylists().addAll(playlists);
        return user;
    }
}
