package by.platonov.music.service;

import by.platonov.music.entity.User;
import by.platonov.music.exception.RepositoryException;
import by.platonov.music.exception.ServiceException;
import by.platonov.music.repository.Repository;
import by.platonov.music.repository.UserRepository;
import by.platonov.music.repository.specification.NotConfirmedRegistrationUserSpecification;
import by.platonov.music.repository.specification.SqlSpecification;
import by.platonov.music.repository.specification.UserEmailHashSpecification;
import by.platonov.music.repository.specification.UserLoginSpecification;
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

    private Repository<User> repository = UserRepository.getInstance();

    public List<User> login(String login) throws ServiceException {
        SqlSpecification specification = new UserLoginSpecification(login);
        try {
            log.debug("finding " + login + " in repository");
            return repository.query(specification);
        } catch (RepositoryException e) {
            throw new ServiceException(EXCEPTION_MESSAGE, e);
        }
    }

    public boolean register(User user) throws ServiceException {
        try {
            log.debug("registering " + user + " in repository");
            return repository.add(user);
        } catch (RepositoryException e) {
            throw new ServiceException(EXCEPTION_MESSAGE, e);
        }
    }

    public boolean activate(String email, String hash) throws ServiceException {
        boolean result = false;
        SqlSpecification specification = new UserEmailHashSpecification(email, hash);
        List<User> users;
        try {
            users = repository.query(specification);
            if (!users.isEmpty()) {
                User user = users.get(0);
                user.setActive(true);
                user.setVerificationUuid(null);
                log.debug("activating " + email + " in repository");
                result = repository.update(user);
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
            users = repository.query(specification);
            if (!users.isEmpty()) {
                for (User user : users) {
                    log.debug("removing not active " + users);
                    repository.remove(user);
                }
            }
        } catch (RepositoryException e) {
            throw new ServiceException(EXCEPTION_MESSAGE, e);
        }
    }
}
