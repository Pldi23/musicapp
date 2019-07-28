package by.platonov.music.service;

import by.platonov.music.entity.Playlist;
import by.platonov.music.entity.User;
import by.platonov.music.entity.filter.EntityFilter;
import by.platonov.music.exception.RepositoryException;
import by.platonov.music.exception.ServiceException;
import by.platonov.music.repository.PlaylistRepository;
import by.platonov.music.repository.UserRepository;
import by.platonov.music.repository.specification.*;
import lombok.EqualsAndHashCode;
import lombok.extern.log4j.Log4j2;

import java.time.LocalDate;
import java.util.List;

/**
 * @author dzmitryplatonov on 2019-06-17.
 * @version 0.0.1
 */
@Log4j2
@EqualsAndHashCode
public class UserService {

    public List<User> login(String login) throws ServiceException {
        SqlSpecification specification = new UserLoginSpecification(login);
        List<User> users;
        try {
            log.debug("searching " + login + " in repository");
            users = UserRepository.getInstance().query(specification);
            for (User user : users) {
                setUserWithPlaylists(user);
            }
            return users;
        } catch (RepositoryException e) {
            throw new ServiceException("Repository provide an exception for user service", e);
        }
    }

    public boolean register(User user) throws ServiceException {
        try {
            log.debug("registering " + user + " in repository");
            return UserRepository.getInstance().add(user);
        } catch (RepositoryException e) {
            throw new ServiceException("Repository provide an exception for user service", e);
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
            throw new ServiceException("Repository provide an exception for user service", e);
        }
        return result;
    }

    public void removeNotActiveUser() throws ServiceException {
        SqlSpecification specification = new UserNotConfirmedRegistrationSpecification();
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
            throw new ServiceException("Repository provide an exception for user service", e);
        }
    }

    public boolean updateUser(User user) throws ServiceException {
        try {
            return UserRepository.getInstance().update(user);
        } catch (RepositoryException e) {
            throw new ServiceException("Repository provide an exception for user service", e);
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
            throw new ServiceException("Repository provide an exception for user service", e);
        }
    }

    public List<User> searchAllUsers(int limit, long offset) throws ServiceException {
        try {
            List<User> users = UserRepository.getInstance().query(new UserAllLimitSpecification(limit, offset));
            for (User user : users) {
                setUserWithPlaylists(user);
            }
            return users;
        } catch (RepositoryException e) {
            log.debug("could not search for all users", e);
            throw new ServiceException(e);
        }
    }

    public List<User> searchUserByLogin(String login) throws ServiceException {
        try {
            List<User> users = UserRepository.getInstance().query(new UserLoginSpecification(login));
            for (User user : users) {
                setUserWithPlaylists(user);
            }
            return users;
        } catch (RepositoryException e) {
            log.debug("could not search user by login", e);
            throw new ServiceException(e);
        }
    }

    public List<User> searchUserByFilter(EntityFilter entityFilter, int limit, long offset) throws ServiceException {

        try {
            return UserRepository.getInstance().query(new UserFilterSpecification(entityFilter, limit, offset));
        } catch (RepositoryException e) {
            log.debug("could not search users by filter", e);
            throw new ServiceException(e);
        }
    }

    private void setUserWithPlaylists(User user) throws RepositoryException {
        List<Playlist> playlists = PlaylistRepository.getInstance().query(new PlaylistOwnedByUserSpecification(user.getLogin()));
        user.getPlaylists().addAll(playlists);
    }
}
