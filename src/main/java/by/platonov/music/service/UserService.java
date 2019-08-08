package by.platonov.music.service;

import by.platonov.music.entity.Payment;
import by.platonov.music.entity.Playlist;
import by.platonov.music.entity.User;
import by.platonov.music.entity.filter.EntityFilter;
import by.platonov.music.exception.RepositoryException;
import by.platonov.music.exception.ServiceException;
import by.platonov.music.repository.PaymentRepository;
import by.platonov.music.repository.PlaylistRepository;
import by.platonov.music.repository.TrackRepository;
import by.platonov.music.repository.UserRepository;
import by.platonov.music.repository.specification.*;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * encapsulates {@link User}'s logic methods to provide needed data to command layer
 *
 * @author dzmitryplatonov on 2019-06-17.
 * @version 0.0.1
 */
@Log4j2
@EqualsAndHashCode
public class UserService {

    /**
     * to add {@link User} to {@link UserRepository}
     * @param user to add
     * @return true id user was added and false if not
     * @throws ServiceException if {@link UserRepository} throws {@link RepositoryException}
     */
    public boolean register(User user) throws ServiceException {
        try {
            log.debug("registering " + user + " in repository");
            return UserRepository.getInstance().add(user);
        } catch (RepositoryException e) {
            log.error(e);
            throw new ServiceException("Repository provide an exception for user service", e);
        }
    }

    /**
     * to change {@link User} status to active
     * @param email user's e-mail
     * @param hash user's unique verification code
     * @return true if user activated and false if not
     * @throws ServiceException if {@link UserRepository} throws {@link RepositoryException}
     */
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
            log.error(e);
            throw new ServiceException("Repository provide an exception for user service", e);
        }
        return result;
    }

    /**
     * to remove {@link User}s with non-active status
     * @throws ServiceException if {@link UserRepository} throws {@link RepositoryException}
     */
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
            log.error(e);
            throw new ServiceException("Repository provide an exception for user service", e);
        }
    }

    /**
     * to update {@link User}'s data
     * @param user to update
     * @return true if user's data updated and false if not
     * @throws ServiceException if {@link UserRepository} throws {@link RepositoryException}
     */
    public boolean updateUser(User user) throws ServiceException {
        try {
            return UserRepository.getInstance().update(user);
        } catch (RepositoryException e) {
            log.error(e);
            throw new ServiceException("Repository provide an exception for user service", e);
        }
    }

    /**
     * to change {@link Playlist} access level
     * @param playlistId id of the playlist
     * @return true if playlist was updated and false if not
     * @throws ServiceException if {@link PlaylistRepository} throws {@link RepositoryException}
     */
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

    /**
     * to find all {@link User}s from database using limit and offset
     * @param limit number of users needed
     * @param offset offset
     * @return list of users
     * @throws ServiceException if {@link UserRepository} throws {@link RepositoryException}
     */
    public List<User> searchAllUsers(int limit, long offset) throws ServiceException {
        try {
            List<User> users = UserRepository.getInstance().query(new UserAllSpecification(limit, offset));
            for (User user : users) {
                setUserWithPlaylists(user);
            }
            return users;
        } catch (RepositoryException e) {
            log.error("could not search for all users", e);
            throw new ServiceException(e);
        }
    }

    /**
     * to find {@link User} by required login
     * @param login user's login
     * @return list of one {@link User} if user with same login found and empty list if not
     * @throws ServiceException if {@link UserRepository} throws {@link RepositoryException}
     */
    public List<User> searchUserByLogin(String login) throws ServiceException {
        try {
            List<User> users = UserRepository.getInstance().query(new UserLoginSpecification(login));
            for (User user : users) {
                setUserWithPlaylists(user);
            }
            return users;
        } catch (RepositoryException e) {
            log.error("could not search user by login", e);
            throw new ServiceException(e);
        }
    }

    /**
     * to find list of {@link User}s by {@link by.platonov.music.entity.filter.UserFilter} using limit and offset
     * @param entityFilter instance of {@link by.platonov.music.entity.filter.UserFilter}
     * @param limit number of users needed
     * @param offset offset
     * @return list of users
     * @throws ServiceException if {@link UserRepository} throws {@link RepositoryException}
     */
    public List<User> searchUserByFilter(@NonNull EntityFilter entityFilter, int limit, long offset) throws ServiceException {

        try {
            return UserRepository.getInstance().query(new UserFilterSpecification(entityFilter, limit, offset));
        } catch (RepositoryException e) {
            log.error("could not search users by filter", e);
            throw new ServiceException(e);
        }
    }

    /**
     * to add {@link Payment} to {@link PaymentRepository}
     * @param payment to add
     * @return true if payment was added and false if not
     * @throws ServiceException if {@link PaymentRepository} throws {@link RepositoryException}
     */
    public boolean processPayment(Payment payment) throws ServiceException {
        try {
            return PaymentRepository.getInstance().add(payment);
        } catch (RepositoryException e) {
            log.error("could not proccess payment " + payment, e);
            throw new ServiceException(e);
        }
    }


    /**
     * to find a set of {@link Payment}s of required user by his login
     * @param userLogin of required user
     * @return payments set
     * @throws ServiceException if {@link PaymentRepository} throws {@link RepositoryException}
     */
    public Set<Payment> getUserPayments(String userLogin) throws ServiceException {
        try {
            return new LinkedHashSet<>(PaymentRepository.getInstance().query(new PaymentByLoginSpecification(userLogin)));
        } catch (RepositoryException e) {
            log.error("could not query for payments", e);
            throw new ServiceException(e);
        }
    }

    /**
     * helper method to set {@link User} with their {@link Playlist}s
     * @param user required user
     * @throws RepositoryException if sql-query could not be completed
     */
    private void setUserWithPlaylists(User user) throws RepositoryException {
        List<Playlist> playlists = PlaylistRepository.getInstance().query(new PlaylistOwnedByUserSpecification(user.getLogin()));
        for (Playlist playlist : playlists) {
            playlist.setTracks(TrackRepository.getInstance().query(new TracksInPlaylistSpecification(playlist.getId())));
        }
        user.getPlaylists().addAll(playlists);
    }
}
